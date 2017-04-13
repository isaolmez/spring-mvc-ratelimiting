package com.isa.ws.rate.algorithm.impl;

import javax.servlet.http.HttpServletRequest;

import com.isa.ws.rate.config.RateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.algorithm.RateLimitingAlgorithm;
import com.isa.ws.rate.strategy.inspect.Inspector;
import com.isa.ws.rate.strategy.rate.RateLimiter;

@Component
public class DefaultRateLimitingAlgorithm extends RateLimitingAlgorithm {

    private final Inspector inspector;

    private final RateLimiter rateLimiter;

    @Autowired
    public DefaultRateLimitingAlgorithm(RateProperties rateProperties,
                                        Inspector inspector,
                                        RateLimiter rateLimiter) {
        super(rateProperties);
        this.inspector = inspector;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public boolean inspectPhase(HttpServletRequest request) {
        return inspector.inspect(request);
    }

    @Override
    public boolean rateLimit(HttpServletRequest request) {
        return rateLimiter.handle(request);
    }
}
