package com.breeze.interview.oom;

/**
 * 方法递归调用：栈溢出
 * Exception in thread "main" java.lang.StackOverflowError
 */
public class StackOverFlowErrorDemo {
    public static void main(String[] args) {
        main(args);
    }
}
