package com.breeze.interview.ref;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {
    //内存够用的时候就保留，不够用就回收
    public static void softRef_Memory_Enough(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);//java.lang.Object@5cad8086
        System.out.println(softReference.get());//java.lang.Object@5cad8086

        o1 = null;
        System.gc();

        System.out.println(o1);//null
        System.out.println(softReference.get());//java.lang.Object@5cad8086
    }
    /**
     * -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void softRef_Memory_NotEnough(){
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        System.out.println(o1);//java.lang.Object@5cad8086
        System.out.println(softReference.get());//java.lang.Object@5cad8086

        o1 = null;

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(o1);//null
            System.out.println(softReference.get());//null
        }
    }
    public static void main(String[] args) {
        //softRef_Memory_Enough();
        softRef_Memory_NotEnough();
    }

}
