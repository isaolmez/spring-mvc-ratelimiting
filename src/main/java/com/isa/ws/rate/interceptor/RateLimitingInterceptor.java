package com.isa.ws.rate.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.isa.ws.rate.algorithm.RateLimitingAlgorithm;

@Component("rateLimitingInterceptor")
public class RateLimitingInterceptor implements HandlerInterceptor {

	@Autowired
	@Qualifier("basicAlgorithm")
	private RateLimitingAlgorithm rateLimitingAlgorithm;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		boolean canContinue = rateLimitingAlgorithm.process(request);
		if (!canContinue) {
			response.getWriter().println(0);
			return false;
		} else {
			return true;
		}
	}

}
