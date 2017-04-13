package com.isa.ws.rate.algorithm.impl;

import com.isa.ws.rate.config.RateLimitingProperties;
import com.isa.ws.rate.strategy.inspect.Inspector;
import com.isa.ws.rate.strategy.rate.RateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    private RateLimitingProperties rateLimitingProperties;

    @Test
    public void shouldRunInspection(){
        when(rateLimitingProperties.isInspectionEnabled()).thenReturn(true);

        algorithm.process(mock(HttpServletRequest.class));

        verify(inspector, times(1)).shouldRateLimit(any());
    }

    @Test
    public void shouldSkipInspection(){
        when(rateLimitingProperties.isInspectionEnabled()).thenReturn(false);

        algorithm.process(mock(HttpServletRequest.class));

        verify(inspector, times(0)).shouldRateLimit(any());
    }

    @Test
    public void shouldRunRateLimit(){
        when(rateLimitingProperties.isInspectionEnabled()).thenReturn(true);
        when(inspector.shouldRateLimit(any())).thenReturn(true);

        algorithm.process(mock(HttpServletRequest.class));

        verify(inspector, times(1)).shouldRateLimit(any());
        verify(rateLimiter, times(1)).handle(any());
    }

    @Test
    public void shouldSkipRateLimit(){
        when(rateLimitingProperties.isInspectionEnabled()).thenReturn(true);
        when(inspector.shouldRateLimit(any())).thenReturn(false);

        algorithm.process(mock(HttpServletRequest.class));

        verify(inspector, times(1)).shouldRateLimit(any());
        verify(rateLimiter, times(0)).handle(any());
    }
}
