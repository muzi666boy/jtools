// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.jdk8;

/**
 * The Curry.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class Closure {
    public  String version="";

    public static void main(String[] args) {
        Closure c1=new Closure();
        c1.version="1.0";
        Moment m1=c1.getMoment();
        System.out.println(m1.getVersion());

        c1.version="2.0";
        Moment m2=c1.getMoment();
        System.out.println(m2.getVersion());
    }

    public Moment getMoment(){
        return () -> {
            System.out.println(version);
            return version;
        };
    }

}

interface Moment{
    String getVersion();
}