package com.isa.ws.rate.strategy.rate.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaCache implements ICache<String, Object, Future> {
	private LoadingCache<String, Object> cache;
	
	private ExecutorService executor;
	
	@Value("${cache.general.defaultTTL}")
	private int defaultTTL;
	
	@Value("${cache.general.timeout}")
	private int timeout;

	@PostConstruct
	public void init() {
		executor = Executors.newSingleThreadExecutor();
		cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Object>() {
					public Integer load(String key) {
						return new Integer(1);
					}
				});
	}

	@Override
	public Future<Boolean> set(String key, Object value) {
		Future<Boolean> future = executor.submit(() -> {
			cache.put(key, value);
			return true;
		});
		
		return future;
	}

	@Override
	public Future<Boolean> set(String key, Object value, int ttl) {
		Future<Boolean> future = executor.submit(() -> {
			cache.put(key, value);
			return true;
		});
		
		return future;
	}

	@Override
	public Future<Boolean> delete(String key) {
		Future<Boolean> future = executor.submit(() -> {
			cache.invalidate(key);
			return true;
		});
		
		return future;
	}

	@Override
	public Object get(String key) {
		Integer result = null;
		Future<Integer> future = executor.submit(() -> {
			return (Integer)cache.get(key);
		});

		try {
			result = future.get(timeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			future.cancel(false);
			e.printStackTrace();
		}

		return result;
	}

}
