// Copyright 2014 Baidu Inc. All rights reserved.

package com.jtools.hash;

/**
 * @author Yue Jun(yuejun@baidu.com)
 */
public class HashHelper {
    /**
     * Thomas Wang hash function: http://burtleburtle.net/bob/hash/integer.html
     *
     * @param value The long value.
     * @return The hash result.
     */
    public static int hash(long value) {
        int key = (int) (value ^ (value >> 32));
        key += ~(key << 15);
        key ^= (key >> 10);
        key += (key << 3);
        key ^= (key >> 6);
        key += ~(key << 11);
        key ^= (key >> 16);
        return key;
    }

    /**
     * Hash a string. Copy from String.hashCode().
     *
     * @param value The string.
     * @return The hash value.
     */
    public static int hash(String value) {
        int h = 0;
        for (int i = 0; i < value.length(); i++) {
            h = 31 * h + value.charAt(i);
        }
        return h;
    }
}
