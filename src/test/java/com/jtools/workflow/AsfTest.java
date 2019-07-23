// Copyright 2017 Baidu Inc. All rights reserved.
package com.jtools.workflow;

import org.junit.Before;
import org.junit.Test;

import com.baidu.asf.engine.ASFEngine;
import com.baidu.asf.engine.ASFInstance;
import com.baidu.asf.engine.spring.ASFEngineFactoryBean;
import com.baidu.asf.engine.spring.ASFEngineProxy;

/**
 * The AsfTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class AsfTest {

    ASFEngineProxy proxy;

    @Before
    public void init() {
        ASFEngineFactoryBean factoryBean = new ASFEngineFactoryBean();
    }

    @Test
    public void helloWorld() {
    }
}
