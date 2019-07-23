// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.limit;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The SmoothRateLimiterTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class SmoothRateLimiterTest {
    @Test
    public void testWarnUpLimit() {
        RateLimiter limiter = RateLimiter.create(10, 5, TimeUnit.SECONDS);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(()-> {
            for (int j = 0; j < 200; j++) {
                if (limiter.tryAcquire()) {
                    System.out.println("success");
                } else {
                    System.out.println("false");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            executor.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
