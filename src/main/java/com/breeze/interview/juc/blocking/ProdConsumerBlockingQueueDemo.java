package com.breeze.interview.juc.blocking;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{
    private volatile boolean flag = true;//默认开启，进行生产+消费
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws Exception{

        String data = null;
        boolean retValue;

        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);

            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t插入队列"
                        + data + "成功");
            }else {
                System.out.println(Thread.currentThread().getName() + "\t插入队列"
                        + data + "失败");
            }
            //暂停一会儿
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t大老板叫停了，表示flag=false，生产动作结束");
    }

    public void myConsumer() throws Exception{

        String result = null;
        while (flag) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);

            if (result == null || result.equalsIgnoreCase("")) {

                flag = false;//(我猜测可能是多线程网络延迟，或者说线程刚准备取出，又被切换堵塞)
                            // 又或者多个线程取，生产线程来不及生产
                            // 有可能走到这一步，flag还是true，避免生产方法一直生产
                System.out.println(Thread.currentThread().getName() + "\t超过2秒钟没有取到蛋糕，消费退出");
                return;
            }

            System.out.println(Thread.currentThread().getName() + "\t消费蛋糕队列"
                    + result + "成功");
        }
    }
    public void stop() throws Exception{
        this.flag = false;
    }

}

/**
 * 阻塞队列版的生消模式
 */
public class ProdConsumerBlockingQueueDemo {
    public static void main(String[] args) {

        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产线程启动");

            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "Prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
            System.out.println();
            System.out.println();

            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Consumer").start();

        //暂停一会儿
        try {TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) {e.printStackTrace(); }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束");

        try {
            myResource.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
