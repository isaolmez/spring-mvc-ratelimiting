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
import com.isa.ws.rate.config.CacheProperties;

@Component("guavaCache")
public class GuavaCache implements Cache<String, Object, Future> {
	private static final Logger LOG = LoggerFactory.getLogger(GuavaCache.class);
	
	private final LoadingCache<String, Object> cache;
	
	private final ExecutorService executor;
	
	private final CacheProperties cacheProperties;

	@Autowired
	public GuavaCache(CacheProperties cacheProperties){
		this.cacheProperties = cacheProperties;
		executor = Executors.newSingleThreadExecutor();
		cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Object>() {
					public Integer load(String key) {
						return 0;
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
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            LOG.error("Error: ", e);
            throw new RuntimeException(e);
        }
    }

}
