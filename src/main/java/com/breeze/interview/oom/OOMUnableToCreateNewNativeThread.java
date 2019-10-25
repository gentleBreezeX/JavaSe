package com.breeze.interview.oom;

import java.util.concurrent.TimeUnit;

/**
 * 高并发请求服务器时，经常会出现如下异常：
 *   Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
 *      不能再创建更多的线程了
 * 准确的讲该native thread异常与对应的平台有关
 *
 * 导致原因：
 *      1. 你的应用创建了太多线程了，一个应用进程创建多个线程，超过系统承载极限
 *      2. 你的服务器并不允许你的应用程序创建这么多线程，Linux系统默认允许单个
 *          进程可以创建的线程数是1024个，你的应用创建超过这个数量就会报java.lang.OutOfMemoryError: unable to create new native thread
 *
 * 解决办法：
 *      1. 想办法降低你应用程序创建线程的数量，分析应用是否真的需要创建这么多线程
 *          如果不是，改代码将线程数降到最低
 *      2. 对于有的应用，确实需要创建很多线程，远超过Linux系统默认的1024个线程的
 *          限制，可以通过修改Linux服务器配置，扩大Linux默认限制
 */
public class OOMUnableToCreateNewNativeThread {
    public static void main(String[] args) {

        for (int i = 1;; i++) {
            System.out.println("******* i :" + i);
            
            new Thread(() -> {
                //暂停一会儿
                try { TimeUnit.SECONDS.sleep(Integer.MAX_VALUE); } catch (InterruptedException e) {e.printStackTrace(); }
            }, String.valueOf(i)).start();
            
        }
    }
}
