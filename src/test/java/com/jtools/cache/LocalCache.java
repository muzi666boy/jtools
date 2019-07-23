// Copyright 2016 Baidu Inc. All rights reserved.

package com.jtools.cache;

import com.jtools.io.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Local cache.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class LocalCache{

    private final int size;
   /**
    * 本地缓存最大容量
    */
   static final int MAXIMUM_CAPACITY = 1 << 30;

   /**
    * 本地缓存支持最大的分区数
    */
   static final int MAX_SEGMENTS = 1 << 16; // slightly conservative

   /**
    * 本地缓存存储的LRUMap数组
    */
   LRUMap<Object>[] segments;

   /**
    * Mask value for indexing into segments. The upper bits of a key's hash
    * code are used to choose the segment.
    */
   int segmentMask;

   /**
    * Shift value for indexing within segments.
    */
   int segmentShift;

   /**
    * 
    * 计数器重置阀值
    */
   private static final int MAX_LOOKUP = 100000000;

   /**
    * 用于重置计数器的锁，防止多次重置计数器
    */
   private final Lock lock = new ReentrantLock();

   /**
    * Number of requests made to lookup a cache entry.
    */
   private AtomicLong lookup = new AtomicLong(0);

   /**
    * Number of successful requests for cache entries.
    */
   private AtomicLong found = new AtomicLong(0);
   
   public LocalCache(int size) throws Exception {
       this.size = size;
       init();
    }

    public Object get(String key) {
     if (StringUtils.isBlank(key)) {
       return null;
     }
     // 增加计数器
     lookup.incrementAndGet();

     // 如果必要重置计数器
     if (lookup.get() > MAX_LOOKUP) {
       if (lock.tryLock()) {
         try {
           lookup.set(0);
           found.set(0);
         }
         finally {
           lock.unlock();
         }
       }
     }

     int hash = hash(key.hashCode());
     Object ret = segmentFor(hash).getEntry(key);
     if (ret != null)
       found.incrementAndGet();
     return ret;
   }

   public void remove(String key) {
     if (StringUtils.isBlank(key)) {
       return;
     }
     int hash = hash(key.hashCode());
     segmentFor(hash).remove(key);
     return;
   }

   public void put(String key, Object val) {
     if (StringUtils.isBlank(key) || val == null) {
       return;
     }
     int hash = hash(key.hashCode());
     segmentFor(hash).addEntry(key, val);
     return;
   }

   public synchronized void clearCache() {
     for (int i = 0; i < segments.length; ++i)
       segments[i].clear();
   }

   public synchronized void reload() throws Exception {
      clearCache();
      init();
   }

   public synchronized void dumpLocalCache() throws Exception {
     for (int i = 0; i < segments.length; ++i) {
       String tmpDir = System.getProperty("java.io.tmpdir");
       String fileName = tmpDir + File.separator + "localCache-dump-file" + i + ".cache";
       File file = new File(fileName);
       ObjectUtils.objectToFile(segments[i], file);
     }
   }

   @SuppressWarnings("unchecked")
   public synchronized void restoreLocalCache() throws Exception {
     for (int i = 0; i < segments.length; ++i) {
       String tmpDir = System.getProperty("java.io.tmpdir");
       String fileName = tmpDir + File.separator + "localCache-dump-file" + i + ".cache";
       File file = new File(fileName);
       LRUMap<Object> lruMap = (LRUMap<Object>) ObjectUtils.fileToObject(file);
       if (lruMap != null) {
         Set<Entry<String, SoftReference<Object>>> set = lruMap.entrySet();
         Iterator<Entry<String, SoftReference<Object>>> it = set.iterator();
         while (it.hasNext()) {
           Entry<String, SoftReference<Object>> entry = it.next();
           if (entry.getValue() != null && entry.getValue().get() != null)
             segments[i].addEntry(entry.getKey(), entry.getValue().get());
         }
       }
     }
   }


   /**
    * 本地缓存命中次数，在计数器RESET的时刻可能会出现0的命中率
    */
   public int getHitRate() {
     long query = lookup.get();
     return query == 0 ? 0 : (int) ((found.get() * 100) / query);
   }

   /**
    * 本地缓存访问次数，在计数器RESET时可能会出现0的查找次数
    */
   public long getCount() {
     return lookup.get();
   }

   public int size() {
     final LRUMap<Object>[] segments = this.segments;
     long sum = 0;
     for (int i = 0; i < segments.length; ++i) {
       sum += segments[i].size();
     }
     if (sum > Integer.MAX_VALUE)
       return Integer.MAX_VALUE;
     else
       return (int) sum;
   }


   /**
    * Returns the segment that should be used for key with given hash
    * 
    * @param hash
    *            the hash code for the key
    * @return the segment
    */
   final LRUMap<Object> segmentFor(int hash) {
     return segments[(hash >>> segmentShift) & segmentMask];
   }


   /* ---------------- Small Utilities -------------- */

   /**
    * Applies a supplemental hash function to a given hashCode, which defends
    * against poor quality hash functions. This is critical because
    * ConcurrentHashMap uses power-of-two length hash tables, that otherwise
    * encounter collisions for hashCodes that do not differ in lower or upper
    * bits.
    */
   private static int hash(int h) {
     // Spread bits to regularize both segment and index locations,
     // using variant of single-word Wang/Jenkins hash.
     h += (h << 15) ^ 0xffffcd7d;
     h ^= (h >>> 10);
     h += (h << 3);
     h ^= (h >>> 6);
     h += (h << 2) + (h << 14);
     return h ^ (h >>> 16);
   }

   @SuppressWarnings("unchecked")
   public void init() throws Exception {
     int concurrencyLevel = 16;
     int capacity = size;
     if (capacity < 0 || concurrencyLevel <= 0)
       throw new IllegalArgumentException();
     if (concurrencyLevel > MAX_SEGMENTS)
       concurrencyLevel = MAX_SEGMENTS;
     // Find power-of-two sizes best matching arguments
     int sshift = 0;
     int ssize = 1;
     while (ssize < concurrencyLevel) {
       ++sshift;
       ssize <<= 1;
     }
     segmentShift = 32 - sshift;
     segmentMask = ssize - 1;
     this.segments = new LRUMap[ssize];
     if (capacity > MAXIMUM_CAPACITY)
       capacity = MAXIMUM_CAPACITY;
     int c = capacity / ssize;
     if (c * ssize < capacity)
       ++c;
     int cap = 1;
     while (cap < c)
       cap <<= 1;
     cap >>= 1;
     for (int i = 0; i < this.segments.length; ++i)
       this.segments[i] = new LRUMap<Object>(cap);
   }
 }