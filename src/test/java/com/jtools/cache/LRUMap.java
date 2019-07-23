// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.cache;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class LRUMap<T> extends LinkedHashMap<String, SoftReference<T>> implements Externalizable {

    private static final long serialVersionUID = 1L;


    /** The maximum size of the cache. */
    private int maxCacheSize;

    /* lock for map */
    private final Lock lock = new ReentrantLock();

    /**
     * 默认构造函数，LRUMap的大小为Integer.MAX_VALUE
     */
    public LRUMap() {
      super();
      maxCacheSize = Integer.MAX_VALUE;
    }

    /**
     * Constructs a new, empty cache with the specified maximum size.
     */
    public LRUMap(int size) {
      super(size + 1, 1f, true);
      maxCacheSize = size;
    }

    /**
     * 让LinkHashMap支持LRU，如果Map的大小超过了预定值，则返回true，LinkedHashMap自身实现返回
     * fasle，即永远不删除元素
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<String, SoftReference<T>> eldest) {
      boolean tmp = (size() > maxCacheSize);
      return tmp;
    }

    public T addEntry(String key, T entry) {
      try {
        SoftReference<T> sr_entry = new SoftReference<T>(entry);
        // add entry to hashmap
        lock.lock();
        put(key, sr_entry);
      }
      finally {
        lock.unlock();
      }
      return entry;
    }

    public T getEntry(String key) {
      SoftReference<T> sr_entry;
      try {
        lock.lock();
        if ((sr_entry = get(key)) == null)
          return null;
        // if soft reference is null then the entry has been
        // garbage collected and so the key should be removed also.
        if (sr_entry.get() == null) {
          remove(key);
          return null;
        }
      }
      finally {
        lock.unlock();
      }
      return sr_entry.get();
    }

    @Override
    public SoftReference<T> remove(Object key) {
      try {
        lock.lock();
        return super.remove(key);
      }
      finally {
        lock.unlock();
      }
    }

    @Override
    public synchronized void clear() {
      super.clear();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
      Iterator<Map.Entry<String, SoftReference<T>>> i = (size() > 0) ? entrySet().iterator() : null;
      // Write out size
      out.writeInt(size());
      // Write out keys and values
      if (i != null) {
        while (i.hasNext()) {
          Map.Entry<String, SoftReference<T>> e = i.next();
          if (e != null && e.getValue() != null && e.getValue().get() != null) {
            out.writeObject(e.getKey());
            out.writeObject(e.getValue().get());
          }
        }
      }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      // Read in size
      int size = in.readInt();
      // Read the keys and values, and put the mappings in the Map
      for (int i = 0; i < size; i++) {
        String key = (String) in.readObject();
        @SuppressWarnings("unchecked")
        T value = (T) in.readObject();
        addEntry(key, value);
      }
    }

    public static void main(String[] args) {
        LRUMap cache = new LRUMap(10);
        for (int i = 0; i < 20; i ++) {
            cache.put(i, i);
            cache.get(20 - i);
        }

        for (int i = 0; i < 20; i ++) {
            System.out.println(i + ":" + cache.get(20 - i));
        }
    }
}
