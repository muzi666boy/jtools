// Copyright 2019 Baidu Inc. All rights reserved.
package com.jtools.hash;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The ConsistentHasherTest.
 *
 * @Author Li Wenwei(liwenwei@baidu.com)
 */
public class ConsistentHasherTest {

    private ConsistentHasher<String> hasher = null;

    @Before
    public void setUp() {
        hasher = new ConsistentHasher<String>(99, Lists.newArrayList("a", "b", "c"));
    }

    @Test
    public void testAdd() {
        Assert.assertEquals("a", hasher.get(1));
        hasher.add("d");
        Assert.assertEquals("d", hasher.get(1));
        hasher.remove("d");
        Assert.assertEquals("a", hasher.get(1));
        hasher.add("e");
        Assert.assertEquals("a", hasher.get(1));
        System.out.println(hasher.toString());
    }
}
