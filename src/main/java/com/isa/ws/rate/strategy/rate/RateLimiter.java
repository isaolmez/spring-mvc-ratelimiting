package com.isa.ws.rate.strategy.rate;

import javax.servlet.http.HttpServletRequest;

public interface RateLimiter {
	boolean handle(HttpServletRequest request);
}
