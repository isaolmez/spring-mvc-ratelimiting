package com.isa.ws.rate.strategy.inspect.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@TestPropertySource(properties = {"inspection.white-list=127.0.0.1", "inspection.black-list=127.0.0.2"})
public class WhiteListInspectorTest {

    @Autowired
    private WhiteListInspector inspector;

    private HttpServletRequest request;

    @Before
    public void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    public void shouldAllow() {
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        boolean result = inspector.shouldRateLimit(request);

        assertFalse(result);
    }

    @Test
    public void shouldDisallow() {
        when(request.getRemoteAddr()).thenReturn("127.1.1.1");

        boolean result = inspector.shouldRateLimit(request);

        assertTrue(result);
    }

}
