package com.isa.ws.rate.strategy.inspect;

import javax.servlet.http.HttpServletRequest;

public interface Inspector {
	boolean inspect(HttpServletRequest request);
}
