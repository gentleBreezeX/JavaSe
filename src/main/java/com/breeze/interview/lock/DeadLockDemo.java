package com.breeze.interview.lock;

import java.util.concurrent.TimeUnit;

class HoldLockThread implements Runnable{

    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有："
                    + lockA + "\t尝试获得：" + lockB);
            //暂停一会儿
            try {
                TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) {e.printStackTrace(); }

            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t自己持有："
                        + lockB + "\t尝试获得：" + lockA);
            }
        }
    }
}


/**
 * 1. 死锁是指两个或两个以上的进程在执行过程中，因抢夺资源而造成的一种相互等待
 *      的现象，若无外力干涉那它们都将无法推进下去，如果系统资源充足，进程的资源
 *      请求都能够得到满足，死锁出现的可能性就很低，否则就会因争夺有限的资源而陷入死锁
 *
 * 2.产生的主要原因：
 *          系统资源不足
 *          进程运行推进的顺讯不合适
 *          资源分配不当
 *
 * 3.   Linux  ps -ef|grep xxx     ls -l
 *      windows下的java运行程序  也有类似ps的查看进程的命令，但是目前我们需要查看的只是java
 *              jps 即java ps   jps -l  定位进程号
 *              jstack + PId 查看运行的java程序的信息，找到死锁查看
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        
        String lockA = "lockA";
        String lockB = "lockB";
        
        new Thread(new HoldLockThread(lockA,lockB), "ThreadAAA").start();
        new Thread(new HoldLockThread(lockB,lockA), "ThreadBBB").start();
    }
}
