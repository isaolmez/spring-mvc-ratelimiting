package com.isa.ws.rate.strategy.rate.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;


public class InMemoryCache implements ICache<String, Object, Future> {
	private Map<String, Integer> counterMap;
	private ExecutorService executor;

	@PostConstruct
	public void init(){
		executor = Executors.newSingleThreadExecutor();
		counterMap = new ConcurrentHashMap<String, Integer>();
	}
	
	@Override
	public Future set(String key, Object value) {
		Future future = executor.submit(() -> {
			counterMap.compute(key, (k, v) -> v == null ? 1 : ++v);
			return true;
		});

		return future;
	}

	@Override
	public Future set(String key, Object value, int ttl) {
		Future future = executor.submit(() -> {
			counterMap.compute(key, (k, v) -> v == null ? 1 : ++v);
			return true;
		});

		return future;
	}

	@Override
	public Future delete(String key) {
		return null;
	}

	@Override
	public Object get(String key) {
		return null;
	}

}
