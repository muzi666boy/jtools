// Copyright 2015 Baidu Inc. All rights reserved.

package com.jtools.hash;

import com.jtools.common.concurrent.NotThreadSafe;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The implementation for consistent hash.
 *
 * @param <T> The target type.
 * @author Alex Lu (lufei@baidu.com)
 */
@NotThreadSafe
public class ConsistentHasher<T> {

    private static final String SPLIT = "#$%$#";

    private final int numberOfReplicas;
    private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();

    public ConsistentHasher(int numberOfReplicas, List<T> nodes) {
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * Add node to consistent hasher.
     *
     * @param node The node.
     */
    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(innerHash(node, i), node);
        }
    }

    /**
     * Remove node.
     *
     * @param node The node.
     */
    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(innerHash(node, i));
        }
    }

    /**
     * Acquire target according to the value.
     *
     * @param value The value to acquire target.
     * @return The target object.
     */
    public T get(long value) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = HashHelper.hash(value);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    private Integer innerHash(T node, int i) {
        return HashHelper.hash(DigestUtils.md5Hex(node.toString() + SPLIT + i).hashCode());
    }

    @Override
    public String toString() {
        return circle.toString();
    }
}
