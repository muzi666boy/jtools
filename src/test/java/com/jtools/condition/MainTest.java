// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.condition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The MainTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Main.class})
@ActiveProfiles("prod")
public class MainTest {
    @Autowired
    private HelloWorldBean helloWorldBean;

    @Test
    public void test() {
        System.out.println(helloWorldBean.getMsg());
        System.out.println(System.currentTimeMillis());
    }
}
