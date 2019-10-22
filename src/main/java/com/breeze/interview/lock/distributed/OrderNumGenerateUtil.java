package com.breeze.interview.lock.distributed;

/**
 * 生成订单编号的工具类
 */
public class OrderNumGenerateUtil {

    private static int number = 0;

    public String getOrderNum(){
        return "" + (++number);
    }
}
