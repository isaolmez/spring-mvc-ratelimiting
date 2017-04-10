package com.isa.ws.rate.strategy.rate.impl;

import com.isa.ws.rate.config.CacheProperties;
import com.isa.ws.rate.config.RateProperties;
import com.isa.ws.rate.strategy.rate.RateLimiter;
import com.isa.ws.rate.strategy.rate.cache.Cache;
import com.isa.ws.rate.utils.SimpleHierarchialKeyGenerator;
import com.isa.ws.rate.utils.HierarchialKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.Future;

@Component
@Primary
public class DefaultRateLimiter implements RateLimiter {

    private final CacheProperties cacheProperties;

    private final RateProperties rateProperties;

    private final Cache<String, Object, Future> cache;

    @Autowired
    public DefaultRateLimiter(CacheProperties cacheProperties,
                              RateProperties rateProperties,
                              @Qualifier("inmemoryCache") Cache<String, Object, Future> cache){
        this.cacheProperties = cacheProperties;
        this.rateProperties = rateProperties;
        this.cache = cache;
    }

    private HierarchialKeyGenerator countGenerator;

    @PostConstruct
    public void initialize() {
        countGenerator = new SimpleHierarchialKeyGenerator().withPrefix("count")
                .withSeperator(cacheProperties.getKeySeperator());
    }

    @Override
    public boolean handle(HttpServletRequest request) {
        if (cacheProperties.isEnabled()) {
            Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String id = (String) pathVariables.get("id");
            Object val = cache.get(countGenerator.toKey(id));
            if (val != null) {
                int currentCount = (Integer) val;
                cache.set(countGenerator.toKey(id), ++currentCount);
                if (currentCount > rateProperties.getLimit()) {
                    return false;
                }
            } else {
                cache.set(countGenerator.toKey(id), 0);
            }
        }

        return true;
    }

}
