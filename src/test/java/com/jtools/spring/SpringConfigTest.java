// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The SpringConfigTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class SpringConfigTest {
    @Test
    public void testValue() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring/test-config-beans.xml");
        context.refresh();
        
//        UserBean user = context.getBean(UserBean.class);
//        System.out.println(user);
    }

}
