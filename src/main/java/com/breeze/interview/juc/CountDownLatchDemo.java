package com.breeze.interview.juc;

import java.util.concurrent.CountDownLatch;

/**
 * juc多线程高并发工具包里面常用的工具类
 *      减少计数
 *          * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，这些线程会阻塞。
 *          * 其它线程调用countDown方法会将计数器减1(调用countDown方法的线程不会阻塞)，
 *          * 当计数器的值变为0时，因await方法阻塞的线程会被唤醒，继续执行。
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {

        //减
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国 被灭");
                countDownLatch.countDown();
            }, CountryEnum.foreach_CountryEnum(i).getRetMessage()).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t 秦帝国，统一华夏");

    }


    private static void closeDoor() {
        //减
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i < 7; i++) {
            new Thread(() -> {

                /*try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                System.out.println(Thread.currentThread().getName() + "\t 离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        try {
            //countDownLatch.await(3L, TimeUnit.SECONDS);
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "\t 锁门离开");
    }
}
