package com.breeze.interview.lock.distributed;

/**
 * 使用zookeeper实现分布式锁
 */
public class DistributedLockDemo {

    OrderNumGenerateUtil orderNumGenerateUtil = new OrderNumGenerateUtil();
    private ZkLock zkLock = new ZkDistributedLock();

    public void getOrderNumber(){

        zkLock.lock();

        try {
            System.out.println("订单编号：" + orderNumGenerateUtil.getOrderNum());
        }finally {
            zkLock.unlock();
        }

    }


    public static void main(String[] args) {

        for (int i = 1; i <= 20; i++) {

            new Thread(() -> new DistributedLockDemo().getOrderNumber(), String.valueOf(i)).start();

        }
    }
}
