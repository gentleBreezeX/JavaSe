package com.breeze.interview.ref;

/**
 * 强引用、软引用、弱引用、虚引用分别是什么？
 *      强引用：就算出现了OOM也不会对该对象进行回收，死都不收
 *            一般都是强引用，因此强引用是造成java内存泄露的主要原因之一
 *
 */
public class FourReferences {
    public static void main(String[] args) {


    }

    /**
     * 强引用演示
     */
    private static void StrongReferenceDemo() {
        Object o1 = new Object();//这样定义的默认是强引用
        Object o2 = o1;//o2引用赋值
        o1 = null; // 置空
        System.gc();
        System.out.println(o2);//java.lang.Object@5cad8086
    }
}
