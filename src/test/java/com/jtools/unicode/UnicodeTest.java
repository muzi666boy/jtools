// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.unicode;

import java.nio.charset.Charset;

import org.junit.Test;

/**
 * The UnicodeTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class UnicodeTest {
    @Test
    public void testUnicode() {
        String str = "新中国测试";

        byte[] bytes;
        bytes = str.getBytes(Charset.forName("gbk"));
        for (int i = 0 ; i < bytes.length; i ++) {
            System.out.print(bytes[i] + ", ");
        }
        bytes = str.getBytes(Charset.forName("unicode"));
        System.out.println();
        for (int i = 0 ; i < bytes.length; i ++) {
            System.out.print(bytes[i] + ", ");
        }
        System.out.println();
        bytes = str.getBytes(Charset.forName("UnicodeBigUnmarked"));
        for (int i = 0 ; i < bytes.length; i ++) {
            System.out.print(bytes[i] + ", ");
        }
    }
}
