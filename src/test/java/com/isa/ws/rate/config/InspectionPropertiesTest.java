package com.isa.ws.rate.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class InspectionPropertiesTest {
    @Autowired
    private InspectionProperties inspectionProperties;

    @Test
    public void shouldReturnWhiteList() {
        String[] whiteList = inspectionProperties.getWhiteList();
        assertEquals(2, whiteList.length);
        assertTrue(Arrays.binarySearch(whiteList, "127.0.0.1") > -1);
        assertTrue(Arrays.binarySearch(whiteList, "127.0.0.2") > -1);
    }

    @Test
    public void shouldReturnBlackList() {
        String[] blackList = inspectionProperties.getBlackList();
        assertEquals(1, blackList.length);
        assertTrue(Arrays.binarySearch(blackList, "127.0.0.3") > -1);
    }
}
