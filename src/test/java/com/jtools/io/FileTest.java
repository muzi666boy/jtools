// Copyright 2017 Baidu Inc. All rights reserved.

package com.jtools.io;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import java.io.File;

/**
 * com.baidu.ecom.prodb.patools.io
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class FileTest {

    private static final String DIR = "/abc/bce/ced";
    private static final String DIR2 = "abc/bce/ced/";
    @Test
    public void testMkdirs() {
        File dir = new File(DIR);
        System.out.println(dir.mkdirs());
        System.out.println(dir.mkdirs());
        System.out.println(FilenameUtils.concat("/home/work", DIR));
        System.out.println(FilenameUtils.concat("/home/work", DIR2));
    }
}
