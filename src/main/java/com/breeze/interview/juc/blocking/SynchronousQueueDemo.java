package com.breeze.interview.juc.blocking;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列SynchronousQueue的演示
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {

        SynchronousQueue<String> sQueue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                sQueue.put("1");

                System.out.println(Thread.currentThread().getName() + "\t put 2");
                sQueue.put("2");

                System.out.println(Thread.currentThread().getName() + "\t put 3");
                sQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                //暂停一会儿
                try {TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + "\t" + sQueue.take());

                try {TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + "\t" + sQueue.take());

                try {TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName() + "\t" + sQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();


    }
}
