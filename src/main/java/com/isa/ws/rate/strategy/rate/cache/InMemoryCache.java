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
import javax.naming.OperationNotSupportedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.config.CacheProperties;

@Component("inmemoryCache")
public class InMemoryCache implements Cache<String, Object, Future<Integer>> {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryCache.class);

    private final CacheProperties cacheProperties;

    private final ConcurrentMap<String, Integer> counterMap;

    private final ExecutorService executor;

    @Autowired
    public InMemoryCache(CacheProperties cacheProperties){
        this.cacheProperties = cacheProperties;
        executor = Executors.newSingleThreadExecutor();
        counterMap = new ConcurrentHashMap<>();
    }

    @Override
    public Future<Integer> set(String key, Object value) {
        return executor.submit(() -> counterMap.compute(key, (k, v) -> v == null ? 1 : ++v));
    }

    @Override
    public Future<Integer> set(String key, Object value, int ttl) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Future<Integer> delete(String key) {
        return executor.submit(() -> counterMap.remove(key));
    }

    @Override
    public Object get(String key) {
        return counterMap.getOrDefault(key, 0);
    }

}
