package com.breeze.juc.thread;


class Window1 extends Thread{
    //100张票
    private static int ticket = 100;

    @Override
    public void run() {
        do {
            sale();
        } while (ticket > 1);
    }

    private synchronized void sale(){
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + ":正在卖第 " + (ticket--)
                    + "张票，还剩：" + ticket);
        }
    }
}

class Window2 extends Thread{
    //100张票
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + ":正在卖第 " + (ticket--)
                            + "张票，还剩：" + ticket);
                }else break;
            }
        }
    }
}

/**
 * 多线程实现方式一： 继承Thread类
 */
public class ThreadExtends {
    public static void main(String[] args) {

        Window1 w1 = new Window1();
        Window1 w2 = new Window1();
        Window1 w3 = new Window1();

        w1.start();
        w2.start();
        w3.start();


        Window2 w4 = new Window2();
        Window2 w5 = new Window2();
        Window2 w6 = new Window2();

        w4.start();
        w5.start();
        w6.start();
    }

}
