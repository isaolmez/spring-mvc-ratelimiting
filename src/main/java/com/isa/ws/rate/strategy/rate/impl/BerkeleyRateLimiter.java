package com.isa.ws.rate.strategy.rate.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.isa.ws.rate.strategy.rate.RateLimiting;

@Component("berkeleyRateLimiter")
public class BerkeleyRateLimiter implements RateLimiting{

	@Override
	public boolean handle(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
