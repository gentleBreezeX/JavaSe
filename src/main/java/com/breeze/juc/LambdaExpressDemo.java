package com.breeze.juc;

@FunctionalInterface
interface Foo{
    int add(int x, int y);

    default int div(int x, int y){
        return x / y;
    }

    static int mul(int x, int y){
        return x * y;
    }
}

/**
 * 2. Lambda Express (前提是函数式接口，只有一个方法)
 *  2.1 拷贝小括号，写死右箭头，落地大括号
 *  2.2 注解 @FunctionalInterface
 *  2.3 default方法 (接口中可以有方法实例)
 *  2.4 static方法
 *
 */
public class LambdaExpressDemo {
    public static void main(String[] args) {
        Foo foo = (x,y) -> {
            System.out.println("======come in add method========");
            return x + y;
        };
        System.out.println(foo.add(3, 4));
        System.out.println(foo.div(4, 2));
        System.out.println(Foo.mul(4, 3));
    }
}
