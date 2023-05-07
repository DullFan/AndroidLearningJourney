# 线程

创建时间: February 24, 2023 12:43 AM
标签: Java

# 什么是进程和线程?

进程是程序运行资源分配的最小单位

线程是 CPU 调度的最小单位,必须依赖于进程而存在

任何一个程序都必须要创建线程,特别是 Java 不管任何程序都必须启动一个main 函数的主线程; Java Web 开发里面的定时任务、定时器、JSP 和 Servlet、异步消息处理机制,远程访问接口RM等,任何一个监听事件, onclick的触发事件等都离不开线程和并发的知识

# 并行和并发

**并发**:指应用能够交替执行不同的任务,比如单 CPU 核心下执行多线程并非是同时执行多个任务,如果你开两个线程执行,就是在你几乎不可能察觉到的速度不断去切换这两个任务,已达到"同时执行效果",其实并不是的,只是计算机的速度太快,我们无法察觉到而已.

**并行**:指应用能够同时执行不同的任务,例:吃饭的时候可以边吃饭边打电话,这两件事情可以同时执行

**两者区别:一个是交替执行,一个是同时执行**

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645712452056-51d54052-7aee-481a-8f2d-372914c6f2bd.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645712452056-51d54052-7aee-481a-8f2d-372914c6f2bd.png)

# 高并发编程的好处和注意事项

### 高并发好处

- 充分利用 CPU 的资源

就像平时坐地铁一样,很多人坐长线地铁的时候都在认真看书,而不是为了坐地铁而坐地铁,到家了再去看书,这样你的时间就相当于有了两倍。这就是为什么有些人时间很充裕,而有些人老是说没时间的一个原因,工作也是这样,有的时候可以并发地去做几件事情,充分利用我们的时间,CPU 也是一样,也要充分利用。

- 加快响应用户的时间

比如我们经常用百度云下载东西,都喜欢多开几个线程去下载,谁都不愿意用一个线程去下载,为什么呢?答案很简单,就是多个线程下载快。

- 可以使你的代码模块化,异步化,简单化

例如我们实现电商系统，下订单和给用户发送短信、邮件就可以进行拆分，将给用户发送短信、邮件这两个步骤独立为单独的模块，并交给其他线程去执行。这样既增加了异步的操作，提升了系统性能，又使程序模块化,清晰化和简单化。

### 注意事项

- 线程之间的安全性

在同一个进程里面的多线程是资源共享的,也就是都可以访问同一个内存地址当中的一个变量。例如:若每个线程中对全局变量、静态变量只有读操作,而无写操作,一般来说,这个全局变量是线程安全的:若有多个线程同时执行写操作,一般都需要考虑线程同步,否则就可能影响线程安全。

- 线程之间的死锁

为了解决线程之间的安全性引入了 Java 的锁机制,而一不小心就会产生 Java线程死锁的多线程问题,因为不同的线程都在等待那些根本不可能被释放的锁,从而导致所有的工作都无法完成。

假设有两个线程,分别代表两个饥饿的人,他们必须共享刀叉并轮流吃饭。他们都需要获得两个锁:共享刀和共享叉的锁。假如线程 A 获得了刀,而线程 B 获得了叉。线程 A 就会进入阻塞状态来等待获得叉,而线程 B 则阻塞来等待线程 A 所拥有的刀。

- 线程太多了会将服务器资源耗尽形成死机

线程数太多有可能造成系统创建大量线程而导致消耗完系统内存以及 CPU的“过渡切换”,造成系统的死机,那么我们该如何解决这类问题呢?

某些系统资源是有限的,如文件描述符。多线程程序可能耗尽资源,因为每个线程都可能希望有一个这样的资源。如果线程数相当大,或者某个资源的侯选线程数远远超过了可用的资源数则最好使用资源池。一个最好的示例是数据库连接池。只要线程需要使用一个数据库连接,它就从池中取出一个,使用以后再将它返回池中。资源池也称为资源库。

# 查看启动的线程

```java
//java 虚拟机线程系统的管理接口
ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//获取线程和线程堆栈信息
ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false,false);
//打印线程ID和名称
for (int i = 0; i < threadInfos.length; i++) {
    System.out.println(threadInfos[i].getThreadId()+":"+threadInfos[i].getThreadName());
}
```

输出结果

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645712752252-1dec78da-ae59-4162-9150-3b1f501c8735.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645712752252-1dec78da-ae59-4162-9150-3b1f501c8735.png)

可以发现Java天生就是多线程的，各个线程的详解：

