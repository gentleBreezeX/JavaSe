package com.breeze.juc.utils;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * juc多线程高并发工具包里面常用的工具类
 *      信号灯
 *      在信号量上我们定义两种操作：
 *          * acquire（获取） 当一个线程调用acquire操作时，它要么通过成功获取信号量（信号量减1），
 *          * 要么一直等下去，直到有线程释放信号量，或超时。
 *          * release（释放）实际上会将信号量的值加1，然后唤醒等待的线程。
 *          * 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制。
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //信号
        Semaphore semaphore = new Semaphore(3);//模拟三个停车位

        for (int i = 1; i < 6; i++) {
            new Thread(() -> {
                boolean flag = false;
                try {
                    semaphore.acquire();
                    flag = true;
                    System.out.println(Thread.currentThread().getName() + "\t驶进车位");
                    try {
                        TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t======驶离车位====");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    if (flag) {
                        semaphore.release();
                    }
                }
            }, String.valueOf(i)).start();

        }

    }
}
