// Copyright 2015 Baidu Inc. All rights reserved.

package com.jtools.hash;

import com.google.common.collect.Lists;
import com.jtools.common.concurrent.NotThreadSafe;

import java.util.Collection;
import java.util.List;

/**
 * The consistent server selector.
 *
 * @author Alex Lu (lufei@baidu.com)
 */
@NotThreadSafe
public class ConsistentServerSelector {

    private List<String> urls;
    private ConsistentHasher<String> hasher;

    public ConsistentServerSelector(int numberOfReplicas, List<String> urls) {
        this.urls = Lists.newArrayList(urls);
        hasher = new ConsistentHasher<String>(numberOfReplicas, urls);
    }

    /**
     * Select server.
     *
     * @param userId The user ID.
     * @return The selected server url.
     * @throws McException If any error occurs.
     */
    public String selectServer(long userId) throws Exception {
        return hasher.get(userId);
    }

    /**
     * Get sava server list.
     *
     * @return The sava server list.
     * @throws McException If any error occurs.
     */
    public List<String> getServers() {
        return urls;
    }

    /**
     * Remove a url.
     *
     * @param urls The url list to remove.
     */
    public void remove(Collection<String> urls) {
        this.urls.removeAll(urls);
        for (String url : urls) {
            hasher.remove(url);
        }
    }

    /**
     * Whether selector is empty.
     *
     * @return True if selector is empty else false.
     */
    public boolean isEmpty() {
        return urls.isEmpty();
    }
}
