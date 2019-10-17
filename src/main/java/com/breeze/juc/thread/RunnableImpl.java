package com.breeze.juc.thread;

class Window4 implements Runnable {
    //100张票
    private int ticket = 100;

    @Override
    public void run() {
        do {
            sale();
        }while (ticket > 1);
    }

    private synchronized void sale(){
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + ":正在卖第 " + (ticket--)
                    + "张票，还剩：" + ticket);
        }
    }
}

class Window3 implements Runnable{
    //100张票
    private int ticket = 100;

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
 * 多线程方式二： 实现Runnable接口
 */
public class RunnableImpl {
    public static void main(String[] args) {

        Window4 window4 = new Window4();
        Thread t1 = new Thread(window4);
        Thread t2 = new Thread(window4);
        Thread t3 = new Thread(window4);

        t1.start();
        t2.start();
        t3.start();

        Window3 window3 = new Window3();
        Thread t4 = new Thread(window3);
        Thread t5 = new Thread(window3);
        Thread t6 = new Thread(window3);

        t4.start();
        t5.start();
        t6.start();

    }
}


