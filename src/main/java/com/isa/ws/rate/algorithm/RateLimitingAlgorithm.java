package com.isa.ws.rate.algorithm;

import com.isa.ws.rate.config.RateProperties;

import javax.servlet.http.HttpServletRequest;

public abstract class RateLimitingAlgorithm {

    private final RateProperties rateProperties;

    public RateLimitingAlgorithm(RateProperties rateProperties) {
        this.rateProperties = rateProperties;
    }

    public boolean process(HttpServletRequest request) {
        if (inspectionEnabled()) {
            inspectPhase(request);
        }

        return rateLimit(request);
    }

    public boolean inspectionEnabled() {
        return rateProperties.isInspectionEnabled();
    }

    public abstract boolean inspectPhase(HttpServletRequest request);

    public abstract boolean rateLimit(HttpServletRequest request);


}
