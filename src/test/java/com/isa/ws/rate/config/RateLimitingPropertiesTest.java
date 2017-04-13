package com.isa.ws.rate.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@TestPropertySource(properties = {
        "rate.enabled = true",
        "rate.inspection-enabled = true",
        "rate.period = 5",
        "rate.limit=5",
        "rate.exceed-message=Request rate has been exceeded!",
        "rate.exceed-redirect=http://localhost:8080/exceed"})
public class RateLimitingPropertiesTest {
    @Autowired
    private RateLimitingProperties rateLimitingProperties;

    @Test
    public void shouldReturnEnabled() {
        assertTrue(rateLimitingProperties.isEnabled());
    }

    @Test
    public void shouldReturnInspectionEnabled() {
        assertTrue(rateLimitingProperties.isInspectionEnabled());
    }

    @Test
    public void shouldReturnPeriod() {
        final int expectedPeriod = 5;
        assertEquals(expectedPeriod, rateLimitingProperties.getPeriod());
    }

    @Test
    public void shouldReturnLimit() {
        final int expectedLimit = 5;
        assertEquals(expectedLimit, rateLimitingProperties.getLimit());
    }

    @Test
    public void shouldReturnExceedMessage() {
        final String expectedExceedMessage = "Request rate has been exceeded!";
        assertEquals(expectedExceedMessage, rateLimitingProperties.getExceedMessage());
    }

    @Test
    public void shouldReturnExceedUrl() {
        final String expectedExceedUrl = "http://localhost:8080/exceed";
        assertEquals(expectedExceedUrl, rateLimitingProperties.getExceedRedirect());
    }
}
