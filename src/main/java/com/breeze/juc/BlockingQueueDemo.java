package com.breeze.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 *      1.抛出异常
 *          当阻塞队列满时，再往队列里add插入元素会抛IllegalStateException:Queue full
 *          当阻塞队列空时，再往队列里remove移除元素会抛NoSuchElementException
 *      2.特殊值
 *          插入方法offer，成功ture失败false
 *          移除方法poll，成功返回出队列的元素，队列里没有就返回null
 *      3.一直阻塞
 *          当阻塞队列满时，生产者线程继续往队列里put元素，队列会一直阻塞生产者线程直到put数据or响应中断退出
 *          当阻塞队列空时，消费者线程试图从队列里take元素，队列会一直阻塞消费者线程直到队列可用
 *      4.超时退出
 *          当阻塞队列满时offer/pool，队列会阻塞生产者线程一定时间，超过限时后生产者线程会退出
 * 好处：
 *       在多线程领域：所谓阻塞，在某些情况下会挂起线程（即阻塞），一旦条件满足，被挂起的线程又会自动被唤起
 *
 *       为什么需要BlockingQueue
 *       好处是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，
 *       因为这一切BlockingQueue都给你一手包办了
 *
 *       在concurrent包发布以前，在多线程环境下，我们每个程序员都必须去自己控制这些细节，
 *       尤其还要兼顾效率和线程安全，而这会给我们的程序带来不小的复杂度。
 *
 * 常用种类：
 *     ArrayBlockingQueue：由数组结构组成的有界阻塞队列
 *     LinkedBlockingQueue：由链表结构组成的有界（但大小默认值为integer.MAX_VALUE）阻塞队列。
 *     SynchronousQueue：不存储元素的阻塞队列，也即单个元素的队列。
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("ThreadCommunication", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("ThreadCommunication", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("ThreadCommunication", 3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("ThreadCommunication", 3, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));

//        blockingQueue.put("a");
//        blockingQueue.put("a");
//        blockingQueue.put("a");
//        blockingQueue.put("a");//会一直阻塞
//
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());
//        System.out.println(blockingQueue.take());


//        System.out.println(blockingQueue.offer("a"));
//        System.out.println(blockingQueue.offer("b"));
//        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("c"));//false

//        System.out.println(blockingQueue.peek());

//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());//null


//        System.out.println(blockingQueue.add("a"));
//        System.out.println(blockingQueue.add("b"));
//        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d"));//java.lang.IllegalStateException: Queue full

//        System.out.println(blockingQueue.element());

//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());//java.util.NoSuchElementException
    }
}