```java
[6] Monitor Ctrl-Break //监控 Ctrl-Break 中断信号的
[5] Attach Listener //内存 dump，线程 dump，类信息统计，获取系统属性等
[4] Signal Dispatcher // 分发处理发送给 JVM 信号的线程
[3] Finalizer // 调用对象 finalize 方法的线程
[2] Reference Handler//清除 Reference 的线程
[1] main //main 线程，用户程序入口
```

# 线程开启方式

启动线程的方式有：

- **X extends Thread;，然后 X.start**

```java
public class Test {
    public static void main(String[] args) throws IOException {
       ThreadTest threadTest = new ThreadTest();
       threadTest.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("主分支"+i);
        }
    }

    public static class ThreadTest extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("执行分支"+i);
            }
        }
    }
}
```

- **X implements Runnable；然后交给 Thread 运行**

```java
public class Test {
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(new ThreadTest());
        thread.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("主分支执行"+i);
        }
    }

    public static class ThreadTest implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("分支执行"+i);
            }
        }
    }
}
```

# Thread 和 Runnable 的区别

Thread 才是 Java 里对线程的唯一抽象，Runnable 只是对任务（业务逻辑）的抽象。Thread 可以接受任意一个 Runnable 的实例并执行。

# 中止线程

线程自然终止,要么是 run 执行完成了，要么是抛出了一个未处理的异常导致线程提前结束。

### stop()

暂停、恢复和停止操作对应在线程 Thread 的 API 就是 suspend()、resume()和 stop()。但是这些 API 是过期的，也就是不建议使用的。不建议使用的原因主要有：以 suspend()方法为例，在调用后，线程不会释放已经占有的资源（比如锁），而是占有着资源进入睡眠状态，这样容易引发死锁问题。同样，stop()方法在终结一个线程时不会保证线程的资源正常释放，通常是没有给予线程完成资源释放工作的机会，因此会导致程序可能工作在不确定状态下。正因为 suspend()、resume()和 stop()方法带来的副作用，这些方法才被标注为不建议使用的过期方法。

### 中断

安全的中止则是其他线程通过调用某个线程 A 的`interrupt()`方法对其进行中断操作, 中断好比其他线程对该线程打了个招呼，“A，你要中断了”，不代表线程 A 会立即停止自己的工作，同样的 A 线程完全可以不理会这种中断请求。因为 java 里的线程是协作式的，不是抢占式的。线程通过检查自身的中断标志位是否被置为 true 来进行响应，

线程通过方法`isInterrupted()`来进行判断是否被中断，也可以调用静态方法`Thread.interrupted()`来进行判断当前线程是否被中断，不过 `Thread.interrupted()`会同时将中断标识位改写为 false。

如果一个线程处于了阻塞状态（如线程调用了 thread.sleep、thread.join、thread.wait 等），则在线程在检查中断标示时如果发现中断标示为 true，则会在这些阻塞方法调用处抛出 InterruptedException 异常，并且在抛出异常后会立即将线程的中断标示位清除，即重新设置为 false。

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645712975434-382f3714-0c8c-4531-9b19-54a7815aa867.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645712975434-382f3714-0c8c-4531-9b19-54a7815aa867.png)

不建议自定义一个取消标志位来中止线程的运行。因为 run 方法里有阻塞调用时会无法很快检测到取消标志，线程必须从阻塞调用返回后，才会检查这个取消标志。这种情况下，使用中断会更好，因为：

- 一般的阻塞方法，如 sleep 等本身就支持中断的检查，
- 检查中断位的状态和检查取消标志位没什么区别，用中断位的状态还可以避免声明取消标志位，减少资源的消耗。

