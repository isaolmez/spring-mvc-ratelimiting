package com.isa.ws.rate.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isa.ws.rate.config.RateLimitingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.isa.ws.rate.algorithm.RateLimitingAlgorithm;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

	private final RateLimitingAlgorithm rateLimitingAlgorithm;
	
	private final RateLimitingProperties rateLimitingProperties;

	@Autowired
	public RateLimitingInterceptor(RateLimitingAlgorithm rateLimitingAlgorithm, RateLimitingProperties rateLimitingProperties){
		this.rateLimitingAlgorithm = rateLimitingAlgorithm;
		this.rateLimitingProperties = rateLimitingProperties;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		// Do nothing
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		// Do nothing
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		boolean canContinue = rateLimitingAlgorithm.process(request);
		if (!canContinue) {
			response.sendRedirect(rateLimitingProperties.getExceedRedirect());
			return false;
		} else {
			return true;
		}
	}
}
