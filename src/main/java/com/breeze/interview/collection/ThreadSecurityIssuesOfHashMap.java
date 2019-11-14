package com.breeze.interview.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap的线程安全问题:
 *
 */
public class ThreadSecurityIssuesOfHashMap {
    public static void main(String[] args) {

        //Map<String, String> map = new HashMap<>();
        //Map<String, String> map1 = Collections.synchronizedMap(new HashMap<>());
        Map<String, String> map = new ConcurrentHashMap<>();

        //开启30条线程对集合进行操作
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 4));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
