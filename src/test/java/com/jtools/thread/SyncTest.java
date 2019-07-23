// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.thread;

import org.junit.Test;

/**
 * The SyncTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class SyncTest {

    @Test
    public void testSyncString() throws InterruptedException {
        String a = "123456";
        String b = "12345";
        new Thread(()-> {
            synchronized(a) {
                while (true) {
                    System.out.println("a");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(()-> {
            synchronized(b) {
                while (true) {
                    System.out.println("b");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        Thread.sleep(10000);
    }
}
