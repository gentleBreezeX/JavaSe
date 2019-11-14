package com.breeze.interview.jvm.gc;


import java.util.Random;

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
 * java的GC回收的类型主要有几种:
 *      底层源码是六个(不包括UseSerialOldGC)：
 *          UseSerialGC：串行回收器
 *          UseSerialOldGC：在old区的串行回收器(但是已经不用了)
 *          UseParallelGC：并行回收器
 *          UseParallelOldGC：在老年区的并行GC
 *          UseParNewGC：在young区的并行多线程的垃圾回收方式（就是serial的多线程版本）
 *          UseConcMarkSweepGC：并发标记清除GC
 *          UseG1GC：
 * <p>
 * 部分参数说明：
 *      DefNew        Default New Generation
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
 *
 * 新生代：
 *      1. 串行GC(Serial)/(Serial Copying) --->单线程 简单高效  但是会导致较长的停顿(Stop-The-World)
 *              对应JVM参数： -XX:+UseSerialGC
 *              开启后会使用： Serial(Young区用) + Serial Old(old区用)的收集器组合
 *              表示：新生代、老年代都会使用串行回收器，新生代使用复制算法，老年代使用标记-整理算法
 *              测试参数：-Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC  (DefNew + Tenured)
 *      2. 并行GC(ParNew) ---> 并行多线程版本 也会导致STW
 *              对应JVM参数: -XX:+UseParNewGC
 *              开启上述参数后：ParNew(Young区用) + Serial Old的收集组合
 *              表示：新生代使用复制算法，老年代采用标记-整理算法
 *              参数：-Xms10m -Xmx10m -XX:PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC  (ParNew + Tenured)
 *      3. 并行回收GC(Parallel)/(Parallel Scavenge) ---> 并行多线程  俗称吞吐量优先收集器
 *              关注重点：可控制的吞吐量(Thoughput=运行用户代码时间/(运行用户代码时间+垃圾收集时间))
 *                       自适应调节策略是ParallelScavenge收集器与ParNew收集器的一个重要区别(自适应调节策略：虚拟机会根据
 *                          当前系统的运行情况收集性能监控信息，动态调整这些参数以提供最合适的停顿时间(-XX:MaxGCPauseMillis)或最大的吞吐量)
 *              对应JVM参数：-XX:+UseParallelGC或-XX:+UseParallelOldGC(可相互激活)
 *              开启上述参数：新生代使用复制算法，老年代使用标记-整理算法
 *              参数：-Xms10m -Xmx10m -XX:PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC   (PSYoungGen + ParOldGen)
 * 老年代：
 *      1. 串行GC(Serial Old)/(Serial MSC)
 *      2. 并行GC(Parallel Old)/(Parallel MSC)
 *              参数：-XX:+UseParallelOldGC  (PSYoungGen + ParOldGen)
 *      3. 并发标记清除GC(CMS): 是一种以获取最短回收停顿时间为目标的收集器
 *              对应JVM参数：-XX:+UseConcMarkSweepGC 该参数开启后自动将-XX:+UseParNewGC打开
 *              开启上述参数：ParNew(Young区用) + CMS(Old区用) + Serial Old组合使用，Serial Old将作为CMS出错的后备收集器
 *              四步过程：1)：初始标记(CMS initial mark)
 *                          只是标记一下GC Roots能直接关联的对象，速度很快，仍然需要暂停所有的工作线程
 *                       2)：并发标记(CMS concurrent mark)和用户线程一起
 *                          进行GC Roots跟踪过程，和用户线程一起工作，不需要暂停工作线程。主要标记过程，标记全部对象
 *                       3)：重新标记(CMS remark)
 *                          为了修正在并发标记期间，因用户程序继续运行而导致标记产生变动的那一部分对象的标记记录，需要暂停
 *                          (意思就是以前是要收的，但是第二次再检测时发现，对象又被重新利用起来了，二次确认)
 *                       4)：并发清楚(CMS concurrent sweep)和用户线程一起
 *                          清楚GC Roots不可达对象，和用户线程一起工作，不暂停
 *                          由于耗时最长的并发标记和并发清除过程是和用户线程一起并发工作的，所以总体看CMS是和用户线程并发执行的
 *               优点：并发收集停顿低
 *               缺点：并发执行，对CPU资源压力大
 *                    采用的标记清除算法会导致大量碎片
 *
 * G1垃圾收集器：
 *      1. JVM参数：-XX:+UseG1GC
 *      2. Heap变成：garbage-first和Metaspace
 *      3. 以前收集器特点：新生代和老年代都是各自独立且连续的内存块
 *                       新生代收集使用Eden+S0+S1进行复制算法
 *                       老年代收集必须扫描整个老年代区域
 *                       都是以尽可能少而快速地执行GC为设计原则
 *      4.G1收集器，是一款面向服务端应用的收集器
 *          G1是一个有整理内存过程的垃圾收集器，不会产生很多内存碎片
 *          G1的Stop The World更可控，G1在停顿时间上添加了预测机制，用户可以指定期望停顿时间
 *      5.特点：
 *          1) G1能充分利用多CPU、多核环境硬件优势，尽量缩短STW
 *          2) G1整体上采用标记-整理算法，局部是通过复制算法，不会产生内存碎片
 *          3) 宏观上看G1之中不再区分年轻代和老年代。把内存划分为多个独立的子区域(Region)
 *          4) G1收集器里面将整个内存区都混合在一起了，但本身依然是在小范围内要进行年轻代和
 *              老年代的区分，保留了新生代和老年代，但他们不再是物理隔离的，而是一部分Region
 *              的集合且不需要Region是连续的，也就是说依然会采用不用的GC方式来处理不同的区域
 *          5) G1虽然也是分代收集器，但整个内存分区不存在物理上的年轻代与老年代的区别，也不需要
 *               完全独立的survivor(to space)堆做赋值准备。G1只是逻辑上的分代概念，或者说每个
 *               分区都可能随G1的运行在不同代之间前后切换
 * G1底层原理：
 *      1. Region区域化垃圾收集器
 *          最大好处是化整为零，避免全内存扫描，只需要按照区域来进行扫描即可
 *      2. 使用-XX:G1HeapRegionSize=n 指定分区大小(1m~32m 必须是2的幂)，默认分为2048个分区
 *      3. 步骤：初始标记：只标记GC Roots能直接关联到的对象
 *              并发标记：进行GC Roots Tracing的过程
 *              最终标记：修正并发标记期间，因程序运行导致标记发生变化的那一部分对象
 *              筛选回收：根据时间来进行价值最大化的回收
 *      4. 和CMS相比的优势：
 *              G1没有内存碎片
 *              可以精确控制停顿
 */
public class GC_Algorithm_Device {
    public static void main(String[] args) {

        System.out.println("=======GCDemo hello");
        try {
            String str = "gentle";
            while (true){
                str += str + new Random().nextInt(88888888) +
                        new Random().nextInt(99999999);

                str.intern();
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
