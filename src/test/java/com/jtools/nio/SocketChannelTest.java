// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * The SocketChannelTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class SocketChannelTest {

    @Test
    public void testClient() throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 9999));
        while (!socketChannel.finishConnect()) {
            //wait, or do something else...
            Thread.sleep(1000);
        }

        ByteBuffer buf = ByteBuffer.allocate(100);
        String newData = "New String to write to file..." + System.currentTimeMillis();
        buf.put(newData.getBytes());
        buf.flip();
        while (buf.hasRemaining()) {
            socketChannel.write(buf);
        }
        buf.clear();

        Thread.sleep(2000);
        socketChannel.read(buf);
        buf.flip();
        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);
        System.out.println(new String(bytes));
        System.out.println(buf);

        socketChannel.close();
    }

    @Test
    public void testServer() throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        ByteBuffer buf = ByteBuffer.allocate(100);
        while (true) {
            System.out.println("start:");
            SocketChannel socketChannel =
                    serverSocketChannel.accept();
            System.out.println("Accept request:" + socketChannel.getRemoteAddress());
            buf.clear();
            socketChannel.read(buf);
            buf.flip();
            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            System.out.print(new String(bytes));
            Thread.sleep(1000);
            String newData = "We have received ..." + System.currentTimeMillis();
            buf.clear();
            buf.put(newData.getBytes());
            buf.flip();
            socketChannel.write(buf);

            buf.clear();
            buf.put(newData.getBytes());
            buf.flip();
            socketChannel.write(buf);
            socketChannel.close();
        }

    }

    @Test
    public void testDatagramChannelClient() throws IOException, InterruptedException {
        final DatagramChannel channel = DatagramChannel.open();
        //接收消息线程
        new Thread(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            byte b[];
            while (true) {
                buffer.clear();
                SocketAddress socketAddress = null;
                try {
                    socketAddress = channel.receive(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (socketAddress != null) {
                    buffer.flip();
                    b = new byte[buffer.remaining()];
                    buffer.get(b);
                    System.out.println("receive remote " + socketAddress.toString() + ":" + new String(b, Charset.forName("UTF-8")));
                }
            }
        }).start();

        //发送控制台输入消息
        sendMessage(channel, "New String to write to file..." + System.currentTimeMillis());
        Thread.sleep(1000);
    }

    @Test
    public void testDatagramChannelServer() throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(9998));
        ByteBuffer buf = ByteBuffer.allocate(48);
        while (true) {
            buf.clear();
            SocketAddress socketAddress = channel.receive(buf);

            buf.flip();
            byte[] bytes = new byte[buf.remaining()];
            buf.get(bytes);
            System.out.println("receive remote " + socketAddress.toString() + ":" + new String(bytes, "UTF-8"));
            sendReback(socketAddress, channel);
        }
    }

    public static void sendReback(SocketAddress socketAddress, DatagramChannel datagramChannel) throws IOException {
        String message = "I has receive your message";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(message.getBytes("UTF-8"));
        buffer.flip();
        datagramChannel.send(buffer, socketAddress);
    }

    public static void sendMessage(DatagramChannel channel, String mes) throws IOException {
        if (mes == null || mes.isEmpty()) {
            return;
        }
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        buffer.put(mes.getBytes("UTF-8"));
        buffer.flip();
        System.out.println("send msg:" + mes);
        int send = channel.send(buffer, new InetSocketAddress("localhost", 9998));
    }
}
