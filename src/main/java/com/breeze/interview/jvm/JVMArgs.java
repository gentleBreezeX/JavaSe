package com.breeze.interview.jvm;


import java.util.concurrent.TimeUnit;

/**
 * 1.JVM参数类型
 *      ① 标配参数
 *          java -version
 *          java -help
 *          java -showversion
 *      ② X参数(了解)
 *          -Xint 解释执行 (java -Xint -version)
 *          -Xcomp 第一次使用就编译成本地代码
 *          -Xmixed  混合模式
 *      ③ XX参数
 *          Boolean类型
 *              公式： -XX:+ 或者 -某个属性值
 *                      +表示开启   -表示关闭
 *              Case：是否打印GC收集细节 -XX:+PrintGCDetails  -XX:-PrintGCDetails
 *                    是否使用串行垃圾回收器 -XX:-UseSerialGC  -XX:+UseSerialGC
 *          KV设值类型
 *              公式：-XX:属性key=属性值value
 *              Case：jinfo -flag MetaspaceSize 查看元空间初始值
 *                     -XX:MaxTenuringThreshold=15 新生代晋升老年代需要的次数
 * 2.题外话 面试题
 *      两个经典参数  -Xms和-Xmx 这两个是直接加参数 -Xms1024m -Xmx1024m
 *      -Xms等价于 -XX:InitialHeapSize
 *      -Xmx等价于 -XX:MaxHeapSize
 *
 * 3.第一种，查看参数
 *      jps
 *      jinfo -flag 具体参数  java进程编号
 *      jinfo -flags         java进程编号
 *
 * 4.第二种 查看参数盘点家底
 *      java -XX:+PrintFlagsInitial 主要查看初始默认 【重点】
 *      java -XX:+PrintFlagsFinal -version  主要查看修改更新
 *          := 表示人为修改过的或者jvm修改过的
 *      java -XX:+PrintCommandLineFlags -version 常用的参数 可以查看垃圾回收器是哪个
 *
 * 5.平时工作中的JVM常用的基本配置：
 *      -Xms  等价于 -XX:InitialHeapSize  初始大小内存，默认是物理内存的1/64
 *      -Xmx  等价于 -XX:MaxHeapSize      最大分配内存，默认是物理内存的1/4
 *      -Xss  等价于 -XX:ThreadStackSize  设置单个线程的大小，一般默认为512k ~ 1024k
 *              -XX:ThreadStackSize=0 如果是0表示的是默认值
 *      -Xmn  设置新生代的大小
 *      -XX:MetaspaceSize 设置元空间大小
 *      -XX:+PrintGCDetails 打印垃圾回收的一些信息
 *      -XX:SurvivorRatio 设置新生代中Eden和s0 s1空间的比例
 *              -XX:SurvivorRatio=8 就是8:1:1
 *      -XX:NewRatio     配置年轻代与老年代在堆结构的占比
 *              -XX:NewRatio=2 就是1:2
 *      -XX:MaxTenuringThreshold=15 新生代晋升老年代需要的次数
 */
public class JVMArgs {
    public static void main(String[] args) {

        System.out.println("*****使用jinfo -flag PrintGCDetails +进程号 查看是否开启打印GC收集细节");

        //暂停一会儿
        //try { TimeUnit.SECONDS.sleep(Integer.MAX_VALUE); } catch (InterruptedException e) {e.printStackTrace(); }
    }
}
