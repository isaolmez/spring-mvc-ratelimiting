package com.isa.ws.rate.algorithm;

import javax.servlet.http.HttpServletRequest;

public abstract class RateLimitingAlgorithm {
	/**
	 * Algorithm for rate limiting
	 * 2 phases are defined
	 */
	public boolean process(HttpServletRequest request) {
		if (doesInspect()) {
			inspectPhase(request);
		}

		return rateLimit(request);
	}

	public boolean doesInspect() {
		return true;
	}
	
	public abstract void inspectPhase(HttpServletRequest request);

	public abstract boolean rateLimit(HttpServletRequest request);

	
}
