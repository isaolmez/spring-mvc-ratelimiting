package com.isa.ws.rate.controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import com.isa.ws.rate.config.RateLimitingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private AtomicInteger counter;

    private RateLimitingProperties rateLimitingProperties;

    @Autowired
    public MainController(RateLimitingProperties rateLimitingProperties) {
        this.rateLimitingProperties = rateLimitingProperties;
    }

    @PostConstruct
    public void init() {
        counter = new AtomicInteger();
    }

    @RequestMapping(path = "/hello/{id}", method = RequestMethod.GET)
    public String hello(@PathVariable String id) {
        return "Hello";
    }

    @RequestMapping(path = "/exceed", method = RequestMethod.GET)
    public String exceed() {
        return rateLimitingProperties.getExceedMessage();
    }

}