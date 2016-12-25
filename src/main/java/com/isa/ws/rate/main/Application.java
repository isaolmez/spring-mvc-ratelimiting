package com.isa.ws.rate.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Request rate limiting main class
 */

@SpringBootApplication(scanBasePackages = "com.isa.ws.*")
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class Application extends WebMvcConfigurerAdapter {

	@Autowired
	@Qualifier("rateLimitingInterceptor")
	private HandlerInterceptor handlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(handlerInterceptor)
		.addPathPatterns("/home/**")
		.excludePathPatterns("/exceed/**");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}