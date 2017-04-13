package com.isa.ws.rate.strategy.inspect.impl;

import com.isa.ws.rate.strategy.inspect.Inspector;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BlackListInspector implements Inspector {
    private static final Logger logger = LoggerFactory.getLogger(BlackListInspector.class);

    @Override
    public boolean inspect(HttpServletRequest request) {
        logger.info("Request coming from: {}", request.getRemoteAddr());
        String remoteAddr = request.getRemoteAddr();
        if(StringUtils.isNotBlank(remoteAddr)){
            if(isBlackListed(remoteAddr)){
                return false;
            }
        }

        return true;
    }

    private boolean isBlackListed(String remoteAddress){
        return false;
    }
}
