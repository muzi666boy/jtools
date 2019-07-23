// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.copy;

import com.jtools.common.json.JsonConverter;

import java.util.Date;

/**
 * The JsonDeepCopy.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class JsonDeepCopy {

    public static  <T> T copy(T src) {
        String srcJson = JsonConverter.serialize(src);
        return (T) JsonConverter.deserialize(srcJson, src.getClass());
    }

    public static void main(String[] args) {
        Date d1 = new Date();
        SAPUser src = new SAPUser("zhangsan","111111",d1,d1);
        src.setRole(new SAPRole("admin"));
        SAPUser dest = copy(src);
        System.out.println(src);
        System.out.println(dest);
        src.getRole().setRolename("lalalal");
        System.out.println(src);
        System.out.println(dest);
    }
}
