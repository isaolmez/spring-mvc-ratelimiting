package com.isa.ws.rate.strategy.rate.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import com.isa.ws.rate.strategy.rate.RateLimiting;

@Component("inmemoryRateLimiter")
public class InMemoryRateLimiter implements RateLimiting {
	Map<String, Integer> counter = new ConcurrentHashMap<String, Integer>();

	@Value("${rate.limit}")
	private int rateLimit;

	@Override
	public boolean handle(HttpServletRequest request) {
		Map pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String id = (String) pathVariables.get("id");
		counter.compute(id, (k, v) -> v == null ? 0 : ++v);
		int currentCounter = counter.get(id);
		if (currentCounter > rateLimit) {
			return false;
		}

		return true;
	}

}
