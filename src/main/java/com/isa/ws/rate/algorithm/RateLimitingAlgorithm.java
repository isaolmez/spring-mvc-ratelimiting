package com.isa.ws.rate.algorithm;

import com.isa.ws.rate.config.CacheProperties;

import javax.servlet.http.HttpServletRequest;

public abstract class RateLimitingAlgorithm {

    private final CacheProperties cacheProperties;

    public RateLimitingAlgorithm(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    public boolean process(HttpServletRequest request) {
        if (inspectionEnabled()) {
            inspectPhase(request);
        }

        return rateLimit(request);
    }

    public boolean inspectionEnabled() {
        return cacheProperties.isEnabled();
    }

    public abstract void inspectPhase(HttpServletRequest request);

    public abstract boolean rateLimit(HttpServletRequest request);


}
