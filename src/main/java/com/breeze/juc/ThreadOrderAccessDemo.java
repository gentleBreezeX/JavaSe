package com.breeze.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource{//模拟订单的资源类
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
            while (flag != 1) {
                c1.await();
            }
            for (int i = 1; i < 6; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            flag = 2;
            c2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            while (flag != 2) {
                c2.await();
            }
            for (int i = 1; i < 11; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            flag = 3;
            c3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            while (flag != 3) {
                c3.await();
            }
            for (int i = 1; i < 16; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            flag = 1;
            c1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 *
 * AA打印5次，BB打印10次，CC打印15次
 * 接着
 * AA打印5次，BB打印10次，CC打印15次
 * ......来10轮
 *
 * 1.在高内低耦的前提下，线程操作资源类
 * 2.判断/干活/通知
 * 3.小心，防止多线程的虚假唤醒，使用while判断不用if
 * 4.注意判断标志位更新
 *
 */
public class ThreadOrderAccessDemo {
    public static void main(String[] args) {

        ShareResource order = new ShareResource();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                order.print5();
            }
        }, "ThreadCommunication").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                order.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                order.print15();
            }
        }, "C").start();
    }
}
