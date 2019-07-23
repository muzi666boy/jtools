// Copyright 2017 Baidu Inc. All rights reserved.
package com.jtools.thread;

import org.junit.Test;

/**
 * The ThreadTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class ThreadTest implements Runnable {
    int b = 100;

    synchronized void m1() throws InterruptedException {
        Thread.sleep(500); //6
        b = 1000;
        System.out.println("b=" + b);
    }

    synchronized void m2() throws InterruptedException {
        Thread.sleep(250); //4
        b = 2000;
    }

    @Test
    public void testThread() throws InterruptedException {
        ThreadTest tt = new ThreadTest();
        Thread t = new Thread(tt);  //1
        t.start(); //2

        tt.m2(); //3
        System.out.println("main thread b=" + tt.b); //5
        t.join();
        System.out.println("main thread b=" + tt.b); //7
    }

    @Override
    public void run() {
        try {
            m1();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