注意：处于死锁状态的线程无法被中断

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        TestThread testThread = new TestThread();
        testThread.start();
        Thread.sleep(500);
        testThread.interrupt();
    }

    public static class TestThread extends Thread{
        @Override
        public void run() {
            super.run();
            String name = Thread.currentThread().getName();
            while (!isInterrupted()){
//            while (!Thread.interrupted()){
//                while (true) 自定义标志位
                System.out.println("线程执行中");
            }
            System.out.println(name+":"+isInterrupted());
        }
    }
}
```

在Runnable中的使用

```java
public static class TestThread implements Runnable {
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        //Thread.currentThread().isInterrupted()获取当前线程状态
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("线程执行中");
        }
        System.out.println(name + ":" + Thread.currentThread().isInterrupted());
    }
}
```

# 理解 run()和 start()

Thread类是Java里对线程概念的抽象，可以这样理解：我们通过`new Thread()`其实只是 new 出一个 Thread 的实例，还没有操作系统中真正的线程挂起钩来。只有执行了`start()`方法后，才实现了真正意义上的启动线程。

`start()`方法让一个线程进入就绪队列等待分配cpu，分到 cpu 后才调用实现的 `run()`方法，`start()`方法不能重复调用，如果重复调用会抛出异常。而 run 方法是业务逻辑实现的地方，本质上和任意一个类的任意一个成员方法并没有任何区别，可以重复执行，也可以被单独调用。

# join()

把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行。比如在线程 B 中调用了线程 A 的 Join()方法，直到线程 A 执行完毕后，才会继续执行线程 B。

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread threadTest1 = new Thread(new ThreadTest1());
        Thread threadTest2 = new Thread(new ThreadTest2(threadTest1));

        threadTest2.start();
        threadTest1.start();
    }

    public static class ThreadTest1 implements Runnable{
        @Override
        public void run() {
            try {
                System.out.println("1开始打饭");
                Thread.sleep(2000);
                System.out.println(
                        Thread.currentThread().getName()+"--1打饭完成"
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ThreadTest2 implements Runnable{
        private Thread thread;

        public ThreadTest2(Thread thread) {
            this.thread = thread;
        }

        public ThreadTest2() {
        }

        @Override
        public void run() {
            try {
                System.out.println("2开始打饭");
                //调用join()
                thread.join();
                System.out.println(
                        Thread.currentThread().getName()+"--2打饭完成"
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
```

# 线程的优先级

在 Java 线程中，通过一个整型成员变量 priority 来控制优先级，优先级的范围从 1~10，在线程构建的时候可以通过 setPriority(int)方法来修改优先级，默认优先级是 5，优先级高的线程分配时间片的数量要多于优先级低的线程。设置线程优先级时，针对频繁阻塞（休眠或者 I/O 操作）的线程需要设置较高优先级，而偏重计算（需要较多 CPU 时间或者偏运算）的线程则设置较低的优先级，确保处理器不会被独占。在不同的 JVM 以及操作系统上，线程规划会存在差异，有些操作系统甚至会忽略对线程优先级的设定

```java
threadTest1.setPriority(10);
threadTest2.setPriority(2);
```

# 守护线程

Daemon（守护）线程是一种支持型线程，因为它主要被用作程序中后台调度以及支持性工作。这意味着，当一个 Java 虚拟机中不存在非 Daemon 线程的时候，Java 虚拟机将会退出。可以通过调用 Thread.setDaemon(true)将线程设置为 Daemon 线程。我们一般用不上，比如垃圾回收线程就是 Daemon 线程。

Daemon 线程被用作完成支持性工作，但是在 Java 虚拟机退出时 Daemon 线程中的 finally 块并不一定会执行。在构建 Daemon 线程时，不能依靠 finally 块中的内容来确保执行关闭或清理资源的逻辑。

代码示例：

```java
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread threadTest1 = new Thread(new ThreadTest1());
        threadTest1.setPriority(10);
        //设置为守护线程
        threadTest1.setDaemon(true);
        threadTest1.start();
        Thread.sleep(1000);
    }

    public static class ThreadTest1 implements Runnable {
        @Override
        public void run() {
           while (true){
               try {
                   System.out.println("分支执行");
                   //finally不一定会执行
               } finally {
                   System.out.println("finally执行");
               }
           }
        }
    }
}
```

输出结果：

# 线程的状态变化

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645713278255-e579171b-7ce2-4dfa-9680-dcab2bc237ca.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645713278255-e579171b-7ce2-4dfa-9680-dcab2bc237ca.png)

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645713287919-6d357d66-fcc3-4272-9ade-d2a275c690b7.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645713287919-6d357d66-fcc3-4272-9ade-d2a275c690b7.png)

要想实现多线程，必须在主线程中创建新的线程对象。任何线程一般具有5种状态，即创建，就绪，运行，阻塞，终止。下面分别介绍一下这几种状态：

- **创建状态**

在程序中用构造方法创建了一个线程对象后，新的线程对象便处于新建状态，此时它已经有了相应的内存空间和其他资源，但还处于不可运行状态。新建一个线程对象可采用Thread 类的构造方法来实现，例如 “Thread thread=new Thread()”。

- **就绪状态**

新建线程对象后，调用该线程的 start() 方法就可以启动线程。当线程启动时，线程进入就绪状态。此时，线程将进入线程队列排队，等待 CPU 服务，这表明它已经具备了运行条件。

