package com.isa.ws.rate.config;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {
	@Value("${cache.status}")
	private boolean cacheEnabled;

	@Value("${memcached.servers}")
	private String memcachedServers;

	@Value("${memcached.timeout}")
	private long memcachedTimeoutInMilliseconds;

	@Value("${memcached.defaultTTL}")
	private int memcachedTTLInSeconds;

	@Value("${memcached.keySeperator}")
	private String memcachedKeySeperator;

	@Value("${rate.limit}")
	private int rateLimit;

	@Value("${rate.exceed.message}")
	private String rateExceededMessage;

	@Value("${rate.exceed.redirect}")
	private String rateExceededRedirect;

	@Value("${cache.general.timeout}")
	private long cacheTimeoutInMilliseconds;

	@Value("${cache.general.defaultTTL}")
	private int cacheTTLInSeconds;

	@Value("${cache.general.keySeperator}")
	private String cacheKeySeperator;

	@Value("${berkeley.path}")
	private String berkeleyDatabasePath;

	private String[] memcachedServersArray;

	@PostConstruct
	public void init() {
		if (StringUtils.isNotBlank(memcachedServers)) {
			memcachedServersArray = StringUtils.split(memcachedServers, ",");
		}
	}
	
	public boolean isCacheEnabled() {
		return cacheEnabled;
	}

	public void setCacheEnabled(boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}

	public long getMemcachedTimeoutInMilliseconds() {
		return memcachedTimeoutInMilliseconds;
	}

	public void setMemcachedTimeoutInMilliseconds(long memcachedTimeoutInMilliseconds) {
		this.memcachedTimeoutInMilliseconds = memcachedTimeoutInMilliseconds;
	}

	public int getMemcachedTTLInSeconds() {
		return memcachedTTLInSeconds;
	}

	public void setMemcachedTTLInSeconds(int memcachedTTLInSeconds) {
		this.memcachedTTLInSeconds = memcachedTTLInSeconds;
	}

	public String getMemcachedKeySeperator() {
		return memcachedKeySeperator;
	}

	public void setMemcachedKeySeperator(String memcachedKeySeperator) {
		this.memcachedKeySeperator = memcachedKeySeperator;
	}

	public int getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(int rateLimit) {
		this.rateLimit = rateLimit;
	}

	public String getRateExceededMessage() {
		return rateExceededMessage;
	}

	public void setRateExceededMessage(String rateExceededMessage) {
		this.rateExceededMessage = rateExceededMessage;
	}

	public String getRateExceededRedirect() {
		return rateExceededRedirect;
	}

	public void setRateExceededRedirect(String rateExceededRedirect) {
		this.rateExceededRedirect = rateExceededRedirect;
	}

	public long getCacheTimeoutInMilliseconds() {
		return cacheTimeoutInMilliseconds;
	}

	public void setCacheTimeoutInMilliseconds(long cacheTimeoutInMilliseconds) {
		this.cacheTimeoutInMilliseconds = cacheTimeoutInMilliseconds;
	}

	public int getCacheTTLInSeconds() {
		return cacheTTLInSeconds;
	}

	public void setCacheTTLInSeconds(int cacheTTLInSeconds) {
		this.cacheTTLInSeconds = cacheTTLInSeconds;
	}

	public String getCacheKeySeperator() {
		return cacheKeySeperator;
	}

	public void setCacheKeySeperator(String cacheKeySeperator) {
		this.cacheKeySeperator = cacheKeySeperator;
	}

	public String getBerkeleyDatabasePath() {
		return berkeleyDatabasePath;
	}

	public void setBerkeleyDatabasePath(String berkeleyDatabasePath) {
		this.berkeleyDatabasePath = berkeleyDatabasePath;
	}

	public String getMemcachedServers() {
		return memcachedServers;
	}

	public void setMemcachedServers(String memcachedServers) {
		this.memcachedServers = memcachedServers;
	}

	public String[] getMemcachedServersArray() {
		return memcachedServersArray;
	}

	public void setMemcachedServersArray(String[] memcachedServersArray) {
		this.memcachedServersArray = memcachedServersArray;
	}
}
