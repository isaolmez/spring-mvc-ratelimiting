package com.isa.ws.rate.controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	AtomicInteger counter;
	
	@PostConstruct
	public void init(){
		counter = new AtomicInteger();
	}
	
	@RequestMapping(path = "/home/{id}", method = RequestMethod.GET)
	public int home(@PathVariable String id) {
		return counter.getAndIncrement();
	}

}