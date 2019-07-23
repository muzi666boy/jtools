// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;

/**
 * The CaffeineTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class CaffeineTest {

    @Test
    public void testCaffeine() {
        Cache<String, DataObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(10)
                .build();
        String key = "A";
        DataObject dataObject = cache.getIfPresent(key);
        assertNull(dataObject);

        dataObject = new DataObject("A data");
        cache.put(key, dataObject);
        dataObject = cache.getIfPresent(key);

        assertNotNull(dataObject);

        cache.invalidate(key);
        dataObject = cache
                .get(key, k -> DataObject.get("Data for A"));

        assertNotNull(dataObject);
        assertEquals("Data for A", dataObject.getData());
    }
}
