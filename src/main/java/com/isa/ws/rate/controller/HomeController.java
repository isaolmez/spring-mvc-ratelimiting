package com.isa.ws.rate.controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.isa.ws.rate.config.ApplicationConfiguration;

@RestController
public class HomeController {

	private AtomicInteger counter;

	@Autowired
	private ApplicationConfiguration config;

	@PostConstruct
	public void init() {
		counter = new AtomicInteger();
	}

	@RequestMapping(path = "/home/{id}", method = RequestMethod.GET)
	public int home(@PathVariable String id) {
		return counter.getAndIncrement();
	}

	@RequestMapping(path = "/exceed", method = RequestMethod.GET)
	public String exceed() {
		return config.getRateExceededMessage();
	}

}