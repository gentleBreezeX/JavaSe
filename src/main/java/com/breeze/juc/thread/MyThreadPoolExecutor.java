package com.breeze.juc.thread;


import java.util.Random;
import java.util.concurrent.*;

/**
 * 多线程方式四： 线程池
 *
 * 线程池的优势：
 *      1.线程池做的工作只要是控制运行的线程数量，处理过程中将任务放入队列，
 *      然后在线程创建后启动这些任务，如果线程数量超过了最大数量，超出数量
 *      的线程排队等候，等其他线程执行完毕，再从队列中取出任务来执行。
 *
 *      2.它的主要特点为：线程复用;控制最大并发数;管理线程。
 *
 *      第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的销耗。
 *      第二：提高响应速度。当任务到达时，任务可以不需要等待线程创建就能立即执行。
 *      第三：提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会销耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。
 *
 * 7大参数：
 *      1、corePoolSize：线程池中的常驻核心线程数
 *      2、maximumPoolSize：线程池中能够容纳同时执行的最大线程数，此值必须大于等于1
 *      3、keepAliveTime：多余的空闲线程的存活时间
 *              当前池中线程数量超过corePoolSize时，当空闲时间达到keepAliveTime时，
 *              多余线程会被销毁直到只剩下corePoolSize个线程为止
 *      4、unit：keepAliveTime的单位
 *      5、workQueue：任务队列，被提交但尚未被执行的任务
 *      6、threadFactory：表示生成线程池中工作线程的线程工厂，用于创建线程，一般默认的即可
 *      7、handler：拒绝策略，表示当队列满了，并且工作线程大于等于线程池的最大线程数（maximumPoolSize）
 *             时如何来拒绝请求执行的runnable的策略
 *
 * 底层工作原理：
 *      1、在创建了线程池后，开始等待请求。
 *      2、当调用execute()方法添加一个请求任务时，线程池会做出如下判断：
 *          2.1如果正在运行的线程数量小于corePoolSize，那么马上创建线程运行这个任务；
 *          2.2如果正在运行的线程数量大于或等于corePoolSize，那么将这个任务放入队列；
 *          2.3如果这个时候队列满了且正在运行的线程数量还小于maximumPoolSize，那么还是要创建非核心线程立刻运行这个任务；
 *          2.4如果队列满了且正在运行的线程数量大于或等于maximumPoolSize，那么线程池会启动饱和拒绝策略来执行。
 *      3、当一个线程完成任务时，它会从队列中取下一个任务来执行。
 *      4、当一个线程无事可做超过一定的时间（keepAliveTime）时，线程会判断：
 *          如果当前运行的线程数大于corePoolSize，那么这个线程就被停掉。
 *          所以线程池的所有任务完成后，它最终会收缩到corePoolSize的大小。
 *
 * 内置拒绝策略：
 * -->内置拒绝策略均实现了RejectedExecutionHandle接口
 *      1.AbortPolicy(默认)：直接抛出RejectedExecutionException异常阻止系统正常运行
 *      2.CallerRunsPolicy：“调用者运行”一种调节机制，该策略既不会抛弃任务，也不
 *          会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量。
 *      3.DiscardOldestPolicy：抛弃队列中等待最久的任务，然后把当前任务加人队列中
 *          尝试再次提交当前任务。
 *      4.DiscardPolicy：该策略默默地丢弃无法处理的任务，不予任何处理也不抛出异常。
 *          如果允许任务丢失，这是最好的一种策略。
 *
 * 自定义线程池的线程数应该怎么配置？
 *      CPU密集型： CPU核数  +  1个线程  的线程池
 *      IO密集型：1. 由于IO密集型任务线程并不是一直在执行任务，则应该配置尽可能多的线程，如CPU核数*2
 *               2. 参考公式： CPU核数/1 - 阻塞系数  阻塞系数在0.8 ~ 0.9之间
 *                            比如8核(四核八线程)CPU : 8/(1 - 0.9) = 80个
 *
 *
 */
public class MyThreadPoolExecutor {
    public static void main(String[] args) {

        System.out.println("CPU的线程数量：" + Runtime.getRuntime().availableProcessors());
        System.out.println(8/(1 - 0.9));

        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        //开发中不使用jdk自带的，需要自己手动写线程池
        //ExecutorService executorService = Executors.newFixedThreadPool(5);//一池5线程
        //ExecutorService executorService = Executors.newSingleThreadExecutor();//一池1线程
        //ExecutorService executorService = Executors.newCachedThreadPool();//一池n线程

        try {
            for (int i = 1; i <= 20; i++) {
                executorService.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t办理业务: " + new Random().nextInt(10));
                });
            }
        } finally {
            executorService.shutdown();
        }

    }
}
