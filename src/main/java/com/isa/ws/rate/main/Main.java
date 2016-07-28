package com.isa.ws.rate.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Request rate limiting main class
 * Look for @SpringBootApplication annotation
 * https://spring.io/guides/gs/serving-web-content/
 * 
 * */

@SpringBootApplication
@ComponentScan("com.isa.ws")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}