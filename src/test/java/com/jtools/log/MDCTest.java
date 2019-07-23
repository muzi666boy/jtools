// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.log;

import org.junit.Test;
import org.slf4j.MDC;

/**
 * The MDCTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class MDCTest {

    @Test
    public void testMdc() {
        MDC.put("key", "abc");
        new Thread() {
            @Override
            public void run() {
                MDC.put("key2", "bcd");
            }
        }.run();
        System.out.println(MDC.getCopyOfContextMap().values());
    }
}
