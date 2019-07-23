// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.condition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The Main.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
@Configuration
@ComponentScan("com.jtools.condition")
public class Main {

    @Bean
    @MyProfile("prod")
    public HelloWorldBean getBeanProd() {
        return new HelloWorldBean("prod");
    }

    @Bean
    @MyProfile("test")
    public HelloWorldBean getBeanTest() {
        return new HelloWorldBean("test");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("prod");

        context.register(Main.class);
        context.refresh();
        HelloWorldBean hello = context.getBean(HelloWorldBean.class);
        System.out.println(hello.getMsg());
    }
}
