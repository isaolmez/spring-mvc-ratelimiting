package com.isa.ws.rate.strategy.inspect.impl;

import com.isa.ws.rate.strategy.inspect.Inspector;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Primary
public class WhiteListInspector implements Inspector {
    private static final Logger logger = LoggerFactory.getLogger(WhiteListInspector.class);

    @Override
    public boolean inspect(HttpServletRequest request) {
        logger.info("Request coming from: {}", request.getRemoteAddr());
        String remoteAddr = request.getRemoteAddr();
        if(StringUtils.isNotBlank(remoteAddr)){
            if(isWhiteListed(remoteAddr)){
                return true;
            }
        }

        return false;
    }

    private boolean isWhiteListed(String remoteAddress){
        return true;
    }
}