- **运行状态**

当就绪状态被调用并获得处理器资源时，线程就进入了运行状态。此时，自动调用该线程对象的 run() 方法。run() 方法定义该线程的操作和功能。

- **阻塞状态**

一个正在执行的线程在某些特殊情况下，如被人为挂起或需要执行耗时的输入/输出操作，会让 CPU 暂时中止自己的执行，进入阻塞状态。在可执行状态下，如果调用sleep(),suspend(),wait() 等方法，线程都将进入阻塞状态，发生阻塞时线程不能进入排队队列，只有当引起阻塞的原因被消除后，线程才可以转入就绪状态。

- **死亡状态**

线程调用 stop() 方法时或 run() 方法执行结束后，即处于死亡状态。处于死亡状态的线程不具有继续运行的能力。

# 小问题

**Java 程序每次运行至少启动几个线程？**

回答：至少启动两个线程，每当使用 Java 命令执行一个类时，实际上都会启动一个 JVM，每一个JVM实际上就是在操作系统中启动一个线程，Java 本身具备了垃圾的收集机制。所以在 Java 运行时至少会启动两个线程，一个是 main 线程，另外一个是垃圾收集线程。

# 线程间的共享

### synchronized 内置锁(锁的是对象)

线程开始运行，拥有自己的栈空间，就如同一个脚本一样，按照既定的代码一步一步地执行，直到终止。但是，每个运行中的线程，如果仅仅是孤立地运行，那么没有一点儿价值，或者说价值很少，如果多个线程能够相互配合完成工作，包括数据之间的共享，协同处理事情。这将会带来巨大的价值。

Java 支持多个线程同时访问一个对象或者对象的成员变量，关键字synchronized 可以修饰方法或者以同步块的形式来进行使用，它主要确保多个线程在同一个时刻，只能有一个线程处于方法或者同步块中，它保证了线程对变量访问的可见性和排他性，又称为内置锁机制。

### 对象锁和类锁

对象锁是用于对象实例方法，或者一个对象实例上的，类锁是用于类的静态方法或者一个类的class 对象上的。我们知道，类的对象实例可以有很多个，但是每个类只有一个 class 对象，所以不同对象实例的对象锁是互不干扰的，但是每个类只有一个类锁。

但是有一点必须注意的是，其实类锁只是一个概念上的东西，并不是真实存在的，类锁其实锁的是每个类的对应的 class 对象。类锁和对象锁之间也是互不干扰的。

代码示例:

```java
public class Test {
    private long count = 0;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    //用在同步块
    public void incCount(){
        synchronized (this){
            count++;
        }
    }

    //用在方法上
    public synchronized void incCount2(){
        count++;
    }

    public static class Count extends Thread{
        private Test test;

        public Count(Test test) {
            this.test = test;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 10000; i++) {
                test.incCount2();
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        Count count = new Count(test);
        Count count2 = new Count(test);
        count.start();
        count2.start();
        Thread.sleep(100);
        System.out.println(test.count);
    }
}
```

# volatile关键字

volatile关键字最轻量的同步机制,volatile 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。

```java
private volatile static boolean ready;
```

不加 volatile 时，子线程无法感知主线程修改了 ready 的值，从而不会退出循环，而加了 volatile 后，子线程可以感知主线程修改了 ready 的值，迅速退出循环。但是 volatile 不能保证数据在多个线程下同时写时的线程安全， 最适用的场景：一个线程写，多个线程读。

```java
public class Test {
    //添加关键字
    private volatile static boolean ready;
    private static int number;

    public static void main(String[] args) {
        new ThreadTest().start();
        try {
            //休眠1秒
            TimeUnit.SECONDS.sleep(1);
            //改变属性值
            number = 51;
            ready = true;
            //休眠5秒
            TimeUnit.SECONDS.sleep(5);
            System.out.println("结束执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class ThreadTest extends Thread{
        @Override
        public void run() {
            super.run();
            System.out.println("分支执行中");
            while (!ready);
            System.out.println("number:"+number);
        }
    }
}
```

# ThreadLocal 的使用

ThreadLocal 类接口很简单，只有 4 个方法：

- void set(Object value)

设置当前线程的线程局部变量的值。

- public Object get()

该方法返回当前线程所对应的线程局部变量。

- public void remove()

将当前线程局部变量的值删除，目的是为了减少内存的占用，该方法是 JDK5.0 新增的方法。需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。

- protected Object initialValue()

