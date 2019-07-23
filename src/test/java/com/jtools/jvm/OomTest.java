// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.jvm;

import java.util.ArrayList;

/**
 * The OomTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class OomTest {

    // -XX:InitialHeapSize=4194304 -XX:InitialTenuringThreshold=6 -XX:MaxHeapSize=4194304 -XX:MaxNewSize=2097152 -XX:MaxTenuringThreshold=6 -XX:NewSize=2097152 -XX:OldPLABSize=16 -XX:OnOutOfMemoryError=/Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/bin/jmap -F -dump:format=b,file=java_pid%p.hprof %p; chmod +r java_pid%p.hprof -XX:+PrintCommandLineFlags -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintTenuringDistribution -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
    public static void main(String[] args) {
        System.out.println("test start...");
        ArrayList<byte[]> a = new ArrayList<byte[]>();
        int num = 0;
        while (num++ < 10) {
            a.add(new byte[1024 * 1024]);
            System.out.println("添加" + num + "M");
        }
        System.out.println("test end...");
    }
}
