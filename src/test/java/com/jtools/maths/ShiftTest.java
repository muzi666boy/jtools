// Copyright 2017 Baidu Inc. All rights reserved.
package com.jtools.maths;

import org.junit.Test;

/**
 * The ShiftTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class ShiftTest {

    @Test
    public void testShift() {
        long a = 12345;
        long b = 56789;
        System.out.println(a << 20 | (b & (1 << 21)-1));
    }
}
