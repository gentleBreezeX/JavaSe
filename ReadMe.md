# 详细笔记见代码
# 一、线程相关基础知识

    1.程序(program)：是为了完成特定任务，用某种语言编写的一组
                    指令的集合即指一段静态的代码，静态对象
                  
    2.进程(process)：是进程是一个具有一定独立功能的程序关于某个数据集合的一次运行活动。
                    它是操作系统动态执行的基本单元，在传统的操作系统中，进程既是基本
                    的分配单元，也是基本的执行单元。             
    3.线程(thread)：进程可进一步细分为线程，是一个程序内部的一条执行路径。
                    通常在一个进程中可以包含若干个线程，当然一个进程中至少有一个线程，
                    不然没有存在的意义。线程可以利用进程所拥有的资源，在引入线程的操作
                    系统中，通常都是把进程作为分配资源的基本单位，而把线程作为独立运行
                    和独立调度的基本单位，由于线程比进程更小，基本上不拥有系统资源，
                    故对它的调度所付出的开销就会小得多，能更高效的提高系统多个程序间并发执行的程度。
                      --> 若一个进程同一时间并行执行多个线程，就是支持多线程的
                      --> 线程作为调度和执行的单位，每个线程拥有独立的运行栈和
                          程序计数器（pc），线程切换开销小
                      --> 多个线程共用一些信息，但是会出现安全隐患
    4.多线程的好处：
         --> 提高应用程序的响应。对图形化界面更有意义，可以增强用户的体验
         --> 提高计算机系统CPU的利用率
         --> 改善程序结构，将既长又复杂的进程分为多个线程，独立运行，利于理解和修改
       
    5.并发：指两个或多个事件在同一个时间段内发生  (多个线程对一个点)
            例子：小米9今天上午10点，限量抢购
                  春运抢票
                  电商秒杀...
      并行：指两个或多个事件在同一时刻发生（同时发生）
            例子：泡方便面，电水壶烧水，一边撕调料倒入桶中
      
    6.线程的状态：
        NEW,(新建)
        RUNNABLE,（准备就绪）
        BLOCKED,（阻塞）
        WAITING,（不见不散）
        TIMED_WAITING,（过时不候）
        TERMINATED;(终结)
        
    7.wait/sleep  
         功能都是当前线程暂停，有什么区别？
            wait放开手去睡，放开手里的锁
            sleep握紧手去睡，醒了手里还有锁                
      
## 多线程的实现方式
    1.继承与Thread类的子类
    2.实现Runnable接口
    3.实现Callable接口  --- JDK5.0新增
>     * 创建一个实现Callable的实现类
>     * 实现call方法
>     * 创建Callable接口实现类对象
>     * 将此Callable接口实现类的对象作为参数传递到FutureTask构造器中，创建FutureTask对象
>     * FutureTask对象作为参数传递到Thread类构造器中，创建Thread对象，调用start方法
>     * 获取Callable中call方法返回值，FutureTask对象.get（）
    4.线程池
>      *      1.Executors.newFixedThreadPool(int nThreads)  -->  返回值是ExecutorService类型
>      *           创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。
>      *      2.Executors.newCachedThreadPool() -->  返回值是ExecutorService类型
>      *           创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
>      *      3.Executors.newSingleThreadExecutor()  -->  返回值是ExecutorService类型
>      *           创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
>      *      4.Executors.newScheduledThreadPool(int corePoolSize) -->  返回值是ScheduledExecutorService类型
>      *           创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
>      * 步骤：
>      *      1.提供指定线程数量的线程池
>      *      2.执行指定线程的操作，需要提供实现Runnable接口或Callable接口实现类的对象
>      *
>      * 好处：
>      *      1.提供相应速度（减少了创建新线程的时间）
>      *      2.降低资源消耗（重复利用线程池中的线程，不需要每次都创建）
>      *      3.便于线程管理
>      *          corePoolSize：核心池的大小
>      *          maximumPoolSize：最大线程数
>      *          keepAliveTime：线程没有任务时最多保持多长时间后会终止

## 线程安全问题
    1.在java中，我们通过同步机制，来解决线程的安全问题
        --> 方式一：同步代码块
                (1): synchronized(同步监视器){需要被同步的代码}
                (2):说明：操作共享数据的代码，即为需要被同步的代码
                (3):共享数据：多个线程共同操作的变量。比如 ticket
                (4):同步监视器，俗称锁，任何一个类的对象都可以充当锁
                        要求：多个线程必须同用同一把锁
                (5):同步方式的好处：解决了线程的安全问题
                        操作同步代码时，只能有一个线程参与，其他线程等待
                        相当于一个单线程的过程，效率低
                (6): 补充：a.在实现Runnable接口创建多线程的方式中，我们可以
                          考虑使用this充当同步监视器
                          b.在继承Thread类创建多线程的方式中，慎用this充当监视器
                          考虑使用当前类充当监视器
    
    
        --> 方式二：同步方法 public synchronized void method(){}
                (1)如果操作共享数据的代码完整的声明在一个方法中，我们不妨将
                    此方法声明同步的。
                (2)：同步方法仍然涉及到同步监视器，这是不需要我们显式的声明
                (3)：非静态的同步方法，同步监视器是 this
                     静态的同步方法，同步监视器是  当前类本身
        --> 方式三： 锁机制

