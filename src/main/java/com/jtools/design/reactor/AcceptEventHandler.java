// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.design.reactor;

/**
 * The AcceptEventHandler.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class AcceptEventHandler extends EventHandler {
    private Selector selector;

    public AcceptEventHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void handle(Event event) {
        //处理Accept的event事件
        if (event.getType() == EventType.ACCEPT) {

            //TODO 处理ACCEPT状态的事件
            System.out.println("Process event accept:" + event.getSource());

            //将事件状态改为下一个READ状态，并放入selector的缓冲队列中
            Event readEvent = new Event();
            readEvent.setSource(event.getSource());
            readEvent.setType(EventType.READ);

            selector.addEvent(readEvent);
        }
    }
}

