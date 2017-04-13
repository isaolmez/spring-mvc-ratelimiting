package com.isa.ws.rate.strategy.rate.impl;

import com.isa.ws.rate.config.RateLimitingProperties;
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

    private final RateLimitingProperties rateLimitingProperties;

    private final CacheManager cacheManager;

    private final Cache<String, Integer> cache;

    @Autowired
    public DefaultRateLimiter(RateLimitingProperties rateLimitingProperties,
                              CacheManager cacheManager) {
        this.rateLimitingProperties = rateLimitingProperties;
        this.cacheManager = cacheManager;
        this.cache = this.cacheManager.getCache("rate", String.class, Integer.class);
    }

    @Override
    public boolean handle(HttpServletRequest request) {
        String remoteAddress = request.getRemoteAddr();
        Integer currentCount = cache.get(remoteAddress);
        if (currentCount != null) {
            cache.put(remoteAddress, ++currentCount);
            if (currentCount > rateLimitingProperties.getLimit() - 1) {
                return false;
            }
        } else {
            cache.put(remoteAddress, 0);
        }

        return true;
    }
}
