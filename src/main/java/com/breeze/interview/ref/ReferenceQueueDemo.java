package com.breeze.interview.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * 引用队列
 */
public class ReferenceQueueDemo {
    public static void main(String[] args) {

        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        WeakReference<Object> weakReference = new WeakReference<>(o1, referenceQueue);

        System.out.println(o1);//java.lang.Object@5cad8086
        System.out.println(weakReference.get());//java.lang.Object@5cad8086
        System.out.println(referenceQueue.poll());//null

        System.out.println("==============");

        o1 = null;
        System.gc();

        //暂停一会儿
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }

        System.out.println(o1);//null
        System.out.println(weakReference.get());//null
        System.out.println(referenceQueue.poll());//java.lang.ref.WeakReference@6e0be858

    }
}
