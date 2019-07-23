// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * The PipeTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class PipeTest {

    @Test
    public void testSinkChannel() throws IOException {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink();

        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        while(buf.hasRemaining()) {
            sinkChannel.write(buf);
        }
        Pipe.SourceChannel sourceChannel = pipe.source();
        buf.clear();
        int bytesRead = sourceChannel.read(buf);
        buf.flip();
        byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);
        System.out.println("receive:" + new String(bytes, "UTF-8"));

    }
}
