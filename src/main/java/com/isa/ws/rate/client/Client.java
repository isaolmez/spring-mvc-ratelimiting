package com.isa.ws.rate.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class Client {
	private static final Logger LOG = LoggerFactory.getLogger(Client.class);

	public static void main(String[] args) {
		final String uri = "http://localhost:8080/home/isa";

		RestTemplate restTemplate = new RestTemplate();
		for (int i = 0; i < 100; i++) {
			Object result = restTemplate.getForObject(uri, String.class);
			LOG.info("Response: {}", result);
		}
	}
}
