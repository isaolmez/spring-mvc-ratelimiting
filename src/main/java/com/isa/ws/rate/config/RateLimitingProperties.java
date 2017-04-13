package com.isa.ws.rate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("rate")
public class RateLimitingProperties {

    private boolean enabled;

    private boolean inspectionEnabled;

    private int period;

    private int limit;

    private String exceedMessage;

    private String exceedRedirect;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isInspectionEnabled() {
        return inspectionEnabled;
    }

    public void setInspectionEnabled(boolean inspectionEnabled) {
        this.inspectionEnabled = inspectionEnabled;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
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

    public String getExceedRedirect() {
        return exceedRedirect;
    }

    public void setExceedRedirect(String exceedRedirect) {
        this.exceedRedirect = exceedRedirect;
    }
}
