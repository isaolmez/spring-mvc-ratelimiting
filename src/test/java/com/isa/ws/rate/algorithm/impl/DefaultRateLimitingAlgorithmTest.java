package com.isa.ws.rate.algorithm.impl;

import com.isa.ws.rate.config.RateProperties;
import com.isa.ws.rate.strategy.inspect.Inspector;
import com.isa.ws.rate.strategy.rate.RateLimiter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRateLimitingAlgorithmTest {

    @InjectMocks
    private DefaultRateLimitingAlgorithm algorithm;

    @Mock
    private Inspector inspector;

    @Mock
    private RateLimiter rateLimiter;

    @Mock
    private RateProperties rateProperties;

    @Test
    public void shouldRunInspection(){
        when(rateProperties.isInspectionEnabled()).thenReturn(true);

        algorithm.process(mock(HttpServletRequest.class));

        verify(inspector, times(1)).inspect(any());
        verify(rateLimiter, times(1)).handle(any());
    }

    @Test
    public void shouldSkipInspection(){
        when(rateProperties.isInspectionEnabled()).thenReturn(false);

        algorithm.process(mock(HttpServletRequest.class));

        verify(inspector, times(0)).inspect(any());
        verify(rateLimiter, times(1)).handle(any());
    }
}
