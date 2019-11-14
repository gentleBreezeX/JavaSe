package com.breeze.interview.oom;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * JVM参数
 *      -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
 *
 * Java 8及之后的版本使用Metaspace来替代永久代
 *
 * Metaspace是方法区在HotSpot中的实现，它与永久代最大的区别在于：Metaspace
 *  并不在虚拟机内存中而是使用本地内存也即在java8中，classe metadata
 *  (the virtual machines internal presentation of Java class)，被存储
 *  在叫做Metaspace的native memory
 *
 * 永久代(Java 8后被元空间Metaspace取代) 存放了以下信息：
 *      虚拟机加载的类信息
 *      常量池
 *      静态变量
 *      即时编译后的代码
 *
 * 模拟Metaspace空间溢出，我们不断生成类往元空间灌，
 * 类占据的空间总会超过Metaspace指定的空间大小
 */
public class OOMMetaspace {

    static class OOMTest{}

    public static void main(String[] args) {

        int i = 0; // 模拟计数多少次以后发生异常
        try {
            while (true) {
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects,
                                            MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, args);
                    }
                });
                enhancer.create();
            }
        }catch (Throwable e){
            System.out.println("========多少次后发生异常：" + i);
            e.printStackTrace();
        }
    }
}
