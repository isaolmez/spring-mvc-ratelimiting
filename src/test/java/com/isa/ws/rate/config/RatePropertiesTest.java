package com.isa.ws.rate.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class RatePropertiesTest {
    @Autowired
    private RateProperties rateProperties;

    @Test
    public void shouldReturnEnabled(){
        assertTrue(rateProperties.isEnabled());
    }

    @Test
    public void shouldReturnInspectionEnabled(){
        assertTrue(rateProperties.isInspectionEnabled());
    }

    @Test
    public void shouldReturnPeriod(){
        final int expectedPeriod = 5;
        assertEquals(expectedPeriod, rateProperties.getPeriod());
    }

    @Test
    public void shouldReturnLimit(){
        final int expectedLimit = 5;
        assertEquals(expectedLimit, rateProperties.getLimit());
    }

    @Test
    public void shouldReturnExceedMessage(){
        final String expectedExceedMessage = "Request rate has been exceeded!";
        assertEquals(expectedExceedMessage , rateProperties.getExceedMessage());
    }

    @Test
    public void shouldReturnExceedUrl(){
        final String expectedExceedUrl  = "http://localhost:8080/exceed";
        assertEquals(expectedExceedUrl, rateProperties.getExceedRedirect());
    }
}
