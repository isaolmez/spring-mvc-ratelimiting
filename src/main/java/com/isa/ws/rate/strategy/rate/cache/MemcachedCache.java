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

import com.isa.ws.rate.utils.AppProperties;

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
	private static final Logger logger = LoggerFactory.getLogger(MemcachedCache.class);
	private MemcachedClient memcachedClient;
	private int defaultTTL;
	private int timeout;

	@Autowired
	private AppProperties appProperties;

	/**
	 * Initialize Spring Component
	 */
	@PostConstruct
	public void initialize() {
		// Initialize attributes from property file
		String defaultTTLStr = appProperties.getProperty("memcached.defaultTTL");
		String timeoutStr = appProperties.getProperty("memcached.timeout");
		defaultTTL = Integer.parseInt(defaultTTLStr);
		timeout = Integer.parseInt(timeoutStr);

		// Get a memcached client connected to the server
		try {
			List<InetSocketAddress> servers = AddrUtil.getAddresses(appProperties.getProperty("memcached.servers"));
			memcachedClient = new MemcachedClient(servers);
			logger.info("Successfully connected to Memcached server");
		} catch (IOException e) {
			// Log exception message
			logger.error(e.getMessage());
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
			value = valueFuture.get(timeout, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			// Log exception message and cancel searching for key in cache if it is timeout
			valueFuture.cancel(false);
			logger.error(e.getMessage());
		} catch (InterruptedException | ExecutionException e) {
			// Log exception message if another error
			logger.error(e.getMessage());
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
		OperationFuture<Boolean> status = memcachedClient.set(key, ttl, value);
		return status;
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
		return set(key, value, defaultTTL);
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
		logger.info("Successfully shutdown connection(s) to Memcached server(s)");
	}
}
