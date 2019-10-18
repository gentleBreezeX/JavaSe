package com.breeze.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class AirConditioner{//模拟空调资源类
    private int temperature = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception {
        lock.lock();
        //1. 判断
        try {
            while (temperature != 0) {
                condition.await();
            }
            //2. 干活
            temperature++;
            System.out.println(Thread.currentThread().getName() + "\t" + temperature);
            //3. 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws Exception {
        lock.lock();
        //1. 判断
        try {
            while (temperature == 0) {
                condition.await();
            }
            //2. 干活
            temperature--;
            System.out.println(Thread.currentThread().getName() + "\t" + temperature);
            //3. 通知
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

/*
    //传统模式
    public synchronized void increment() throws Exception {
        //1. 判断
        while (temperature != 0) {
            this.wait();
        }
        //2. 干活
        temperature++;
        System.out.println(Thread.currentThread().getName() + "\t" + temperature);
        this.notifyAll();
    }

    public synchronized void decrement() throws Exception {
        //1. 判断
        while (temperature == 0) {
            this.wait();
        }
        //2. 干活
        temperature--;
        System.out.println(Thread.currentThread().getName() + "\t" + temperature);
        this.notifyAll();
    }
*/

}

/*
 * 题目：现在两个线程，可以操作初始值为零的一个变量，
 * 实现一个线程对该变量加1，一个线程对该变量减1，
 * 实现交替，来10轮，变量初始值为零。
 *
 *  1. 在高内低耦的前提下，线程操作资源类
 *  2. 判断/干活/通知
 *  3. 小心，防止多线程虚假唤醒，判断的时候用while不用if
 *  4.
 *
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {

        AirConditioner airConditioner = new AirConditioner();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();

    }
}
