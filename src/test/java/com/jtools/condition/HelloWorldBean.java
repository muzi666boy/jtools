// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.condition;

import lombok.Data;

/**
 * The HelloWorldBean.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
@Data
public class HelloWorldBean {
    private String msg;
    public HelloWorldBean(String msg) {
        this.msg = msg;
    }
}
