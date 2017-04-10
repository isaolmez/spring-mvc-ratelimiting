package com.isa.ws.rate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rate")
public class RateProperties {

    private boolean enabled;

    private int limit;

    private String exceedMessage;

    private String exceedUrl;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getExceedMessage() {
        return exceedMessage;
    }

    public void setExceedMessage(String exceedMessage) {
        this.exceedMessage = exceedMessage;
    }

    public String getExceedUrl() {
        return exceedUrl;
    }

    public void setExceedUrl(String exceedUrl) {
        this.exceedUrl = exceedUrl;
    }
}
