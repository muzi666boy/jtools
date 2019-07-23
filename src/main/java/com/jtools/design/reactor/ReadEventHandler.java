// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.design.reactor;

/**
 * The AcceptEventHandler.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class ReadEventHandler extends EventHandler {
    private Selector selector;

    public ReadEventHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void handle(Event event) {
        //处理Accept的event事件
        if (event.getType() == EventType.READ) {

            //TODO 处理READ状态的事件
            System.out.println("Process event read:" + event.getSource());
        }
    }
}

