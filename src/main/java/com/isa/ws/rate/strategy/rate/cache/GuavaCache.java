package com.isa.ws.rate.strategy.rate.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaCache implements ICache<String, Object, Future> {
	private LoadingCache<String, Integer> cache;
	private ExecutorService executor;
	
	@PostConstruct
	public void init() {
		executor = Executors.newSingleThreadExecutor();
		LoadingCache<String, Integer> cache = CacheBuilder.newBuilder().maximumSize(1000)
				.expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 1;
					}
				});
	}
	
	@Override
	public Future set(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future set(String key, Object value, int ttl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future delete(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
