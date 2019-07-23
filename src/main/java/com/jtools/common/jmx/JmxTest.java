// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.common.jmx;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import lombok.Data;

/**
 * The JmxTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
@Data
public class JmxTest implements JmxTestMBean {
    @Override
    public String sayHello() {
        System.out.println("hello world");
        return "hello";
    }

    public static void main(String[] args)
            throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException,
            MBeanRegistrationException {
        // 创建MBeanServer
        MBeanServer server = MBeanServerFactory.createMBeanServer();

        // 为MBean创建ObjectName
        ObjectName objName = new ObjectName("jtools:name=jmxTest");

        // 注册MBean到Server中
        server.registerMBean(new JmxTest(), objName);

        // 表现MBean(一种方式)
        ObjectName adapterName = new ObjectName("ControlImpl:name=htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        server.registerMBean(adapter, adapterName);

        adapter.start();

    }
}