返回该线程局部变量的初始值，该方法是一个 protected 的方法，显然是为了让子类覆盖而设计的。这个方法是一个延迟调用方法，在线程第 1 次调用 get()或 set(Object)时才执行，并且仅执行 1 次。ThreadLocal 中的缺省实现直接返回一个 null。

```java
public final static ThreadLocal<String> RESOURCE = newThreadLocal<String>();
```

RESOURCE代表一个能够存放String类型的ThreadLocal对象。此时不论什么一个线程能够并发访问这个变量，对它进行写入、读取操作，都是线程安全的。

# 与 Synchonized 的比较

ThreadLocal 和 Synchonized 都用于解决多线程并发访问。可是 ThreadLocal与 synchronized 有本质的差别。synchronized 是利用锁的机制，使变量或代码块在某一时该仅仅能被一个线程訪问。而 ThreadLocal 为每个线程都提供了变量的副本，使得每个线程在某一时间訪问到的并非同一个对象，这样就隔离了多个线程对数据的数据共享。

代码示例:

```java
public class Test {
    private static ThreadLocal<Integer> intLocal
            = ThreadLocal.withInitial(() -> 1);

    public static void main(String[] args) {
        //启动3个线程
        Thread[] runs = new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i] = new Thread(new TestThread(i));
        }

        for (int i = 0; i < runs.length; i++) {
            runs[i].start();
        }
    }

    //线程的工作是将ThreadLocal变量的值变化，并写回，看看线程之间是否会互相影响
    public static class TestThread implements Runnable {
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("开始计算");
            Integer i = intLocal.get();
            i = i + id;
            intLocal.set(i);
            System.out.println(Thread.currentThread().getName()
                    + ":" + intLocal.get());
        }
    }
}
```

# 线程间的协作

线程之间相互配合，完成某项工作，比如：一个线程修改了一个对象的值，而另一个线程感知到了变化，然后进行相应的操作，整个过程开始于一个线程，而最终执行又是另一个线程。前者是生产者，后者就是消费者，这种模式隔离了“做什么”（what）和“怎么做”（How），简单的办法是让消费者线程不断地循环检查变量是否符合预期在 while 循环中设置不满足的条件，如果条件满足则

退出 while 循环，从而完成消费者的工作。却存在如下问题：

- 难以确保及时性。
- 难以降低开销。如果降低睡眠的时间，比如休眠 1 毫秒，这样消费者能更加迅速地发现条件变化，但是却可能消耗更多的处理器资源，造成了无端的浪费

# 等待/通知机制

是指一个线程 A 调用了对象 O 的 wait()方法进入等待状态，而另一个线程 B调用了对象 O 的 `notify()`或者 `notifyAll()`方法，线程 A 收到通知后从对象 O 的 `wait()`方法返回，进而执行后续操作。上述两个线程通过对象 O 来完成交互，而对象上的 `wait()`和 `notify/notifyAll()`的关系就如同开关信号一样，用来完成等待方和通知方之间的交互工作。

**notify()**

通知一个在对象上等待的线程,使其从 wait 方法返回,而返回的前提是该线程获取到了对象的锁，没有获得锁的线程重新进入 WAITING 状态。

**notifyAll()**

通知所有等待在该对象上的线程

**wait()**

调用该方法的线程进入 WAITING 状态,只有等待另外线程的通知或被中断才会返回.需要注意,调用 wait()方法后,会释放对象的锁

**wait(long)**

超时等待一段时间,这里的参数时间是毫秒,也就是等待长达n 毫秒,如果没有通知就超时返回

**wait (long,int)**

对于超时时间更细粒度的控制,可以达到纳秒等待和通知的标准范式

**等待方遵循如下原则:**

- 获取对象的锁。
- 如果条件不满足，那么调用对象的 wait()方法，被通知后仍要检查条件。
- 条件满足则执行对应的逻辑。

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645715596138-ca52c053-81df-47de-ad86-6dfd23bb158d.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645715596138-ca52c053-81df-47de-ad86-6dfd23bb158d.png)

**通知方遵循如下原则:**

- 获得对象的锁。
- 改变条件。
- 通知所有等待在对象上的线程。

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645715652975-2583ad3a-d061-4da8-8b9c-1f5f654748fc.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645715652975-2583ad3a-d061-4da8-8b9c-1f5f654748fc.png)

