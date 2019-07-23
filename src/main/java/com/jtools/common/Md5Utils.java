/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.jtools.common;

import java.security.MessageDigest;

/**
 * Created by xiangyuanfei on 17/4/25.
 */
public class Md5Utils {

    public static final String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < bits.length;i++) {
                int a = bits[i];
                if (a < 0) { a += 256; }
                if (a < 16) { buf.append("0"); }
                buf.append(Integer.toHexString(a));
            }
            return buf.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
