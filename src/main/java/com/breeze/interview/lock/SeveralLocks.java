package com.breeze.interview.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.公平锁：是指多个线程按照申请锁的顺序来获取，列斯排队打饭，先来后到
 * 2.非公平锁：是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后
 *          申请的线程比先申请的线程优先获取锁，在高并发的情况下，有
 *          可能会造成优先级【反转或饥饿现象】
 *
 * 3.ReentrantLock，可以通过构造方法指定该锁是否是公平锁，默认非公
 *      非公平锁的有点在于吞吐量比公平锁大
 * 4.Synchronized，也是一种非公平锁
 *
 * 5.可重入锁(也叫递归锁)：指的是同一线程外层函数获得锁之后，内层递归函数
 *                      仍然能获取该锁的代码，在同一个线程的外层方法获取
 *                      锁的时候，在进入内层方法会自动获取锁
 *       即 线程可以进入任何一个它已经拥有的锁所同步着的代码块
 *      ReentrantLock和Synchronized是典型的非公平的可重入锁
 *      所用：防止死锁
 *
 * 6.自旋锁：是指尝试获取锁的线程不会立即阻塞，而是【采用循环的方式去尝试获取锁】
 *          这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU
 *
 */
public class SeveralLocks {
    public static void main(String[] args) {

        /**
         * 运行结果
         * A	invoked sendSMS   在同一个线程的外层方法获取锁的时候
         * A	invoked sendEmail  在进入内层方法会自动获取锁
         * B	invoked sendSMS
         * B	invoked sendEmail
         *
         * C	invoked get()
         * C	invoked set()
         * D	invoked get()
         * D	invoked set()
         */
        Phone phone = new Phone();
        //测试synchronized的可重入锁
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        //测试ReentrantLock的可重入锁
        Thread t3 = new Thread(phone, "C");
        Thread t4 = new Thread(phone, "D");

        t3.start();
        t4.start();

    }
}

class Phone implements Runnable{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\tinvoked sendSMS");
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\tinvoked sendEmail");
    }

    private Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }
    public void get(){
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\tinvoked get()");
            set();
        }finally {
            lock.unlock();
            lock.unlock();
        }
    }
    public void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\tinvoked set()");
        }finally {
            lock.unlock();
        }
    }

}