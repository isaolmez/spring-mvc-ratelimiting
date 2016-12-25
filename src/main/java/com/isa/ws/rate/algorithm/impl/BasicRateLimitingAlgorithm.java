package com.isa.ws.rate.algorithm.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.algorithm.RateLimitingAlgorithm;
import com.isa.ws.rate.strategy.inspect.Inspector;
import com.isa.ws.rate.strategy.rate.RateLimiter;

@Component("basicAlgorithm")
public class BasicRateLimitingAlgorithm extends RateLimitingAlgorithm{

	@Autowired
	@Qualifier("basicInspector")
	private Inspector inspector;
	
	@Autowired
	@Qualifier("defaultRateLimiter")
	private RateLimiter rateLimiter;
	
	@Override
	public void inspectPhase(HttpServletRequest request) {
		inspector.inspect(request);
	}
	
	@Override
	public boolean rateLimit(HttpServletRequest request) {
		return rateLimiter.handle(request);
	}
	@Override
	public boolean doesInspect() {
		return true;
	}
}
