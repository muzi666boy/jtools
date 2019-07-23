// Copyright 2018 Baidu Inc. All rights reserved.
package com.jtools.cache;

import lombok.Data;

/**
 * The DataObject.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
@Data
public class DataObject {

    private final String data;

    private static int objectCounter = 0;

    DataObject(String data) {
        this.data = data;
    }
    // standard constructors/getters

    public static DataObject get(String data) {
        objectCounter++;
        return new DataObject(data);
    }
}
