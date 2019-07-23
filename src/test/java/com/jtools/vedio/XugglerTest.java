// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.vedio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

/**
 * The XugglerTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class XugglerTest {
    private static final int DEFAULT_VIDEO_DURATION = 30;

    @Test
    public void testRead() throws FileNotFoundException, InterruptedException {
        long startTime = System.currentTimeMillis();
        IContainer container = IContainer.make();
        int result = container.open(new FileInputStream(ResourceUtils.getFile("classpath:vedio/Google.mp4")), null);
//        int result = container.open("/Users/liwenwei/Downloads/Google.mp4", Type.READ, null);
        if (result < 0) {
            System.out.println("result: " + result);
            return;
        }
        // query how many streams the call to open found
        int numStreams = container.getNumStreams();
        // query for the total duration
        long duration = container.getDuration();
        // query for the file size
        long fileSize = container.getFileSize();
        long secondDuration = duration/1000000;

        System.out.println("时长："+secondDuration+"秒");
        System.out.println("文件大小:"+fileSize+"M");


        for (int i=0; i < numStreams; i++) {
            IStream stream = container.getStream(i);
            IStreamCoder coder = stream.getStreamCoder();
            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                System.out.println("视频宽度：" + coder.getWidth());
                System.out.println("视频高度：" + coder.getHeight());
            }
        }
        System.out.println("bitrate: " + container.getBitRate());
        System.out.println("bitrate: " + container.getMyCPtr());
        System.out.println("duration: " + duration);
//        Thread.sleep(1000);
        System.out.println("cost: " + (System.currentTimeMillis() - startTime));
    }
}