在调用 wait（）、notify()系列方法之前，线程必须要获得该对象的对象级别锁，即只能在同步方法	或同步块中调用 wait（）方法、notify()系列方法，进入 wait（）方法后，当前线程释放锁，在从 wait（）返回前，线程与其他线程竞争重新获得锁，执行 notify()系列方法的线程退出调用了 notifyAll 的 synchronized代码块的时候后，他们就会去竞争。如果其中一个线程获得了该对象锁，它就会继续往下执行，在它退出 synchronized 代码块，释放锁后，其他的已经被唤醒的线程将会继续竞争获取该锁，一直进行下去，直到所有被唤醒的线程都执行完毕。

notify 和 notifyAll 应该用谁尽可能用 notifyall()，谨慎使用 notify()，因为 notify()只会唤醒一个线程，我们无法确保被唤醒的这个线程一定就是我们需要唤醒的线程，具体表现参见代码。

# 线程池

在执行一个异步任务或并发任务时，往往是通过直接new Thread()方法来创建新的线程，这样做弊端较多，更好的解决方案是合理地利用线程池，线程池的优势很明显，如下：

- 降低系统资源消耗，通过重用已存在的线程，降低线程创建和销毁造成的消耗；
- 提高系统响应速度，当有任务到达时，无需等待新线程的创建便能立即执行；
- 方便线程并发数的管控，线程若是无限制的创建，不仅会额外消耗大量系统资源，更是占用过多资源而阻塞系统或oom等状况，从而降低系统的稳定性。线程池能有效管控线程，统一分配、调优，提供资源使用率；
- 更强大的功能，线程池提供了定时、定期以及可控线程数等功能的线程池，使用方便简单。

# 线程池用法

Java API针对不同需求，利用Executors类提供了4种不同的线程池：`newCachedThreadPool`, 	`newFixedThreadPool`, `newScheduledThreadPool`, `newSingleThreadExecutor`，接下来讲讲线程池的用法。

### newCachedThreadPool

创建一个可缓存的无界线程池，该方法无参数。当线程池中的线程空闲时间超过60s则会自动回收该线程，当任务超过线程池的线程数则创建新线程。线程池的大小上限为Integer.MAX_VALUE，可看做是无限大。

