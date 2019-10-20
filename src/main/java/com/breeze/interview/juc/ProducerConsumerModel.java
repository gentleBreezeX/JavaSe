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
 *      2.使用方法
 *          synchronized 不需要用户手动释放锁，将synchronized代码执行
 *              完后系统会自动让线程释放对锁的占用
 *          ReentrantLock则需要用户手动释放锁，若没有主动释放锁，可能死锁
 *              需要lock()和unlock()方法配合try/finally语句块完成
 *      3.等待是否可中断
 *          synchronized不可中断，除非抛出异常或者正常运行完成
 *          ReentrantLock 可中断，1) 设置超时方法 tryLock(long timeout, TimeUnit unit)
 *                               2) LockInterruptibly()放代码块中，调用interrupt()方法可以中断
 *      4.加锁是否公平
 *          synchronized 非公平锁
 *          ReentrantLock两者都可以，默认是非公平锁，构造方法可以传入Boolean值，
 *              true为公平锁，false为非公平锁
 *      5.锁绑定多个条件Condition
 *          synchronized没有
 *          ReentrantLock用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是
 *              像synchronized要么随机唤醒一个线程要么唤醒全部线程
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
