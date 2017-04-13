package com.isa.ws.rate.interceptor;

import com.isa.ws.rate.algorithm.RateLimitingAlgorithm;
import com.isa.ws.rate.config.RateLimitingProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RateLimitingInterceptorTest {

    @InjectMocks
    private RateLimitingInterceptor interceptor;

    @Mock
    private RateLimitingAlgorithm algorithm;

    @Mock
    private RateLimitingProperties rateLimitingProperties;

    @Mock
    private HttpServletResponse mockResponse;

    @Test
    public void shouldRedirect_WhenExceeded() throws IOException {
        final boolean expected = false;
        when(algorithm.process(any())).thenReturn(false);

        try {
            boolean actual = interceptor.preHandle(mock(HttpServletRequest.class), mockResponse, null);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }

        verify(mockResponse, times(1)).sendRedirect(eq(rateLimitingProperties.getExceedRedirect()));
    }

    @Test
    public void shouldNotRedirect_WhenNotExceeded() throws IOException {
        final boolean expected = true;
        when(algorithm.process(any())).thenReturn(true);

        try {
            boolean actual = interceptor.preHandle(mock(HttpServletRequest.class), mockResponse, null);
            assertEquals(expected, actual);
        } catch (Exception e) {
            fail();
        }

        verify(mockResponse, times(0)).sendRedirect(eq(rateLimitingProperties.getExceedRedirect()));
    }
}
