package com.isa.ws.rate.strategy.rate.impl;

import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import com.isa.ws.rate.strategy.rate.RateLimiting;
import com.isa.ws.rate.strategy.rate.cache.ICache;
import com.isa.ws.rate.utils.AppProperties;
import com.isa.ws.rate.utils.HierarchialKeyGenerator;
import com.isa.ws.rate.utils.IHierarchialKeyGenerator;

@Component("memcachedRateLimiter")
public class MemcachedRateLimiter implements RateLimiting {

	@Autowired
	@Qualifier("fileBasedProperties")
	private AppProperties properties;

	@Autowired
	@Qualifier("memcached")
	private ICache<String, Object, Future> cache;

	@Value("${cache.status}")
	private Boolean isCacheOn;

	@Value("${rate.limit}")
	private int rateLimit;

	private IHierarchialKeyGenerator countGenerator;

	@PostConstruct
	public void initialize() {
		countGenerator = new HierarchialKeyGenerator().withPrefix("count")
				.withSeperator(properties.getProperty("memcached.keySeperator"));
	}

	@Override
	public boolean handle(HttpServletRequest request) {
		if (isCacheOn) {
			Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			String id = (String) pathVariables.get("id");
			Object val = cache.get(countGenerator.toKey(id));
			if (val != null) {
				int currentCount = (Integer) val;
				cache.set(countGenerator.toKey(id), ++currentCount);
				if (currentCount > rateLimit) {
					return false;
				}
			} else {
				cache.set(countGenerator.toKey(id), 0);
			}
		}

		return true;
	}

}
