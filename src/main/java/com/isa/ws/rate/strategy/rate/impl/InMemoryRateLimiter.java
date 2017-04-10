package com.isa.ws.rate.strategy.rate.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import com.isa.ws.rate.config.RateProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import com.isa.ws.rate.strategy.rate.RateLimiter;
/**
 * Direct implementation of cache with ConcurrentMap and without Cache implementation
 * 
 * @author isa
 *
 */
@Component("inmemoryRateLimiter")
public class InMemoryRateLimiter implements RateLimiter {
	private ConcurrentMap<String, Integer> counter = new ConcurrentHashMap<>();

	private final RateProperties rateProperties;

	@Autowired
	public InMemoryRateLimiter(RateProperties rateProperties){
		this.rateProperties = rateProperties;
	}

	@Override
	public boolean handle(HttpServletRequest request) {
		Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String id = (String) pathVariables.get("id");
		counter.compute(id, (k, v) -> v == null ? 0 : ++v);
		int currentCounter = counter.get(id);
		if (currentCounter > rateProperties.getLimit()) {
			return false;
		}
		
		return true;
	}
}
