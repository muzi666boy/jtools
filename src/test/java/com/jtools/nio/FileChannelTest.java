// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * The FileChannelTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class FileChannelTest {

    @Test
    public void testWrite() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("/Users/liwenwei/Downloads/test3.txt", "rw");
        FileChannel channel = aFile.getChannel();
        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        buf.flip();

        channel.position(10);
        while (buf.hasRemaining()) {
            channel.write(buf);
        }
        channel.force(true);
        channel.close();
    }

    @Test
    public void testTruncate() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("/Users/liwenwei/Downloads/test3.txt", "rw");
        FileChannel channel = aFile.getChannel();
        channel.truncate(20);
        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf = ByteBuffer.allocate(48);
        int readSize = channel.read(buf);
        while (readSize != -1) {
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print(buf.get());
            }
            buf.clear();
            readSize = channel.read(buf);
        }
        channel.close();
    }

    @Test
    public void testMappedByteBuffer() {
        String file = "/Users/liwenwei/Downloads/Sketch.zip";
        method3(file);
        System.out.println("=============");
        method4(file);
    }

    public static void method3(String file) {
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            aFile = new RandomAccessFile(file, "rw");
            fc = aFile.getChannel();
            long timeBegin = System.currentTimeMillis();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());
            // System.out.println((char)mbb.get((int)(aFile.length()/2-1)));
            // System.out.println((char)mbb.get((int)(aFile.length()/2)));
            //System.out.println((char)mbb.get((int)(aFile.length()/2)+1));
            long timeEnd = System.currentTimeMillis();
            System.out.println("Read time: " + (timeEnd - timeBegin) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void method4(String file) {
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            aFile = new RandomAccessFile(file, "rw");
            fc = aFile.getChannel();
            long timeBegin = System.currentTimeMillis();
            ByteBuffer buff = ByteBuffer.allocate((int) aFile.length());
            buff.clear();
            fc.read(buff);
            //System.out.println((char)buff.get((int)(aFile.length()/2-1)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)+1));
            long timeEnd = System.currentTimeMillis();
            System.out.println("Read time: " + (timeEnd - timeBegin) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
