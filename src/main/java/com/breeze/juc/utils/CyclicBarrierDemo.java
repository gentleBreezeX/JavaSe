package com.breeze.juc.utils;

import java.util.concurrent.CyclicBarrier;

/**
 * juc多线程高并发工具包里面常用的工具类
 *      循环栅栏
 *          * CyclicBarrier
 *          * 的字面意思是可循环（Cyclic）使用的屏障（Barrier）。它要做的事情是，
 *          * 让一组线程到达一个屏障（也可以叫同步点）时被阻塞，
 *          * 直到最后一个线程到达屏障时，屏障才会开门，所有
 *          * 被屏障拦截的线程才会继续干活。
 *          * 线程进入屏障通过CyclicBarrier的await()方法。
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //加
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,
                () -> System.out.println(Thread.currentThread().getName() + "\t集齐7龙珠，可以召唤神龙"));

        for (int i = 1; i < 8; i++) {
            int finalI = i;
            new Thread(() -> {

                System.out.println(Thread.currentThread().getName() + "\t收集第：" + finalI + "\t颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();

        }
    }
}
