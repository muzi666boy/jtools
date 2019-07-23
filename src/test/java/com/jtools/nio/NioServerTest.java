// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

import org.junit.Test;

/**
 * The NioServerTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class NioServerTest {
    private static final int PORT = 8888;

    @Test
    public void testServer() throws IOException {
        ServerSocketChannel svr = ServerSocketChannel.open();
        svr.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), PORT));
        svr.configureBlocking(false);
        Selector selector = Selector.open();
        new Thread(new ReactorTask()).start();

    }
}
