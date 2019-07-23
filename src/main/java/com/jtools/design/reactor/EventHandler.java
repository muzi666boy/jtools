// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.design.reactor;

/**
 * event处理器的抽象类.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public abstract class EventHandler {

    private InputSource source;
    public abstract void handle(Event event);

    public InputSource getSource() {
        return source;
    }

    public void setSource(InputSource source) {
        this.source = source;
    }
}