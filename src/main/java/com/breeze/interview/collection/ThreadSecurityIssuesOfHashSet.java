package com.breeze.interview.collection;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * HashSet线程安全问题：
 *      1.底层是HashMap
 *          public HashSet() {
 *              map = new HashMap<>();
 *          }
 *      2.add方法：
 *          private static final Object PRESENT = new Object();
 *          public boolean add(E e) {
 *              return map.put(e, PRESENT)==null;
 *          }
 *
 */
public class ThreadSecurityIssuesOfHashSet {
    public static void main(String[] args) {

        //Set<String> set = new HashSet<>();
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();

        //开启30条线程对集合进行操作
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 4));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }

    }
}
