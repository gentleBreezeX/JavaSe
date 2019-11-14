package com.breeze.interview.lock.distributed;


import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

public abstract class ZkAbstractTemplateLock implements ZkLock {

    private static final String ZK_SERVER = "192.168.233.128:2181";
    private static final int TIME_OUT = 45 * 1000;
    //创建zookeeper客户端
    ZkClient zkClient = new ZkClient(ZK_SERVER, TIME_OUT);

    String path = "/zkLock";
    CountDownLatch countDownLatch = null;

    public abstract boolean tryZkLock();
    public abstract void waitZkLock();

    @Override
    public void lock() {
        //尝试抢占锁
        if (tryZkLock()) {
            System.out.println(Thread.currentThread().getName() + "\t 抢占锁成功");
        }else {
            waitZkLock();

            //递归调用加锁方法
            lock();
        }
    }
    @Override
    public void unlock() {
        if (zkClient != null) {
            zkClient.close();
        }
        System.out.println(Thread.currentThread().getName() + "\t 释放锁成功");
        System.out.println();
        System.out.println();
    }
}
