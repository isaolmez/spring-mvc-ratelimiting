package com.isa.ws.rate.algorithm;

import com.isa.ws.rate.config.RateLimitingProperties;

import javax.servlet.http.HttpServletRequest;

public abstract class RateLimitingAlgorithm {

    private final RateLimitingProperties rateLimitingProperties;

    public RateLimitingAlgorithm(RateLimitingProperties rateLimitingProperties) {
        this.rateLimitingProperties = rateLimitingProperties;
    }

    public boolean process(HttpServletRequest request) {
        if (inspectionEnabled()) {
            boolean shouldRateLimit = inspectPhase(request);
            if (!shouldRateLimit) {
                return true;
            }
        }

        return rateLimit(request);
    }

    public boolean inspectionEnabled() {
        return rateLimitingProperties.isInspectionEnabled();
    }

    public abstract boolean inspectPhase(HttpServletRequest request);

    public abstract boolean rateLimit(HttpServletRequest request);


}
