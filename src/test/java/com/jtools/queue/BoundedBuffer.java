// Copyright 2017 Baidu Inc. All rights reserved.
package com.jtools.queue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The BoundedBuffer.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class BoundedBuffer {
    //生产者
    public static class Producer implements Runnable{
        private final BlockingQueue<Integer> queue;

        public Producer(BlockingQueue q){
            this.queue=q;
        }

        @Override
        public void run() {
            try {
                while (true){
                    Thread.sleep(400);//模拟耗时
                    queue.put(produce());
                }
            }catch (InterruptedException e){

            }
        }

        private int produce() {
            int n=new Random().nextInt(10000);
            System.out.println("Thread:" + Thread.currentThread().getId() + " produce:" + n);
            return n;
        }
    }
    //消费者
    public static class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue q){
            this.queue=q;
        }

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(1000);//模拟耗时
                    consume(queue.take());
                }catch (InterruptedException e){

                }

            }
        }

        private void consume(Integer n) {
            System.out.println("Thread:" + Thread.currentThread().getId() + " consume:" + n);

        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue=new ArrayBlockingQueue<Integer>(10);
        Producer p1=new Producer(queue);
        Producer p2=new Producer(queue);
        Consumer c1=new Consumer(queue);
//        Consumer c2=new Consumer(queue);

        new Thread(p1).start();
        new Thread(p2).start();
        new Thread(c1).start();
//        new Thread(c2).start();
    }
}
