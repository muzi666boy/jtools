// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.design.reactor;

/**
 * The InputSource.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class InputSource {
    private Object data;
    private long id;

    public InputSource(Object data, long id) {
        this.data = data;
        this.id = id;
    }

    @Override
    public String toString() {
        return "InputSource{" +
                "data=" + data +
                ", id=" + id +
                '}';
    }
}

