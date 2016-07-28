package com.isa.ws.rate.strategy.rate;

import javax.servlet.http.HttpServletRequest;

public interface RateLimiting {
	boolean handle(HttpServletRequest request);
}
