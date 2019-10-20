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