// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.design.reactor;

/**
 * The Server.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class Server {
    Selector selector = new Selector();
    Dispatcher eventLooper = new Dispatcher(selector);
    Acceptor acceptor;

    Server(int port) {
        acceptor = new Acceptor(selector, port);
    }

    public void start() {
        eventLooper.registEventHandler(EventType.ACCEPT, new AcceptEventHandler(selector));
        eventLooper.registEventHandler(EventType.READ, new ReadEventHandler(selector));
        new Thread(acceptor, "Acceptor-" + acceptor.getPort()).start();
        new Thread(() -> {
            while (true) {
                acceptor.addNewConnection(new InputSource("time:"+ System.currentTimeMillis(),
                        System.currentTimeMillis()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        eventLooper.handleEvents();
    }

    public static void main(String[] args) {
        Server server = new Server(8080);
        server.start();
    }
}
