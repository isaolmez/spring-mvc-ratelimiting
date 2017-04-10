package com.isa.ws.rate.strategy.rate.cache;

import com.isa.ws.rate.config.MemcachedProperties;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component("memcached")
public class MemcachedCache implements Cache<String, Object, Future> {
    private static final Logger LOG = LoggerFactory.getLogger(MemcachedCache.class);

    private final MemcachedClient memcachedClient;

    private final MemcachedProperties memcachedProperties;

    @Autowired
    public MemcachedCache(MemcachedProperties memcachedProperties) {
        this.memcachedProperties = memcachedProperties;
        try {
            List<InetSocketAddress> servers = AddrUtil.getAddresses(Arrays.asList(memcachedProperties.getServers()));
            memcachedClient = new MemcachedClient(servers);
            LOG.info("Successfully connected to Memcached server");
        } catch (IOException e) {
            LOG.error("Error occured: {}", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object get(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }

        Future<Object> valueFuture = null;
        Object value = null;
        try {
            valueFuture = memcachedClient.asyncGet(key);
            value = valueFuture.get(memcachedProperties.getTimeout(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            valueFuture.cancel(false);
            LOG.error("Timeout occurred: {}", e);
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Error occurred: {}", e);
        }

        return value;
    }

    @Override
    public OperationFuture<Boolean> set(String key, Object value, int ttl) {
        return memcachedClient.set(key, ttl, value);
    }

    @Override
    public OperationFuture<Boolean> set(String key, Object value) {
        return set(key, value, memcachedProperties.getDefaultTtl());
    }

    @Override
    public OperationFuture<Boolean> delete(String key) {
        return memcachedClient.delete(key);
    }

    public void shutdown() {
        memcachedClient.shutdown();
        LOG.info("Successfully shutdown connection(s) to Memcached server(s)");
    }
}
