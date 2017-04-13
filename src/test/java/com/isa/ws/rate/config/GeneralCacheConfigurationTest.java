package com.isa.ws.rate.config;

import org.ehcache.CacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class GeneralCacheConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void shouldFetchCacheManager(){
        CacheManager cacheManager = context.getBean(CacheManager.class);

        assertNotNull(cacheManager);
    }

    @Test
    public void shouldFetchSingleCacheManager(){
        final int expectedSize = 1;
        Map<String, CacheManager> managers = context.getBeansOfType(CacheManager.class);

        assertNotNull(managers);
        assertEquals(expectedSize, managers.size());
    }

    @Test
    public void shouldListCaches(){
        final String expectedName = "rate";
        CacheManager cacheManager = context.getBean(CacheManager.class);

        assertNotNull(cacheManager.getCache(expectedName, String.class, Integer.class));
    }
}
