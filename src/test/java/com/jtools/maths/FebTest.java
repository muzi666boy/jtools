// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.maths;

import org.junit.Test;

/**
 * The FebTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class FebTest {

    @Test
    public void testFeb() {
        long start = System.currentTimeMillis();
        long result = feb(40);
        long end = System.currentTimeMillis();
        System.out.println(String.format("result:%d, cost:%d", result, end - start));

    }

    public long feb(int n) {
        if (n < 2) {
            return n;
        }
        return feb(n-1) + feb(n-2);
    }
}
