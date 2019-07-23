package com.jtools.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class CyclicBarrierTest {

    @Test
    public void test() throws InterruptedException {
        final CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("wawa, exciting...");
            }
        });
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i< 30; i++) {
            final int j = i;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(j + " waiting...");
                        barrier.await();
                        System.out.println(j + " success...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        Thread.sleep(1000);
        executor.shutdown();
    }
}
