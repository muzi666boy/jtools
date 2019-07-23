package com.jtools.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.junit.Test;

/**
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class SemaphoreTest {
    private final Semaphore available = new Semaphore(3, true);

    @Test
    public void test() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i< 10; i++) {
            final int j = i;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
//                        System.out.println(j + " waiting...");
                        available.acquire();
                        System.out.println(j + " starting...");
                        Thread.sleep(1000);
                        System.out.println(j + " success...");
                        available.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        Thread.sleep(5000);
        executor.shutdown();
    }
}