# 二、JUC （WWWH）
    1.是什么（what）
>       java.util.concurrent
>       java.util.concurrent.atomic
>       java.util.concurrent.locks
    2.能干嘛（why 为什么要用）
    3.哪里下（where）
    4.怎么用（how）
>     --> 高内低耦的前提下，线程   操作   资源类
>     --> Lambda Express（前提是函数式接口，只有一个方法）
>            (1).拷贝小括号，写死右箭头，落地大括号
>            (2).注解@FunctionalInterface           
>            (3).default方法 (接口中可以有方法实例)         
>            (4).static方法    

# 三、Linux基础命令
## 整机：top命令(精简版 uptime)
    1.load average：负载均衡  三个值是1、5、15分钟  三个值相加除以三，超过0.6系统负载负担压力重
    2.zombie：僵尸进程数
    3.id：CPU的空闲率
    4.按1 可以详细显示各个cpu的使用情况
 
 ## CPU：vmstat命令
    1. case： vmstat -n 2 3  --->  每隔2秒打印一条信息，总共取样三次
    2. procs中：
        1) r：运行和等待CPU时间片的进程数，原则上1核CPU的运行队列不超过2
              整体系统的运行队列不能超过总核数的2倍
        2) 等待资源的进程数，比如正在等待磁盘I/O 网络I/O等
    3. cpu中：
        1) us：用户进程消耗CPU时间百分比，长期大于50% 优化程序
        2) sy：内核进程消耗的cpu时间百分比
        3) us + sy参考值为80% 大于这个值，说明CPU不足
        4) wa：系统等待IO的CPU时间百分比
        5) st：来自于一个虚拟机偷取的CPU百分比
    4.free -m：应用程序可用内存数  单位m
    5.df -h： 磁盘剩余空间
    6.磁盘IO：
        1) iostat -xdk 2 3 每隔2秒取样3次  
        2) 参数 util： 一秒中有百分几的时间用于IO操作，接近100% 表示磁盘带宽跑满，
                        需要优化程序或增加磁盘
    7.网络IO
        1) ifstat 采样时间
    8.  pidstat -d 采样时间 -p 进程号： 每隔几秒采样，打印详细IO情况                    
    9.  pidstat -p 进程号 -r 采样时间： 每隔几秒取样，打印详细内存使用情况
    10. pidstat -u 采样时间 -p 进程号： 每隔几秒取样，打印对应进程号详细CPU使用情况
    
 ## 当CPU占用高，系统变慢怎么解决
    1.先用top命令找出CPU占比最高的
    2.ps -ef或者jps进一步定位，得知是一个怎么样的后台程序
    3.定位到具体的线程或代码
        ps -mp 进程 -o THREAD,tid,time
        -m 显示所有线程
        -p pid进程使用CPU时间
        -o 该参数后是用户自定义的格式
    4.将需要的线程id转换为16进制格式(英文小写)
        printf "%x\n" 有问题的线程id
    5.jstack 线程id|grep tid(16进制线程id小写英文) -A60
            case： jstack 5101 | grep 13ee -A60   
        
 # GitHub之骚操作
 ## 常用词含义
    1.watch: 会持续收到该项目的动态
    2.fork: 复制某个项目到自己的GitHub仓库
    3.star: 可以理解为点赞
    4.clone: 将项目下载至本地
    5.follow:关注你感兴趣的作者，会收到他们的动态
 ## in关键词限制搜索范围
    1.公式：xxx关键词  in:name 或description 或readme
    2. xxx in:name 项目名中包含XXX
    2. xxx in:description  项目描述包含xxx
    3. xxx in:readme  项目的readme文件中包含xxx
    4. 组合使用：搜索项目名或readme中包含
        xxx in:name,readme
 ## stars或fork数量关键词去查找
    1.公式：xxx关键词 starts 通配符 :> 或者  :>=
        springboot stars:>=5000
        springcloud forks:>=5000
    2. 组合使用
        forks在100到200的，stars在80到100的
        springboot forks:100..200 stars:80..100
 ## awesome加强搜索
    1.公式 awesome + 关键字
    2.awesome系列 一般是用来收集学习、工具、书籍类相关的项目
 ## 高亮显示哪一/多行   
    1.公式：有代码的那个网址 在后面加 
        #L13       高亮显示13行
        #L13-L23   高亮显示13到23行
 ## 项目内搜索
    1.快捷键 t
        在打开项目的时候直接按 t
 
 ## 搜索某个地区内的大佬
    1.上海地区java方向的用户
        location:shanghai language:java