package com.isa.ws.rate.strategy.rate.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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

import com.isa.ws.rate.config.ApplicationConfiguration;
/**
 * In-memory based implementation of ICache API.
 * 
 * @author isa
 *
 */
@Component("inmemoryCache")
public class InMemoryCache implements ICache<String, Object, Future<Integer>> {
	private static final Logger LOG = LoggerFactory.getLogger(InMemoryCache.class);
	
	@Autowired
	private ApplicationConfiguration config;

	private ConcurrentMap<String, Integer> counterMap;

	private ExecutorService executor;

	@PostConstruct
	public void init() {
		executor = Executors.newSingleThreadExecutor();
		counterMap = new ConcurrentHashMap<>();
	}

	@Override
	public Future<Integer> set(String key, Object value) {
		return executor.submit(() -> counterMap.compute(key, (k, v) -> v == null ? 1 : ++v));
	}

	@Override
	public Future<Integer> set(String key, Object value, int ttl) {
		return executor.submit(() -> counterMap.compute(key, (k, v) -> v == null ? 1 : ++v));
	}

	@Override
	public Future<Integer> delete(String key) {
		return executor.submit(() -> counterMap.remove(key));
	}

	@Override
	public Object get(String key) {
		Integer result = null;
		Future<Integer> future = executor.submit(() -> counterMap.getOrDefault(key, 0));

		try {
			result = future.get(config.getCacheTimeoutInMilliseconds(), TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			LOG.error("The operation is interrupted: {}", e);
		} catch (ExecutionException e) {
			LOG.error("Error occurred: {}", e);
		} catch (TimeoutException e) {
			future.cancel(false);
			LOG.error("Timeout occurred: {}", e);
		}

		return result;
	}

}
