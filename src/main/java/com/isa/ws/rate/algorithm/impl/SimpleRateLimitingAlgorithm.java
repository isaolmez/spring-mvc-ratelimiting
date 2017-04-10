package com.isa.ws.rate.algorithm.impl;

import javax.servlet.http.HttpServletRequest;

import com.isa.ws.rate.config.CacheProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.algorithm.RateLimitingAlgorithm;
import com.isa.ws.rate.strategy.inspect.Inspector;
import com.isa.ws.rate.strategy.rate.RateLimiter;

@Component
public class SimpleRateLimitingAlgorithm extends RateLimitingAlgorithm {

    private final Inspector inspector;

    private final RateLimiter rateLimiter;

    @Autowired
    public SimpleRateLimitingAlgorithm(CacheProperties cacheProperties,
                                       Inspector inspector,
                                       @Qualifier("defaultRateLimiter") RateLimiter rateLimiter) {
        super(cacheProperties);
        this.inspector = inspector;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void inspectPhase(HttpServletRequest request) {
        inspector.inspect(request);
    }

    @Override
    public boolean rateLimit(HttpServletRequest request) {
        return rateLimiter.handle(request);
    }
}
