// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.netty;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * The SelectorTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class SelectorTest {
    private static final int PORT = 8080;

    @Test
    public void testServerSelect() throws IOException, InterruptedException {
        Selector selector = Selector.open();
        ServerSocketChannel servChannel = ServerSocketChannel.open();
        servChannel.configureBlocking(false);
        servChannel.socket().bind(new InetSocketAddress(PORT), 1024);
        servChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            System.out.println("select");
            selector.select(1000);
            System.out.println("select done");
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                    System.out.println("isAcceptable" + key.toString());

                    // Accept the new connection
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    // Add the new connection to the selector
                    sc.register(selector, SelectionKey.OP_READ);
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                    System.out.println("isConnectable" + key.toString());
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                    System.out.println("isReadable" + key.toString());
                    // Read the data
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");
                        System.out.println("The time server receive order : "
                                + body);
                        String currentTime = "QUERY TIME ORDER"
                                .equalsIgnoreCase(body) ? new java.util.Date(
                                System.currentTimeMillis()).toString()
                                : "BAD ORDER";
                        doWrite(sc, currentTime);
                    } else if (readBytes < 0) {
                        // 对端链路关闭
                        key.cancel();
                        sc.close();
                    } else {
                        ; // 读到0字节，忽略
                    }
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                    System.out.println("isWritable" + key.toString());
                }
                keyIterator.remove();
                Thread.sleep(1000);
            }
        }
    }

    @Test
    public void testClientSelect() throws IOException, InterruptedException {
        new Thread(new TimeClientHandle("127.0.0.1", 8080), "TimeClient-001")
                .start();
        Thread.sleep( 1000);
    }


    private void doWrite(SocketChannel channel, String response)
            throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}
