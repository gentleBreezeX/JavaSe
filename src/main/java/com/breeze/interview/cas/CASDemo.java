package com.breeze.interview.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. CAS 是什么？ ===> compareAndSet
 *      【比较并交换】
 *
 *  面试题：
 *    CAS ---> UnSafe ---> CAS底层原理 ---> ABA ---> 原子引用更新 ---> 如何规避ABA问题
 *
 *      如果期望值和主物理内存的值一样就修改，返回true
 *      如果期望值和主物理内存的值不一样就不修改，返回false
 *
 * 2. 底层原理：
 *      UnSafe类 + CAS思想(自旋锁)
 *
 * 3. CAS缺点:
 *      synchronized 一致性保证，并发性下降
 *      CAS ： 不加锁， 保证一致性，也保证并发性
 *      但是：1) 循环时间长，开销大
 *           2)  只能保证一个共享变量的原子操作
 *           3)  引出来ABA问题
 *
 * 4. ABA问题： 狸猫换太子
 *      比如：一个线程one从内存位置V取出A，这是另一线程two也从内存中取出A
 *          并且线程two进行了一些操作将值变成了B，然后线程two又将V位置的
 *          数据变成A，这时候线程one进行CAS操作发现内存中仍然是A，one线程操作成功
 *          尽管线程one的CAS操作成功，但是不代表这个线程就是没问题的
 *
 * 5. 原子引用见AtomicReferenceDemo类
 *
 * 6. 时间戳的原子引用：见AtomicStampedReferenceDemo
 *      怎么解决ABA问题？  AtomicStampedReference类
 *
 *
 */
public class CASDemo {
    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(5);

        //main do thing...


        System.out.println(atomicInteger.compareAndSet(5, 10)
                + "\tcurrent atomicInteger is: " + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 2019)
                + "\tcurrent atomicInteger is: " + atomicInteger.get());

        //看源码
        atomicInteger.getAndIncrement();
    }
}
