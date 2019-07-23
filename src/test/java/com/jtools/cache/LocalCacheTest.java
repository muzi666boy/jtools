// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.cache;

import org.junit.Test;

import java.util.Random;

/**
 * Test for {@link LocalCache}}.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class LocalCacheTest {

    @Test
    public void testCache() throws Exception {
        LocalCache cache = new LocalCache(100);
        Random rand = new Random();
        for (int i = 0; i < 1000; i ++) {
            cache.put("key" + i, "value" + i);
        }
        for (int i = 0; i < 1000; i ++) {
            Object str = cache.get("key" + rand.nextInt(1000));
            if (str != null) {
                System.out.println(str);
            }
        }
        System.out.println(cache.getHitRate());
        System.out.println(cache.getCount());
        System.out.println(cache.size());
    }
}
