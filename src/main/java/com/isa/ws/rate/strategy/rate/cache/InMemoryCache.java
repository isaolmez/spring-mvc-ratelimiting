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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.utils.AppProperties;
/**
 * In-memory based implementation of ICache API.
 * 
 * @author isa
 *
 */
@Component("inmemoryCache")
public class InMemoryCache implements ICache<String, Object, Future<Integer>> {

	@Autowired
	@Qualifier("fileBasedProperties")
	private AppProperties appProperties;

	@Value("${cache.general.timeout}")
	private int timeout;

	private ConcurrentMap<String, Integer> counterMap;

	private ExecutorService executor;

	@PostConstruct
	public void init() {
		executor = Executors.newSingleThreadExecutor();
		counterMap = new ConcurrentHashMap<String, Integer>();
	}

	@Override
	public Future<Integer> set(String key, Object value) {
		Future<Integer> future = executor.submit(() -> {
			return counterMap.compute(key, (k, v) -> v == null ? 1 : ++v);
		});

		return future;
	}

	@Override
	public Future<Integer> set(String key, Object value, int ttl) {
		Future<Integer> future = executor.submit(() -> {
			return counterMap.compute(key, (k, v) -> v == null ? 1 : ++v);
		});

		return future;
	}

	@Override
	public Future<Integer> delete(String key) {
		Future<Integer> future = executor.submit(() -> {
			return counterMap.remove(key);
		});

		return future;
	}

	@Override
	public Object get(String key) {
		Integer result = null;
		Future<Integer> future = executor.submit(() -> {
			return counterMap.getOrDefault(key, 0);
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
