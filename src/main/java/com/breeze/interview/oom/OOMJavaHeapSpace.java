package com.breeze.interview.oom;

import java.util.Random;

/**
 * 获取jvm的堆的内存大小
 *      以及演示堆内存溢出  GC  FullGC
 *      设置：-Xms1m -Xmx1m -XX:+PrintGCDetails
 *
 * [GC (Allocation Failure) [PSYoungGen: 509K->488K(1024K)] 509K->496K(1536K), 0.0012041 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [GC (Allocation Failure) [PSYoungGen: 993K->488K(1024K)] 1001K->576K(1536K), 0.0010397 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [GC (Allocation Failure) [PSYoungGen: 988K->504K(1024K)] 1076K->668K(1536K), 0.0013256 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]
 * [GC (Allocation Failure) [PSYoungGen: 965K->504K(1024K)] 1129K->783K(1536K), 0.0011854 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [GC (Allocation Failure) [PSYoungGen: 978K->507K(1024K)] 1257K->874K(1536K), 0.0009929 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [GC (Allocation Failure) --[PSYoungGen: 865K->865K(1024K)] 1232K->1232K(1536K), 0.0006473 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 865K->490K(1024K)] [ParOldGen: 367K->427K(512K)] 1232K->918K(1536K), [Metaspace: 2953K->2953K(1056768K)], 0.0083667 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 973K->951K(1024K)] [ParOldGen: 427K->196K(512K)] 1401K->1147K(1536K), [Metaspace: 2953K->2953K(1056768K)], 0.0103123 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 963K->367K(1024K)] [ParOldGen: 426K->418K(512K)] 1390K->786K(1536K), [Metaspace: 2954K->2954K(1056768K)], 0.0090227 secs] [Times: user=0.05 sys=0.00, real=0.01 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 608K->598K(1024K)] [ParOldGen: 418K->418K(512K)] 1027K->1016K(1536K), [Metaspace: 2954K->2954K(1056768K)], 0.0048719 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * [Full GC (Allocation Failure) [PSYoungGen: 598K->598K(1024K)] [ParOldGen: 418K->418K(512K)] 1016K->1016K(1536K), [Metaspace: 2954K->2954K(1056768K)], 0.0078229 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * 	at java.util.Arrays.copyOf(Arrays.java:3332)
 * 	at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:124)
 * 	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:448)
 * 	at java.lang.StringBuilder.append(StringBuilder.java:136)
 * 	at com.breeze.jvm.OOMJavaHeapSpace.main(OOMJavaHeapSpace.java:14)
 * Heap
 *  PSYoungGen      total 1024K, used 699K [0x00000000ffe80000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 512K, 64% used [0x00000000ffe80000,0x00000000ffed2f60,0x00000000fff00000)
 *   from space 512K, 71% used [0x00000000fff00000,0x00000000fff5bf28,0x00000000fff80000)
 *   to   space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 *  ParOldGen       total 512K, used 418K [0x00000000ffe00000, 0x00000000ffe80000, 0x00000000ffe80000)
 *   object space 512K, 81% used [0x00000000ffe00000,0x00000000ffe689b8,0x00000000ffe80000)
 *  Metaspace       used 3064K, capacity 4500K, committed 4864K, reserved 1056768K
 *   class space    used 333K, capacity 388K, committed 512K, reserved 1048576K
 *

 */
public class OOMJavaHeapSpace {
    public static void main(String[] args){

        String str = "gentle breeze";
        while (true) {

            str += str + new Random().nextInt(99999999) + new Random().nextInt(88888888);
        }

    }

    public static void stackMemoryPercent(){
        long maxMemory = Runtime.getRuntime().maxMemory() ;//返回 Java 虚拟机试图使用的最大内存量。
        long totalMemory = Runtime.getRuntime().totalMemory() ;//返回 Java 虚拟机中的内存总量。
        System.out.println("TOTAL_MEMORY(-Xms) = " + totalMemory + "（字节）、" + (totalMemory / (double)1024 / 1024) + "MB");
        System.out.println("MAX_MEMORY(-Xmx) = " + maxMemory + "（字节）、" + (maxMemory / (double)1024 / 1024) + "MB");
    }
}
