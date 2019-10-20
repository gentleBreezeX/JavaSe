package com.breeze.interview.jvm.gc;

/**
 * JVM垃圾回收的时候如何确定垃圾？是否知道什么是GC Roots
 *      1.什么是垃圾：简单的说就是内存中已经不再被使用到的空间就是垃圾
 *      2.如何判断：① 引用计数法
 *                 ② 枚举根节点做可达性分析(根搜索路径算法)
 *
 *      3.所谓GC roots 或者说tracing GC的"根集合" 就是一组必须活跃的引用
 *          通过一系列名为“GC Roots”的对象作为起始点 进行链路的扫描
 *
 *      4.哪些可以作为GC Roots对象
 *          虚拟机栈(栈桢中的局部变量区，也叫做局部变量表)中引用的对象
 *          方法区中的类静态属性引用的对象
 *          方法区中常量引用的对象
 *          本地方法栈中JNI(Native方法)引用的对象
 */
public class GCRoots {
    private byte[] byteArray = new byte[100 * 1024 * 1024];

    //假设的两个类 没有写
    //private static GCRoots2 t2;
    //private static final GCRoots3 t3 = new GCRoots3(8);

    public static void m1() {
        GCRoots t1 = new GCRoots();
        System.gc();
        System.out.println("第一次GC完成");
    }

    public static void main(String[] args) {
        m1();
    }
}
