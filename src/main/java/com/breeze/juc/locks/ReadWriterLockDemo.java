package com.breeze.juc.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache {
    private volatile Map<String, String> map = new HashMap<>();

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void put(String key, String value) {

        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入开始");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入结束");
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key) {

        readWriteLock.readLock().lock();
        String result;
        try {
            System.out.println(Thread.currentThread().getName() + "读取开始");
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取结束result：" + result);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 * 读写锁
 */
public class ReadWriterLockDemo {
    public static void main(String[] args) {

        MyCache myCache = new MyCache();

        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI + "", finalI + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + "");
            }, String.valueOf(i)).start();
        }
    }
}
