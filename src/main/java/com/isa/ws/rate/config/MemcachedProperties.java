package com.isa.ws.rate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("memcached")
public class MemcachedProperties {
    private String[] servers;

    private long timeout;

    private int defaultTtl;

    private String keySeperator;

    public String[] getServers() {
        return servers;
    }

    public void setServers(String[] servers) {
        this.servers = servers;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public int getDefaultTtl() {
        return defaultTtl;
    }

    public void setDefaultTtl(int defaultTtl) {
        this.defaultTtl = defaultTtl;
    }

    public String getKeySeperator() {
        return keySeperator;
    }

    public void setKeySeperator(String keySeperator) {
        this.keySeperator = keySeperator;
    }
}
