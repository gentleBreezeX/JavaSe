package com.breeze.interview.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PrintInteractive{//资源类  实现交互打印

    private static int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();

    public void printNumber(){
        lock.lock();
        try {
            for (int i = 1; i <= 52; i++) {
                while (flag % 3 == 0) {
                    try {c1.await(); } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(i);
                flag++;
                c2.signal();
            }
        }finally {
            lock.unlock();
        }
    }

    public void printWord(){
        lock.lock();
        try {
            for (char i = 'A'; i <= 'Z'; i++) {
                while (flag % 3 != 0) {
                    try {c2.await(); } catch (InterruptedException e) {e.printStackTrace(); }
                }
                System.out.println(i);
                flag++;
                c1.signal();
            }
        }finally {
            lock.unlock();
        }
    }

}

/**
 * 两个线程，一个线程打印1-52，另一个打印字母A-Z打印顺序为12A34B...5152Z,
 * 要求用线程间通信
 */
public class ThreadCommunication {
    public static void main(String[] args) {

        PrintInteractive printInteractive = new PrintInteractive();

        new Thread(printInteractive::printNumber, "A").start();
        new Thread(printInteractive::printWord, "B").start();

    }
}
