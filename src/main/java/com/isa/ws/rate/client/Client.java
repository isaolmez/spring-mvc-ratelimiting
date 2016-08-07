package com.isa.ws.rate.client;

import org.springframework.web.client.RestTemplate;

public class Client {
	public static void main(String[] args) {
		final String uri = "http://localhost:8080/home/isa";

		RestTemplate restTemplate = new RestTemplate();
		for (int i = 0; i < 49; i++) {
			Object result = restTemplate.getForObject(uri, Object.class);
			System.out.println(result);
		}
	}
}