```java
public void cachedThreadPoolDemo(){
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
        final int index = i;

        cachedThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+", index="+index);
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

运行结果:

```java
pool-1-thread-1, index=0
pool-1-thread-1, index=1
pool-1-thread-1, index=2
pool-1-thread-1, index=3
pool-1-thread-1, index=4
```

从运行结果可以看出，整个过程都在同一个线程pool-1-thread-1中运行，后面线程复用前面的线程。

### newFixedThreadPool

创建一个固定大小的线程池，该方法可指定线程池的固定大小，对于超出的线程会在`LinkedBlockingQueue`队列中等待。

```java
public void fixedThreadPoolDemo(){
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    for (int i = 0; i < 6; i++) {
        final int index = i;

        fixedThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+", index="+index);
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

运行结果:

```java
pool-1-thread-1, index=0
pool-1-thread-2, index=1
pool-1-thread-3, index=2
pool-1-thread-1, index=3
pool-1-thread-2, index=4
pool-1-thread-3, index=5
```

从运行结果可以看出，线程池大小为3，每休眠1s后将任务提交给线程池的各个线程轮番交错地执行。线程池的大小设置，可参数`Runtime.getRuntime().availableProcessors()`。

### newSingleThreadExecutor

创建一个只有线程的线程池，该方法无参数，所有任务都保存队列LinkedBlockingQueue中，等待唯一的单线程来执行任务，并保证所有任务按照指定顺序(FIFO或优先级)执行。

```java
public void singleThreadExecutorDemo(){
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    for (int i = 0; i < 3; i++) {
        final int index = i;

        singleThreadExecutor.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+", index="+index);
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

运行结果:

```java
pool-1-thread-1, index=0
pool-1-thread-1, index=1
pool-1-thread-1, index=2
```

从运行结果可以看出，所有任务都是在单一线程运行的。

### newScheduledThreadPool

创建一个可定时执行或周期执行任务的线程池，该方法可指定线程池的核心线程个数。

```java
public void scheduledThreadPoolDemo(){
    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
    //定时执行一次的任务，延迟1s后执行
    scheduledThreadPool.schedule(new Runnable() {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+", delay 1s");
        }
    }, 1, TimeUnit.SECONDS);

    //周期性地执行任务，延迟2s后，每3s一次地周期性执行任务
    scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+", every 3s");
        }
    }, 2, 3, TimeUnit.SECONDS);
}
```

运行结果:

```java
pool-1-thread-1, delay 1s
pool-1-thread-1, every 3s
pool-1-thread-2, every 3s
pool-1-thread-2, every 3s
...
```

- schedule(Runnable command, long delay, TimeUnit unit)，延迟一定时间后执行Runnable任务；
- schedule(Callable callable, long delay, TimeUnit unit)，延迟一定时间后执行Callable任务；
- scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)，延迟一定时间后，以间隔period时间的频率周期性地执行任务；
- scheduleWithFixedDelay(Runnable command, long initialDelay, long delay,TimeUnit unit)，与scheduleAtFixedRate()方法很类似，但是不同的是scheduleWithFixedDelay()方法的周期时间间隔是以上一个任务执行结束到下一个任务开始执行的间隔，而scheduleAtFixedRate()方法的周期时间间隔是以上一个任务开始执行到下一个任务开始执行的间隔，也就是这一些任务系列的触发时间都是可预知的。

### ThreadPoolExecutor

```java
public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
    this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
         Executors.defaultThreadFactory(), defaultHandler);
}
```

创建线程池，在构造一个新的线程池时，必须满足下面的条件：

- corePoolSize（线程池基本大小）必须大于或等于0；
- maximumPoolSize（线程池最大大小）必须大于或等于1；
- maximumPoolSize必须大于或等于corePoolSize；
- keepAliveTime（线程存活保持时间）必须大于或等于0；
- workQueue（任务队列）不能为空；
- threadFactory（线程工厂）不能为空，默认为DefaultThreadFactory类
- handler（线程饱和策略）不能为空，默认策略为ThreadPoolExecutor.AbortPolicy。

参数说明：

- corePoolSize（线程池基本大小）：当向线程池提交一个任务时，若线程池已创建的线程数小于corePoolSize，即便此时存在空闲线程，也会通过创建一个新线程来执行该任务，直到已创建的线程数大于或等于corePoolSize时，才会根据是否存在空闲线程，来决定是否需要创建新的线程。除了利用提交新任务来创建和启动线程（按需构造），也可以通过 prestartCoreThread() 或 prestartAllCoreThreads() 方法来提前启动线程池中的基本线程。
- maximumPoolSize（线程池最大大小）：线程池所允许的最大线程个数。当队列满了，且已创建的线程数小于maximumPoolSize，则线程池会创建新的线程来执行任务。另外，对于无界队列，可忽略该参数。
- keepAliveTime（线程存活保持时间）：默认情况下，当线程池的线程个数多于corePoolSize时，线程的空闲时间超过keepAliveTime则会终止。但只要keepAliveTime大于0，allowCoreThreadTimeOut(boolean) 方法也可将此超时策略应用于核心线程。另外，也可以使用setKeepAliveTime()动态地更改参数。
- unit（存活时间的单位）：时间单位，分为7类，从细到粗顺序：NANOSECONDS（纳秒），MICROSECONDS（微妙），MILLISECONDS（毫秒），SECONDS（秒），MINUTES（分），HOURS（小时），DAYS（天）；
- workQueue（任务队列）：用于传输和保存等待执行任务的阻塞队列。可以使用此队列与线程池进行交互：
- 如果运行的线程数少于 corePoolSize，则 Executor 始终首选添加新的线程，而不进行排队。
- 如果运行的线程数等于或多于 corePoolSize，则 Executor 始终首选将请求加入队列，而不添加新的线程。
- 如果无法将请求加入队列，则创建新的线程，除非创建此线程超出 maximumPoolSize，在这种情况下，任务将被拒绝。
- threadFactory（线程工厂）：用于创建新线程。由同一个threadFactory创建的线程，属于同一个ThreadGroup，创建的线程优先级都为Thread.NORM_PRIORITY，以及是非守护进程状态。threadFactory创建的线程也是采用new Thread()方式，threadFactory创建的线程名都具有统一的风格：pool-m-thread-n（m为线程池的编号，n为线程池内的线程编号）;
- handler（线程饱和策略）：当线程池和队列都满了，则表明该线程池已达饱和状态。
- ThreadPoolExecutor.AbortPolicy：处理程序遭到拒绝，则直接抛出运行时异常 RejectedExecutionException。(默认策略)
- ThreadPoolExecutor.CallerRunsPolicy：调用者所在线程来运行该任务，此策略提	供简单的反馈控制机制，能够减缓新任务的提交速度。
- ThreadPoolExecutor.DiscardPolicy：无法执行的任务将被删除。
- ThreadPoolExecutor.DiscardOldestPolicy：如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重新尝试执行任务（如果再次失败，则重复此过程）。

# 排队有三种通用策略

- 直接提交。工作队列的默认选项是 SynchronousQueue，它将任务直接提交给线程而不保持它们。在此，如果不存在可用于立即运行任务的线程，则试图把任务加入队列将失败，因此会构造一个新的线程。此策略可以避免在处理可能具有内部依赖性的请求集时出现锁。直接提交通常要求无界 maximumPoolSizes 以避免拒绝新提交的任务。当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
- 无界队列。使用无界队列（例如，不具有预定义容量的 LinkedBlockingQueue）将导致在所有 corePoolSize 线程都忙时新任务在队列中等待。这样，创建的线程就不会超过 corePoolSize。（因此，maximumPoolSize 的值也就无效了。）当每个任务完全独立于其他任务，即任务执行互不影响时，适合于使用无界队列；例如，在 Web 页服务器中。这种排队可用于处理瞬态突发请求，当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
- 有界队列。当使用有限的 maximumPoolSizes 时，有界队列（如 ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制。队列大小和最大池大小可能需要相互折衷：使用大型队列和小型池可以最大限度地降低 CPU 使用率、操作系统资源和上下文切换开销，但是可能导致人工降低吞吐量。如果任务频繁阻塞（例如，如果它们是 I/O 边界），则系统可能为超过您许可的更多线程安排时间。使用小型队列通常要求较大的池大小，CPU 使用率较高，但是可能遇到不可接受的调度开销，这样也会降低吞吐量。

# 线程池关闭

调用线程池的shutdown()或shutdownNow()方法来关闭线程池

- shutdown原理：将线程池状态设置成SHUTDOWN状态，然后中断所有没有正在执行任务的线程。
- shutdownNow原理：将线程池的状态设置成STOP状态，然后中断所有任务(包括正在执行的)的线程，并返回等待执行任务的列表。

中断采用interrupt方法，所以无法响应中断的任务可能永远无法终止。但调用上述的两个关闭之一，isShutdown()方法返回值为true，当所有任务都已关闭，表示线程池关闭完成，则isTerminated()方法返回值为true。当需要立刻中断所有的线程，不一定需要执行完任务，可直接调用shutdownNow()方法。

# 线程池流程

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645716804960-8b8f88d6-c583-49f5-b170-9ea962104fdf.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645716804960-8b8f88d6-c583-49f5-b170-9ea962104fdf.png)

- 判断核心线程池是否已满，即已创建线程数是否小于corePoolSize？没满则创建一个新的工作线程来执行任务。已满则进入下个流程。
- 判断工作队列是否已满？没满则将新提交的任务添加在工作队列，等待执行。已满则进入下个流程。
- 判断整个线程池是否已满，即已创建线程数是否小于maximumPoolSize？没满则创建一个新的工作线程来执行任务，已满则交给饱和策略来处理这个任务。

# 合理地配置线程池

需要针对具体情况而具体处理，不同的任务类别应采用不同规模的线程池，任务类别可划分为CPU密集型任务、IO密集型任务和混合型任务。

- 对于CPU密集型任务：线程池中线程个数应尽量少，不应大于CPU核心数；
- 对于IO密集型任务：由于IO操作速度远低于CPU速度，那么在运行这类任务时，CPU绝大多数时间处于空闲状态，那么线程池可以配置尽量多些的线程，以提高CPU利用率；
- 对于混合型任务：可以拆分为CPU密集型任务和IO密集型任务，当这两类任务执行时间相差无几时，通过拆分再执行的吞吐率高于串行执行的吞吐率，但若这两类任务执行时间有数据级的差距，那么没有拆分的意义。

# 线程池监控

利用线程池提供的参数进行监控，参数如下：

- taskCount：线程池需要执行的任务数量。
- completedTaskCount：线程池在运行过程中已完成的任务数量，小于或等于taskCount。
- largestPoolSize：线程池曾经创建过的最大线程数量，通过这个数据可以知道线程池是否满过。如等于线程池的最大大小，则表示线程池曾经满了。
- getPoolSize：线程池的线程数量。如果线程池不销毁的话，池里的线程不会自动销毁，所以这个大小只增不减。
- getActiveCount：获取活动的线程数。

通过扩展线程池进行监控：继承线程池并重写线程池的beforeExecute()，afterExecute()和terminated()方法，可以在任务执行前、后和线程池关闭前自定义行为。如监控任务的平均执行时间，最大执行时间和最小执行时间等。