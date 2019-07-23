// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * The ChannelTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class BufferTest {

    @Test
    public void testByteBuffer() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("/Users/liwenwei/Downloads/3093302.csv", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buf.flip();

            while(buf.hasRemaining()){
                System.out.print((char) buf.get());
            }

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    @Test
    public void testWriteBuffer() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("/Users/liwenwei/Downloads/3093302.csv", "rw");
        RandomAccessFile bFile = new RandomAccessFile("/Users/liwenwei/Downloads/test2.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        FileChannel outChannel = bFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);
        ByteBuffer bbuf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            buf.flip();

            while(buf.hasRemaining()){
                System.out.print((char) buf.get());
            }
            buf.rewind();
            while(buf.hasRemaining()){
                bbuf.put(buf.get());
            }
            bbuf.flip();
            outChannel.write(bbuf);
            bbuf.clear();
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

    @Test
    public void testRewind() throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.put("社会主义好".getBytes());
        System.out.println(buf);
        buf.flip();
        System.out.println(buf);
        while(buf.hasRemaining()){
            System.out.print((char) buf.get());
        }
        System.out.println();
        System.out.println(buf);
        buf.rewind();
        System.out.println(buf);
        while(buf.hasRemaining()){
            System.out.print((char) buf.get());
        }
    }

    @Test
    public void testMark() throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.put("shehuizhuyihao".getBytes());
        System.out.println(buf);
        buf.flip();
        System.out.println(buf);
        buf.get();
        buf.get();
        buf.mark();
        while(buf.hasRemaining()){
            System.out.print((char) buf.get());
        }
        buf.reset();
        System.out.println();
        System.out.println(buf);
        while(buf.hasRemaining()){
            System.out.print((char) buf.get());
        }
        System.out.println();
        System.out.println(buf);
        buf.rewind();
        System.out.println(buf);
        while(buf.hasRemaining()){
            System.out.print((char) buf.get());
        }
    }

    @Test
    public void testEqual() throws IOException {
        ByteBuffer abuf = ByteBuffer.allocate(48);
        abuf.put("ashehuizhuyihao".getBytes());
        ByteBuffer bbuf = ByteBuffer.allocate(48);
        bbuf.put("bshehuizhuyihao".getBytes());
        System.out.println(abuf.equals(bbuf));
        abuf.flip();
        bbuf.flip();
        System.out.println(abuf.equals(bbuf));
        abuf.get();
        bbuf.get();
        System.out.println(abuf.equals(bbuf));
    }

    @Test
    public void testScatter() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("/Users/liwenwei/Downloads/3093302.csv", "rw");
        FileChannel channel = aFile.getChannel();
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };

        channel.read(bufferArray);
        header.flip();
        while(header.hasRemaining()){
            System.out.print((char) header.get());
        }
        body.flip();
        while(body.hasRemaining()){
            System.out.print((char) body.get());
        }
        channel.close();
    }

}
