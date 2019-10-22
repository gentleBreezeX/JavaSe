package com.breeze.juc.locks;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket{  //资源类
    //传统的企业级方式
    /*private int number = 30;
    public synchronized void sale(){
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "正在卖第：" + (number--)
                    + " 张\t还剩下：" + number);
        }
    }*/
    //使用juc
    private int number = 30;
    private Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();

        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "正在卖第：" + (number--)
                        + " 张\t还剩下：" + number);
            }
        } finally {
            lock.unlock();
        }
    }

}

/**
 * 题目：三个销售员   卖出  30张票
 * 目的：如何写出企业级的多线程程序
 *      new ReentrantLock() 可重入锁
 *
 *    ** 高内聚，低耦合
 *      1.在高内低耦的前提下，线程  操作  资源类
 */
public class SaleTicket {
    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        //ExecutorService executorService = Executors.newFixedThreadPool(3);
        //ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 30; i++) executorService.execute(ticket::sale);
        } finally {
            executorService.shutdown();
        }


        //lambda表达式方式
//        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale(); }, "ThreadCommunication").start();
//        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale(); }, "B").start();
//        new Thread(() -> { for (int i = 0; i < 40; i++) ticket.sale(); }, "C").start();



        //Thread(Runnable target, String name) Allocates a new Thread object.
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "ThreadCommunication").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.sale();
                }
            }
        }, "C").start();*/
    }
}