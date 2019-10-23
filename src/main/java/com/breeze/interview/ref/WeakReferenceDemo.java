package com.breeze.interview.ref;

import java.lang.ref.WeakReference;

/**
 * 弱引用
 */
public class WeakReferenceDemo {
    public static void main(String[] args) {

        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);//java.lang.Object@5cad8086
        System.out.println(weakReference.get());//java.lang.Object@5cad8086

        o1 = null;
        System.gc();
        System.out.println("==============");

        System.out.println(o1);//null
        System.out.println(weakReference.get());//null

    }
}
