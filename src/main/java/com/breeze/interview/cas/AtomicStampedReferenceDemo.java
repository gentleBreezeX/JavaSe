package com.breeze.interview.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceDemo {
    
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {

        System.out.println("====以下是ABA问题的产生====");
        new Thread(() -> {
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        }, "t1").start();

        new Thread(() -> {
            //暂停1s 保证t1线程完成了一次ABA操作
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get());
        }, "t2").start();

        //暂停一会
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) {e.printStackTrace(); }
        System.out.println("====以下是ABA问题的解决====");

        new Thread(() -> {
            //获取初始版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号：" + stamp);

            //暂停一秒钟
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }

            atomicStampedReference.compareAndSet(100, 101,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);

            System.out.println(Thread.currentThread().getName() + "\t第二次版本号：" + atomicStampedReference.getStamp());

            atomicStampedReference.compareAndSet(101, 100,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp()+1);

            System.out.println(Thread.currentThread().getName() + "\t第三次版本号：" + atomicStampedReference.getStamp());

        }, "t3").start();

        new Thread(() -> {
            //获取初始版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第一次版本号：" + stamp);

            //暂停3秒钟 保证t3线程完成了一次ABA操作
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) {e.printStackTrace(); }

            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp+1);
            System.out.println(Thread.currentThread().getName() + "\t修改成功否：" + result +
                    "\t当前最新实际版本号：" + atomicStampedReference.getStamp());

            System.out.println(Thread.currentThread().getName() + "\t当前最新实际值：" + atomicStampedReference.getReference());

        }, "t4").start();

    }
}
