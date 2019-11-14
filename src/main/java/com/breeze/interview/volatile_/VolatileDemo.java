package com.breeze.interview.volatile_;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {//资源类
    int number = 0;
    boolean flag = false;

    public void changeValue(){
        number = 1;
        flag = true;
    }

    public void addNumber(){
        if (flag) {
            number = number + 5;
            System.out.println(Thread.currentThread().getName() + "\tnumber: " + number);
        }
    }

    public void addTo60() {
        this.number = 60;
    }

    //请注意，此时number前面是加入volatile关键字修饰的，volatile不保证原子性
    public void addPlusPlus(){
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic(){
        atomicInteger.getAndIncrement();
    }
}

/**
 * # 请谈谈你对volatile的理解
 * 	1.volatile是java虚拟机提供的轻量级的同步机制
 * 		1.1 保证可见性
 * 		1.2 不保证原子性
 * 		1.3 禁止指令重排序（禁重排）
 *
 * 	2.谈谈JMM：java内存模型
 * 		2.1 并不真实存在，它描述的是一组规范或规则，通过这组规范定义了程序中各种变量的访问方式
 * 		2.2 JMM关于同步的规定：
 * 			1) 线程解锁前，必须把共享变量的值刷新回主内存
 * 			2) 线程加锁前，必须读取内存的最新值到自己的工作内存
 * 			3) 加锁解锁是同一把锁
 * 		2.3 线程对变量的操作必须在工作内存中进行，首先要将变量从主内存拷贝到自己的工作内存空间
 * 				然后对变量进行操作，操作完成后再将变量写回主内存
 * 		2.4 JMM编程的三大特性：
 * 				可见性     原子性     有序性
 */
public class VolatileDemo {
    public static void main(String[] args) {

        try {
            visibility();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指令重排  (没有测试出结果)
     */
    private static void rearrangement() throws InterruptedException {
        MyData myData = new MyData();

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            Thread t1 = new Thread(myData::changeValue, "ThreadCommunication");
            Thread t2 = new Thread(myData::addNumber, "B");
            t2.start();
            t1.start();
            t1.join();
            t2.join();
            if (myData.number == 5) {
                break;
            }
        }
    }

    /**
     * 验证volatile不保证原子性
     *    2.1 原子性指的是什么意思？
     *        不可分割，完整性，也即某个线程正在做某个具体业务时，
     *        中间不可以加塞或者被分割。需要整体完整，要么同时成功
     *        要么同时失败
     *    2.2 volatile不保证原子性演示
     *    2.3 为什么不能保证？ 线程之间切换太快了
     *    2.4 如何解决原子性
     *      * 加synchronized
     *      * 使用juc下AtomicInteger
     */
    private static void unAtomicity(){

        MyData myData = new MyData();

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addMyAtomic();
                }
            }, String.valueOf(i)).start();
        }

        //需要等待上面20个线程全部计算完成，再用main线程取的最终的结果值看是多少？
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName()
                + "\tfinally number value： " + myData.number);
        System.out.println(Thread.currentThread().getName()
                + "\tmission is over main atomic value: " + myData.atomicInteger);
    }

    /**
     * 验证volatile的可见性
     *    1.1 假如 int number = 0; 没有加volatile关键字
     *    1.2 加了volatile关键字
     */
    private static void visibility(){
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\tcome in");

            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            myData.addTo60();

            System.out.println(Thread.currentThread().getName()
                    + "\tupdated number value: " + myData.number);
        }, "A").start();

        //第二个线程  main
        while (myData.number == 0) {
            //不加volatile 程序会一直在这里打转，下面的mission is over不会执行
            //加了volatile 下面会打印
        }
        System.out.println(Thread.currentThread().getName()
                + "\tmission is over main number value: " + myData.number);
    }
}
