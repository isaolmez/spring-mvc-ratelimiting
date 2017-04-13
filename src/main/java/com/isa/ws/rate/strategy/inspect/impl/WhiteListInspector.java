package com.isa.ws.rate.strategy.inspect.impl;

import com.isa.ws.rate.config.InspectionProperties;
import com.isa.ws.rate.strategy.inspect.Inspector;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Component
@Primary
public class WhiteListInspector implements Inspector {
    private static final Logger logger = LoggerFactory.getLogger(WhiteListInspector.class);

    private final InspectionProperties inspectionProperties;

    @Autowired
    public WhiteListInspector(InspectionProperties inspectionProperties){
        this.inspectionProperties = inspectionProperties;
    }

    @Override
    public boolean shouldRateLimit(HttpServletRequest request) {
        logger.info("Request coming from: {}", request.getRemoteAddr());
        String remoteAddr = request.getRemoteAddr();
        if(StringUtils.isNotBlank(remoteAddr)){
            if(isWhiteListed(remoteAddr)){
                return false;
            }
        }

        return true;
    }

    private boolean isWhiteListed(String remoteAddress){
        return Arrays.binarySearch(inspectionProperties.getWhiteList(), remoteAddress) > -1;
    }
}
