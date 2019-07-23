// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.design.reactor;

/**
 * The Event.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class Event {
    private InputSource source;
    private EventType type;

    public InputSource getSource() {
        return source;
    }

    public void setSource(InputSource source) {
        this.source = source;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
