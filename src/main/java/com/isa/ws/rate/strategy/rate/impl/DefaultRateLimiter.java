package com.isa.ws.rate.strategy.rate.impl;

import com.isa.ws.rate.config.RateProperties;
import com.isa.ws.rate.strategy.rate.RateLimiter;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Primary
public class DefaultRateLimiter implements RateLimiter {

    private final RateProperties rateProperties;

    private final CacheManager cacheManager;

    private final Cache<String, Integer> cache;

    @Autowired
    public DefaultRateLimiter(RateProperties rateProperties,
                              CacheManager cacheManager) {
        this.rateProperties = rateProperties;
        this.cacheManager = cacheManager;
        this.cache = this.cacheManager.getCache("rate", String.class, Integer.class);
    }

    @Override
    public boolean handle(HttpServletRequest request) {
        String remoteAddress = request.getRemoteAddr();
        Integer currentCount = cache.get(remoteAddress);
        if (currentCount != null) {
            cache.put(remoteAddress, ++currentCount);
            if (currentCount > rateProperties.getLimit()) {
                return false;
            }
        } else {
            cache.put(remoteAddress, 0);
        }

        return true;
    }
}
