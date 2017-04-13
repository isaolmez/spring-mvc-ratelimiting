package com.isa.ws.rate.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@ConfigurationProperties("inspection")
public class InspectionProperties {

    private String[] whiteList;

    private String[] blackList;

    @PostConstruct
    public void init(){
        Arrays.sort(whiteList);
        Arrays.sort(blackList);
    }

    public String[] getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String[] whiteList) {
        this.whiteList = whiteList;
    }

    public String[] getBlackList() {
        return blackList;
    }

    public void setBlackList(String[] blackList) {
        this.blackList = blackList;
    }
}
