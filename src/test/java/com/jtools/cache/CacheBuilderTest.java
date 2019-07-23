// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class CacheBuilderTest {

    private Cache<String, AtomicInteger> cache;

    @Before
    public void setUp() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .recordStats()
                .maximumSize(100)
                .softValues()
                .build();
    }

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 120; i++) {
            cache.put("key_" + i , new AtomicInteger(i));
        }
        System.out.println(cache.getIfPresent("key_1"));
        System.out.println(cache.getIfPresent("key_111"));
        Thread.sleep(1000 * 10);
        System.out.println(cache.getIfPresent("key_111"));
        System.out.println(cache.stats());
    }
}
