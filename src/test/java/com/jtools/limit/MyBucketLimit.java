// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.limit;

/**
 * The MyBucketLimit.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class MyBucketLimit {
    public long duration = 1000;
    public long timeStamp = now();
    public int capacity = 10; // 桶的容量
    public int rate = 1; // 令牌放入速度
    public long tokens; // 当前令牌数量

    private MyBucketLimit(int rate, int capacity, long duration) {
        this.rate = rate;
        this.capacity = capacity;
        this.duration = duration;
    }

    public static MyBucketLimit create(int rate, int capacity) {
        return create(rate, capacity, 1000);
    }

    public static MyBucketLimit create(int rate, int capacity, long duration) {
        return new MyBucketLimit(rate, capacity, duration);
    }

    public boolean grant() {
        long now = now();
// 先添加令牌
        tokens = Math.min(capacity, tokens + (now - timeStamp) * rate);
        timeStamp = now;
        if (tokens < 1) {
// 若不到1个令牌,则拒绝
            return false;
        } else {
// 还有令牌，领取令牌
            tokens -= 1;
            return true;
        }
    }

    long now() {
        return System.currentTimeMillis() / duration;
    }
}
