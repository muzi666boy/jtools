// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.common.monitor;


import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

/**
 * The SystemMonitor.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class SystemMonitor2 {

    public static void main(String[] argus) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        System.out.println(osBean.getProcessCpuLoad());//指CPU的负载情况
        System.out.println(osBean.getProcessCpuTime());//指CPU的负载情况
        System.out.println(osBean.getFreeSwapSpaceSize());//指CPU的负载情况
// What % CPU load this current JVM is taking, from 0.0-1.0
        System.out.println(osBean.getSystemLoadAverage());//指CPU的负载情况
// What % CPU load this current JVM is taking, from 0.0-1.0
        System.out.println(osBean.getAvailableProcessors());//指CPU的负载情况
// What % CPU load this current JVM is taking, from 0.0-1.0
        System.out.println(osBean.getArch());//指CPU的负载情况
        System.out.println(osBean.getName());//指CPU的负载情况
        System.out.println(osBean.getVersion());//指CPU的负载情况
        System.out.println(osBean);//指CPU的负载情况
    }
}
