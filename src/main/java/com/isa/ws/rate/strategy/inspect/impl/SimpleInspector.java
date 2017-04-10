package com.isa.ws.rate.strategy.inspect.impl;

import com.isa.ws.rate.strategy.inspect.Inspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SimpleInspector implements Inspector{
	private static final Logger logger = LoggerFactory.getLogger(SimpleInspector.class);
	
	@Override
	public void inspect(HttpServletRequest request) {
		logger.warn("Username {} does not exists!", request.toString());
	}
}
