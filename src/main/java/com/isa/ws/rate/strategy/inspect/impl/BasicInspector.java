package com.isa.ws.rate.strategy.inspect.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.strategy.inspect.Inspector;

@Component("basicInspector")
public class BasicInspector implements Inspector{
	private static final Logger logger = LoggerFactory.getLogger(BasicInspector.class);
	
	@Override
	public void inspect(HttpServletRequest request) {
		logger.warn("Username {} does not exists!", request.toString());
	}
}
