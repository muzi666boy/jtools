// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.image;

import com.jtools.common.image.OperateImage;
import org.junit.Test;

/**
 * The ImageIoTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class ImageIoTest {

    private static final String SRC_IMAGE = "http://ms.bdimg.com/pacific/nvwa/a6f7bba5e14219b817432eca3455f5a0.jpg";

    @Test
    public void testBigger() {
        try {
            //获取图片
            OperateImage operateImage = new OperateImage();
            operateImage.enlargementImageByRatio("/Users/liwenwei/tmp/badimg.jpg",
                    "/Users/liwenwei/tmp/badimg_big.jpg", 2 ,2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
