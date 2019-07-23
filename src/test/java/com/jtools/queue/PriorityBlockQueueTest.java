/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.jtools.queue;

import org.junit.Test;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The PriorityBlockQueueTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class PriorityBlockQueueTest {
    @Test
    public void testBlockQueue() throws InterruptedException {
        BlockingQueue<String> queue = new PriorityBlockingQueue<String>(20);
        for (int i = 5; i >0; i-- ) {
            queue.offer("item" + i);
        }
        while (true) {
            String item = queue.poll(2, TimeUnit.SECONDS);
            System.out.println(System.currentTimeMillis() + item);
            if (item == null) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() + " success");
    }

    @Test
    public void testBlockQueueComparator() throws InterruptedException {
        BlockingQueue<Task> queue = new PriorityBlockingQueue<Task>(20, new PaQueueComparator());

        for (int i = 5; i >0; i-- ) {
            queue.offer(new MyPriorityTask(i, i));
        }
        queue.offer(new MyTask(666));
        queue.offer(new MyPriorityTask(777, 3));
        queue.offer(new MyTask(888));
        queue.offer(new MyPriorityTask(999, 3));
        queue.offer(new MyTask(0));
        queue.offer(new MyTask(111));
        while (true) {
            Task item = queue.poll(2, TimeUnit.SECONDS);
            System.out.println(item);
            if (item == null) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() + " success");
    }

    public static class PaQueueComparator implements Comparator<Task> {

        @Override
        public int compare(Task o1, Task o2) {
            int priority1 = 3;
            int priority2 = 3;
            if (o1 instanceof PaPriorityTask) {
                priority1 = ((PaPriorityTask) o1).getPriority();
            }
            if (o2 instanceof PaPriorityTask) {
                priority2 = ((PaPriorityTask) o2).getPriority();
            }
            return priority2 - priority1;
        }
    }

    public interface PaPriorityTask extends Task {
        int getPriority();
    }

    public interface Task {
        int getTaskId();
    }

    public class MyTask implements Task {

        private long taskId;
        public MyTask(long taskId) {
            this.taskId = taskId;
        }
        @Override
        public int getTaskId() {
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{MyTask, taskId=");
            builder.append(taskId);
            builder.append("}");
            return builder.toString();
        }
    }

    public class MyPriorityTask implements PaPriorityTask {

        private long taskId;
        private int priority;
        public MyPriorityTask(long taskId, int priority) {
            this.taskId = taskId;
            this.priority = priority;
        }
        @Override
        public int getTaskId() {
            return 0;
        }

        @Override
        public int getPriority() {
            return this.priority;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{MyPriorityTask, taskId=");
            builder.append(taskId);
            builder.append(", priority=");
            builder.append(priority);
            builder.append("}");
            return builder.toString();
        }
    }
}

