package com.breeze.interview.ref;

/**
 * 强引用、软引用、弱引用、虚引用分别是什么？
 *      强引用(Reference)：就算出现了OOM也不会对该对象进行回收，死都不收
 *            一般都是强引用，因此强引用是造成java内存泄露的主要原因之一
 *      软引用(SoftReference)：内存足够我不收，内存不够就收，不报OOM，比如高速缓存
 *      弱引用(WeakReference)：不管内存是否够，只要有gc就回收
 *      虚引用(PhantomReference)：如果一个对象仅保持有虚引用，那么它就和没有任何
 *                              引用一样，在任何时候都可以内垃圾回收器回收，必须和
 *                              引用队列(ReferenceQueue)联合使用
 *              作用：是跟踪对象被垃圾回收的状态
 *      引用队列(ReferenceQueue)：我要被回收的时候需要被放到引用队列保存一下
 *
 * 什么时候用？
 *      1.比如读取图片，就可以使用软引用或者弱引用
 *          设计思路：用一个HashMap来保存图片的路径和相应图片对象关联的软引用
 *                  之间的映射关系，在内存不足时，JVM会自动回收这些缓存图片
 *                  对象所占的空间，从而有效的避免OOM
 *       Map<String,SoftReference<Bitmap>> imageCache = new HashMap<>();
 *       Bitmap:新版的位图索引  (Mybatis底层就有大量的软引用，弱引用)
 *
 * 你知道软引用的话，能谈谈WeakHashMap吗？
 *
 * java提供了4种引用类型，在垃圾回收的时候，都有各自的特点
 * ReferenceQueue是用来配合引用工作的，没有ReferenceQueue一样可以运行
 *
 * 创建引用的时候可以指定关联的队列，当GC释放对象内存的时候，会将引用加入到引用队列
 * 如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动
 * 这相当于是一种通知机制
 *
 * 当关联的引用队列中有数据的时候，意味着引用指向的堆内存中的对象被回收。 通过这种方式，JVM允许我们在对象被销毁后
 * 做一些我们自己想做的事情
 *
 *
 *
 */

/**
 * 强引用演示
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {


        Object o1 = new Object();//这样定义的默认是强引用
        Object o2 = o1;//o2引用赋值
        o1 = null; // 置空
        System.gc();
        System.out.println(o2);//java.lang.Object@5cad8086

    }
}
