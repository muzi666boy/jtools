// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Properties;

/**
 * The PerformanceTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class PerformanceTest {
    @Test
    public void testLog4j() {
        // Set log configuration.
        PropertyConfigurator.configure(generateLog4jConfigProperties());
        Log logger = LogFactory.getLog(PerformanceTest.class);
        for (int i = 0; i < 1000; i ++) {
            logger.info("org.apache.log4j.DailyRollingFileAppender");
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i ++) {
            logger.info("org.apache.log4j.DailyRollingFileAppender");
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void testLogBack() {
        // Set log configuration.
        PropertyConfigurator.configure(generateLog4jConfigProperties());
        Log logger = LogFactory.getLog(PerformanceTest.class);
        for (int i = 0; i < 1000; i ++) {
            logger.info("org.apache.log4j.DailyRollingFileAppender");
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i ++) {
            logger.info("org.apache.log4j.DailyRollingFileAppender");
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    private Properties generateLog4jConfigProperties() {
        Properties properties = new Properties();

        properties.put("log4j.rootLogger", "INFO, file");

        properties.put("log4j.appender.file",
                "org.apache.log4j.DailyRollingFileAppender");
        properties.put("log4j.appender.file.File", "log4j.log");
        properties.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
        properties.put("log4j.appender.file.layout.ConversionPattern", "%d-[%t] %p %c - %m%n");
        properties.put("log4j.appender.file.encoding", "UTF-8");
        return properties;
    }

    private Properties generateLogbackConfigProperties() {
        Properties properties = new Properties();

        properties.put("log4j.rootLogger", "INFO, file");

        properties.put("log4j.appender.file",
                "org.apache.log4j.DailyRollingFileAppender");
        properties.put("log4j.appender.file.File", "log4j.log");
        properties.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
        properties.put("log4j.appender.file.layout.ConversionPattern", "%d-[%t] %p %c - %m%n");
        properties.put("log4j.appender.file.encoding", "UTF-8");
        return properties;
    }
}
