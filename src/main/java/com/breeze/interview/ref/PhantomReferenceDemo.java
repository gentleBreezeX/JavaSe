package com.breeze.interview.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.TimeUnit;

/**
 * 虚引用
 */
public class PhantomReferenceDemo {
    public static void main(String[] args) {

        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Object> phantomReference = new PhantomReference<>(o1, referenceQueue);

        System.out.println(o1);//java.lang.Object@5cad8086
        System.out.println(phantomReference.get());//null
        System.out.println(referenceQueue.poll());//null

        System.out.println("================");

        o1 = null;
        System.gc();
        //暂停一会儿
        try {
            TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) {e.printStackTrace(); }

        System.out.println(o1);//null
        System.out.println(phantomReference.get());//null
        System.out.println(referenceQueue.poll());//java.lang.ref.PhantomReference@6e0be858

    }
}
