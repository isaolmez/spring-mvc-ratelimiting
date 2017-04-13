package com.isa.ws.rate;

import com.isa.ws.rate.config.RateLimitingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Request rate limiting main class
 */
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application extends WebMvcConfigurerAdapter {

    private final HandlerInterceptor rateInterceptor;

    private final RateLimitingProperties rateLimitingProperties;

    @Autowired
    public Application(HandlerInterceptor rateInterceptor, RateLimitingProperties rateLimitingProperties) {
        this.rateLimitingProperties = rateLimitingProperties;
        this.rateInterceptor = rateInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (rateLimitingProperties.isEnabled()) {
            registry.addInterceptor(rateInterceptor)
                    .addPathPatterns("/hello/**")
                    .excludePathPatterns("/exceed/**");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}