package com.isa.ws.rate.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Request rate limiting main class
 * Look for @SpringBootApplication annotation
 * https://spring.io/guides/gs/serving-web-content/
 * 
 * */

@SpringBootApplication
@EnableWebMvc
@Configuration
@ComponentScan("com.isa.ws.*")
@PropertySource("classpath:application.properties")
public class Main extends WebMvcConfigurerAdapter {

	@Autowired
	@Qualifier("rateLimitingInterceptor")
	private HandlerInterceptor handlerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(handlerInterceptor).addPathPatterns("/home/**").excludePathPatterns("/exceed/**");;
	}

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}