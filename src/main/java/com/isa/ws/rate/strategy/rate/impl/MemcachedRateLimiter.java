package com.isa.ws.rate.strategy.rate.impl;

import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import com.isa.ws.rate.config.ApplicationConfiguration;
import com.isa.ws.rate.strategy.rate.RateLimiter;
import com.isa.ws.rate.strategy.rate.cache.ICache;
import com.isa.ws.rate.utils.HierarchialKeyGenerator;
import com.isa.ws.rate.utils.IHierarchialKeyGenerator;
/**
 * Memcached based cache is wired to the cache property and memcached related properties are used. Other than that same with the DefaultRateLimiter.
 * 
 * @author isa
 *
 */
@Component("memcachedRateLimiter")
public class MemcachedRateLimiter implements RateLimiter {

	@Autowired
	private ApplicationConfiguration config;

	@Autowired
	@Qualifier("memcached")
	private ICache<String, Object, Future> cache;

	private IHierarchialKeyGenerator countGenerator;

	@PostConstruct
	public void initialize() {
		countGenerator = new HierarchialKeyGenerator().withPrefix("count")
				.withSeperator(config.getMemcachedKeySeperator());
	}

	@Override
	public boolean handle(HttpServletRequest request) {
		if (config.isCacheEnabled()) {
			Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			String id = (String) pathVariables.get("id");
			Object val = cache.get(countGenerator.toKey(id));
			if (val != null) {
				int currentCount = (Integer) val;
				cache.set(countGenerator.toKey(id), ++currentCount);
				if (currentCount > config.getRateLimit()) {
					return false;
				}
			} else {
				cache.set(countGenerator.toKey(id), 0);
			}
		}

		return true;
	}

}
