// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.limit;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;

/**
 * The TokenLimitTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class TokenLimitTest {
    @Test
    public void rateLimiterTest() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(10);
        Executor executor = Executors.newFixedThreadPool(10);
        Thread.sleep(3000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            final int j = i;
            executor.execute(() -> {
                limiter.acquire();
                System.out.println("take " + j + ", cost:" + (System.currentTimeMillis() - start));
            });
        }
        Thread.sleep(10000);
    }

    @Test
    public void rateLimiterWarmTest() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(10, 2, TimeUnit.SECONDS);
        Executor executor = Executors.newFixedThreadPool(10);
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            final int j = i;
            executor.execute(() -> {
                limiter.acquire();
                System.out.println("take " + j + ", cost:" + (System.currentTimeMillis() - start1));
            });
        }
        Thread.sleep(7000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            final int j = i;
            executor.execute(() -> {
                limiter.acquire();
                System.out.println("take " + j + ", cost:" + (System.currentTimeMillis() - start));
            });
        }
        Thread.sleep(16000);
    }

    @Test
    public void rateLimiterBigTest() throws InterruptedException {
        RateLimiter limiter = RateLimiter.create(10);
        Executor executor = Executors.newFixedThreadPool(10);
        Thread.sleep(3000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            final int j = i;
            executor.execute(() -> {
                limiter.acquire(40);
                System.out.println("take " + j + ", cost:" + (System.currentTimeMillis() - start));
            });
        }
        Thread.sleep(10000);
    }

    @Test
    public void rateLimiterDoubleTest() throws InterruptedException {
        MyBucketLimit limiter = MyBucketLimit.create(10, 10, 10000);
        MyBucketLimit limiter2 = MyBucketLimit.create(2, 2);
        Executor executor = Executors.newFixedThreadPool(10);
        Thread.sleep(3000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            final int j = i;
            Thread.sleep(100);
            if(limiter2.grant()) {
                System.out.println("take " + j + ", cost:" + (System.currentTimeMillis() - start));
            }
//            executor.execute(() -> {
//                if(limiter.grant() && limiter2.grant()) {
//                    System.out.println("take " + j + ", cost:" + (System.currentTimeMillis() - start));
//                }
//            });
        }
        Thread.sleep(10000);
    }
}
