package com.breeze.interview.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void add(){
        lock.lock();
        try {
            while (number != 0) {
                try {condition.await(); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void sub(){
        lock.lock();
        try {
            while (number != 1) {
                try {condition.await(); } catch (InterruptedException e) { e.printStackTrace(); }
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "\t" + number);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

/*
    //传统版本
    public synchronized void add(){
        while (number != 0) {
            try {this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        this.notifyAll();
    }

    public synchronized void sub(){
        while (number != 1) {
            try {this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "\t" + number);
        this.notifyAll();
    }
*/


}

/**
 * 生产者消费者模式
 *
 * synchronized和lock有什么区别？ 用新的lock有什么好处
 *      1.原始构造
 *          synchronized是关键字属于JVM层面的
 *              monitorenter(底层是通过monitor对象来完成，其实wait/notify
 *                     等方法也依赖于monitor对象，所以只有在synchronized修饰
 *                     的同步块或方法中才能调用wait/notify等方法)
 *              monitorexit
 *          Lock是具体类(java.concurrent.locks.lock)是api层面的锁
 *
 */
public class ProducerConsumerModel {
    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) shareData.add();
        }, "AAA").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) shareData.sub();
        }, "BBB").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) shareData.add();
        }, "CCC").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) shareData.sub();
        }, "DDD").start();

    }
}
