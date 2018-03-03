package com.jsmsframework.sms.send.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @param <K>
 * @param <V>
 * @author dylan
 */
public class JsmsCacheMap<K, V> extends AbstractMap<K, V> {
    private Logger logger = LoggerFactory.getLogger(JsmsCacheMap.class);

    private static final long DEFAULT_TIMEOUT = 180000; // 180秒
    private static JsmsCacheMap<Object, Object> defaultInstance;

    public static final JsmsCacheMap<Object, Object> getDefault() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            if (defaultInstance == null) {
                defaultInstance = new JsmsCacheMap<Object, Object>(DEFAULT_TIMEOUT);
            }
        } finally {
            lock.unlock();
        }
        return defaultInstance;
    }

    private class CacheEntry implements Entry<K, V> {
        long time;
        V value;
        K key;

        CacheEntry(K key, V value) {
            super();
            this.value = value;
            this.key = key;
            this.time = System.currentTimeMillis();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }
    }

    private class ClearThread extends Thread {
        ClearThread() {
            setName("clear cache thread");
            setDaemon(true);
        }

        public void run() {
            while (true) {
                try {
                    long now = System.currentTimeMillis();
                    Object[] keys = map.keySet().toArray();
                    for (Object key : keys) {
                        CacheEntry entry = map.get(key);
                        long t = (now - entry.time) - cacheTimeout;
                        if (t >= 0) {
                            map.remove(key);
                        }
                    }
                    Thread.sleep(cacheTimeout);
                } catch (Exception e) {
                    logger.error("缓存异常 ---> {}", e);
                }
            }
        }
    }

    private long cacheTimeout;
    private Map<K, CacheEntry> map = new ConcurrentHashMap<K, CacheEntry>();

    public JsmsCacheMap(long timeout) {
        this.cacheTimeout = timeout;
        new ClearThread().start();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<Entry<K, V>>();
        Set<Entry<K, CacheEntry>> wrapEntrySet = map.entrySet();
        for (Entry<K, CacheEntry> entry : wrapEntrySet) {
            entrySet.add(entry.getValue());
        }
        return entrySet;
    }

    @Override
    public V get(Object key) {
        CacheEntry entry = map.get(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public V put(K key, V value) {
        CacheEntry entry = new CacheEntry(key, value);
        map.put(key, entry);
        return value;
    }
}
