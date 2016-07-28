package com.isa.ws.rate.strategy.inspect;

import javax.servlet.http.HttpServletRequest;

public interface Inspecting {
	void inspect(HttpServletRequest request);
}
