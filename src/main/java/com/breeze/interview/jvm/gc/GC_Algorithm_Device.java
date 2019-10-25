package com.breeze.interview.jvm.gc;


/**
 * 生产上如何配置垃圾收集器？谈谈你对垃圾收集器的理解？
 * GC垃圾回收算法和垃圾回收器的关系？ 分别是什么请你谈谈
 *      GC算法是内存回收的方法论，垃圾回收器就是算法的落地实现
 *      因为目前为止还没有完美的收集器出现，更没有万能的收集器，
 *      只是针对具体应用最合适的收集器，进行分代收集
 *          四大算法：
 *              1.引用计数
 *              2.复制算法
 *              3.标记清除
 *              4.标记压缩
 * <p>
 * 四种主要垃圾回收器：
 *      1.Serial(串行)：它为单线程环境设计且只使用一个线程进行垃圾回收，会暂停所有的用户线程。
 *                      所以不适合服务器环境
 *      2.Parallel(并行)：多个垃圾收集器并行工作，此时用户线程是暂停的，适用于科学计算/大数据
 *                      处理首台处理等弱交互场景
 *      3.CMS(并发)：用户线程和垃圾收集器同时执行(不一定是并行，可能交替执行)，不需要停顿用户线程，
 *                  互联网公司多用它，适用对响应时间又要求的场景
 *      4.G1(garbage first)：将堆内存分割成不同的区域探后并发的对其进行垃圾回收
 * <p>
 * <p>
 * 怎么查看服务器默认的垃圾收集器是哪个？
 *      java -XX:+PrintCommandLineFlags -version
 *      -XX:+UseParallelGC  并行垃圾回收器
 * <p>
 * java的GC回收的类型主要有几种（7种，还有一种是串行老年区GC，但是已经不用了）：
 *      底层源码是这六个：
 *          UseSerialGC：串行回收器
 *          UseParallelGC：并行回收器
 *          UseParNewGC：在young区的并发的串行垃圾回收方式（就是serial的多线程版本）
 *          UseParallelOldGC：在老年区的并行GC
 *          UseConcMarkSweepGC：并发标记清除GC
 *          UseG1GC：
 * <p>
 * 部分参数说明：
 *      DefNew       Default New Generation
 *      Tenured       Old
 *      ParNew        Parallel New Generation
 *      PSYoungGen    Parallel Scavenge
 *      ParOldGen     Parallel Old Generation
 * <p>
 * Server/Client模式分别是什么意思？
 *      1. 只需要掌握Server模式即可，Client模式基本上不会用
 *      2. 32位Window操作系统，不论硬件如何都默认使用Client的JVM模式
 *      3. 32位其他操作系统，2G内存同时有2个CPU以上的用Server模式，低于该配置的还是Client模式
 *      4. 64位only server模式
 */
public class GC_Algorithm_Device {
    public static void main(String[] args) {

    }
}
