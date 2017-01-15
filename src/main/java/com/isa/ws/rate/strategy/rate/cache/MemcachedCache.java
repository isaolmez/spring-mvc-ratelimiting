package com.isa.ws.rate.strategy.rate.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isa.ws.rate.config.ApplicationConfiguration;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * Memcached based implementation of ICache API.
 * 
 * @author isa
 *
 */
@Component("memcached")
public class MemcachedCache implements ICache<String, Object, Future> {
	private static final Logger LOG = LoggerFactory.getLogger(MemcachedCache.class);
	private MemcachedClient memcachedClient;

	@Autowired
	private ApplicationConfiguration config;

	@PostConstruct
	public void initialize() {
		// Get a memcached client connected to the server
		try {
			List<InetSocketAddress> servers = AddrUtil.getAddresses(config.getMemcachedServers());
			memcachedClient = new MemcachedClient(servers);
			LOG.info("Successfully connected to Memcached server");
		} catch (IOException e) {
			LOG.error("Error occured: {}", e);
		}
	}

	/**
	 * Return value with key in cache, return NULL if not found
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Object get(String key) {
		// Check if key is valid
		if (key == null || key.isEmpty()) {
			return null;
		}

		// Initialize objects
		Future<Object> valueFuture = null;
		Object value = null;

		try {
			// Try to get a value, for up to X seconds, and cancel if it doesn't return
			valueFuture = memcachedClient.asyncGet(key);
			value = valueFuture.get(config.getMemcachedTimeoutInMilliseconds(), TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			// Cancel searching for key in cache if it is timeout
			valueFuture.cancel(false);
			LOG.error("Timeout occurred: {}", e);
		} catch (InterruptedException | ExecutionException e) {
			LOG.error("Error occurred: {}", e);
		}

		return value;
	}

	/**
	 * Set value with TTL in cache using key, return OperationFuture object
	 *
	 * @param key
	 * @param value
	 * @param ttl
	 * @return
	 */
	@Override
	public OperationFuture<Boolean> set(String key, Object value, int ttl) {
		return memcachedClient.set(key, ttl, value);
	}

	/**
	 * Set value using default TTL in cache using key, return OperationFuture object
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public OperationFuture<Boolean> set(String key, Object value) {
		return set(key, value, config.getMemcachedTTLInSeconds());
	}

	/**
	 * Delete entry with key in memcache
	 *
	 * @param key
	 * @return
	 */
	@Override
	public OperationFuture<Boolean> delete(String key) {
		return memcachedClient.delete(key);
	}

	/**
	 * Shutdown client connection
	 */
	public void shutdown() {
		memcachedClient.shutdown();
		LOG.info("Successfully shutdown connection(s) to Memcached server(s)");
	}
}
