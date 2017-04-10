package com.isa.ws.rate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("cache.general")
public class CacheProperties {
    private boolean enabled;

    private long timeout;

    private int defaultTtl;

    private String keySeperator;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
