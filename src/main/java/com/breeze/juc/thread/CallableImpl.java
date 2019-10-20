package com.breeze.juc.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class GetSum implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        synchronized (this) {
            for (int i = 0; i < 101; i++) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                sum += i;
            }
            return sum;
        }
    }
}

/**
 * 多线程方式三： 实现Callable
 * 在主线程中需要执行比较耗时的操作时，但又不想阻塞主线程时，可以把这些作业交给Future对象在后台完成，
 * 当主线程将来需要时，就可以通过Future对象获得后台作业的计算结果或者执行状态。
 *
 * 一般FutureTask多用于耗时的计算，主线程可以在完成自己的任务后，再去获取结果。
 *
 * 仅在计算完成时才能检索结果；如果计算尚未完成，则阻塞 get 方法。一旦计算完成，
 * 就不能再重新开始或取消计算。get方法而获取结果只有在计算完成时获取，否则会一直阻塞直到任务转入完成状态，
 * 然后会返回结果或者抛出异常。
 *
 * 只计算一次
 * get方法放到最后
 *
 *  面试题:callable接口与runnable接口的区别？
 *
 *  答：1）是否有返回值
 *      2）是否抛异常
 *      3）落地方法不一样，一个是run，一个是call
 */
public class CallableImpl {
    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        FutureTask<Integer> integerFutureTask = new FutureTask<>(() ->{
            int sum = 0;
            lock.lock();
            try {
                for (int i = 0; i < 10; i++) {
                    sum += i;
                    System.out.println(Thread.currentThread().getName() + "\t sum = " + sum);
                }
                return sum;
            } finally {
                lock.unlock();
            }
        });


        new Thread(integerFutureTask).start();

        //类似于自旋锁，线程没有计算完成的话就自旋一下直到计算出结果
        while (!integerFutureTask.isDone()){

        }

        Integer result = null;
        try {
            result = integerFutureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);


       /* GetSum getSum = new GetSum();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(getSum);
        Thread t1 = new Thread(integerFutureTask);
        Thread t2 = new Thread(integerFutureTask);
        Thread t3 = new Thread(integerFutureTask);

        t1.start();
        t2.setPriority(6);
        t2.start();
        t3.start();

        try {
            //get（）方法的返回值即为FutureTask构造器参数Callable
            //实现类重写call()的返回值
            Integer sum = integerFutureTask.get();
            System.out.println(sum);
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
