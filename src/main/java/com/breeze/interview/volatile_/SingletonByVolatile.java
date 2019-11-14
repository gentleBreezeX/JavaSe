package com.breeze.interview.volatile_;


/**
 * volatile解决单例模式的问题
 *      synchronized可以控制线程安全问题，但是会导致并发性下降
 *      多线程下，由于指令重排可能会出现线程安全问题
 *      所以需要使用volatile修饰
 *
 * 多线程单例模式：需要DCL 双端检锁机制 + volatile
 *
 */
public class SingletonByVolatile {
    private static volatile SingletonByVolatile instance = null;

    private SingletonByVolatile() {
        System.out.println(Thread.currentThread().getName()
                + "\t我是构造方法SingletonByVolatile()");
    }

    //DCL模式 (Double Check Lock 双端检锁机制)
    public static SingletonByVolatile getInstance(){
        if (instance == null) {
            //在加锁前后进行判断
            synchronized (SingletonByVolatile.class) {
                if (instance == null) {
                    instance = new SingletonByVolatile();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程 main线程的操作动作
//        System.out.println(SingletonByVolatile.getInstance() == SingletonByVolatile.getInstance());
//        System.out.println(SingletonByVolatile.getInstance() == SingletonByVolatile.getInstance());
//        System.out.println(SingletonByVolatile.getInstance() == SingletonByVolatile.getInstance());

        //并发多线程后
        for (int i = 1; i <= 50; i++) {
            new Thread(SingletonByVolatile::getInstance, String.valueOf(i)).start();
        }

    }
}
