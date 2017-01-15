package com.isa.ws.rate.strategy.rate.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.isa.ws.rate.config.ApplicationConfiguration;

/**
 * Google Guava based implementation of ICache API.
 * 
 * @author isa
 *
 */
@Component("guavaCache")
public class GuavaCache implements ICache<String, Object, Future> {
	private static final Logger LOG = LoggerFactory.getLogger(GuavaCache.class);
	
	private LoadingCache<String, Object> cache;
	
	private ExecutorService executor;
	
	@Autowired
	private ApplicationConfiguration config;

	@PostConstruct
	public void init() {
		executor = Executors.newSingleThreadExecutor();
		cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Object>() {
					public Integer load(String key) {
						return 1;
					}
				});
	}

	@Override
	public Future<Boolean> set(String key, Object value) {
		return executor.submit(() -> {
			cache.put(key, value);
			return true;
		});
	}

	@Override
	public Future<Boolean> set(String key, Object value, int ttl) {
		return executor.submit(() -> {
			cache.put(key, value);
			return true;
		});
	}

	@Override
	public Future<Boolean> delete(String key) {
		return executor.submit(() -> {
			cache.invalidate(key);
			return true;
		});
	}

	@Override
	public Object get(String key) {
		Integer result = null;
		Future<Integer> future = executor.submit(() -> (Integer)cache.get(key));

		try {
			result = future.get(config.getCacheTimeoutInMilliseconds(), TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException e) {
			LOG.error("Error occurred: {}", e);
		} catch (TimeoutException e) {
			future.cancel(false);
			LOG.error("Timeout occurred: {}", e);
		}

		return result;
	}

}
