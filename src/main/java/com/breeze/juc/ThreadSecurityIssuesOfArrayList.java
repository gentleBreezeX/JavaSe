package com.breeze.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ArrayList的线程安全问题
 *      1.new ArrayList(); 的时候是 Object类型的数组，初始长度是空
 *      2.当第一次add时，初始长度变成10
 *      3.ArrayList一半扩容，第一次15 第二次22  底层是先Arrays.copyOf()原先的数组,再去add
 *      4.在确定数据长度的时候，一般使用new ArrayList(int initialCapacity);
 *      5.线程不安全的，会出现ConcurrentModificationException
 *      6.解决线程不安全的问题：
 *          --> 使用Collections.synchronizedList()解决线程安全问题
 *          --> 使用CopyOnWriteArrayList解决线程安全问题【重点】---> 【读写分离，写时复制】
 *          --> 使用new Vector(); (基本上不采取 效率低)
 *      7.[写时复制]
 *          CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，
 *          而是先将当前容器Object[]进行Copy，复制出一个新的容器Object[] newElements，
 *          然后新的容器Object[] newElements里添加元素，添加完元素之后，再将原容器的引用指向新的容器
 *          setArray(newElements);。这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，
 *          因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器
 *
 *        [CopyOnWriteArrayList 底层的add方法实现]
 *          public boolean add(E e) {
 *              final ReentrantLock lock = this.lock;
 *              lock.lock();
 *              try {
 *                  Object[] elements = getArray();
 *                  int len = elements.length;
 *                  Object[] newElements = Arrays.copyOf(elements, len + 1);
 *                  newElements[len] = e;
 *                  setArray(newElements);
 *                  return true;
 *              } finally {
 *                  lock.unlock();
 *              }
 *          }
 */
public class ThreadSecurityIssuesOfArrayList {
    public static void main(String[] args) {
        //演示ArrayList线程安全问题
        List list = new ArrayList();

        //开启30条线程对集合进行操作
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 4));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}

class SolveByCopyOnWriteArrayList{
    public static void main(String[] args) {
        //使用CopyOnWriteArrayList解决线程安全问题
        List list = new CopyOnWriteArrayList();

        //开启30条线程对集合进行操作
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 4));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}

class SolveBySynchronizedList{
    public static void main(String[] args) {
        //使用Collections.synchronizedList解决线程安全问题
        List list = Collections.synchronizedList(new ArrayList<>());

        //开启30条线程对集合进行操作
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 4));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}

