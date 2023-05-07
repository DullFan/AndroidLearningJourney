# JVM

创建时间: February 24, 2023 12:43 AM
标签: Java

# 什么是JVM

JVM代表Java虚拟机（Java Virtual Machine）。它是Java平台的核心组件之一，负责将Java字节码（Java代码编译后的中间形式）转换为计算机可以理解和执行的机器码。JVM提供了一种独立于硬件和操作系统的执行环境，使得Java程序可以在不同的平台上运行，而不需要对程序进行重写或重新编译。

JVM还负责Java程序的内存管理、垃圾回收、安全性等方面的任务。它提供了一组标准的Java API（应用程序接口），用于访问底层系统资源，如文件、网络、输入输出等。JVM还可以通过Java Native Interface（JNI）与本地代码进行交互，使得Java程序可以调用C/C++等语言编写的库和程序。

总之，JVM是Java语言的核心技术之一，它使得Java程序具有可移植性、安全性、高效性等优点，成为业界广泛使用的编程语言之一。

JVM也可以被视为一种规范或者标准。Java虚拟机规范定义了JVM的行为和特性，这些特性包括JVM的指令集、类加载机制、内存管理、异常处理、线程执行模型等等。Java虚拟机规范由Java Community Process（JCP）制定和维护，是Java平台的核心规范之一。

根据Java虚拟机规范，可以实现不同的JVM，这些JVM可以运行同样的Java字节码。因此，Java程序可以在不同的操作系统和硬件平台上运行，只需要安装相应的JVM即可。JVM实现者需要遵守Java虚拟机规范，确保其JVM的行为和特性与标准一致，从而保证Java程序的可移植性和兼容性。

Java虚拟机规范是由Java Community Process（JCP）制定和维护的。JCP是一个开放的、多利益相关方参与的组织，旨在推动Java技术的发展和标准化。JCP由Java技术领导者、开发者、用户、厂商等组成，它通过制定Java规范、技术规范、参考实现等方式，推动Java技术的发展和应用。

**常见的JVM**

**Hotspot**：目前使用的最多的Java虚拟机。在命令行 java –version。它会输出你现在使用的虚拟机的名字、版本等信息、执行模式。

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705127478-2249786d-d106-4c24-ad02-814343c8bd6c.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705127478-2249786d-d106-4c24-ad02-814343c8bd6c.png)

# Java程序运行过程

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645704982663-b129e7d3-8a88-4f74-b38f-012af8668913.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645704982663-b129e7d3-8a88-4f74-b38f-012af8668913.png)

# Java虚拟机的内存结构

### 运行时数据区域

Java虚拟机在执行Java程序的过程中会把它所管理的**内存**划分为若干个不同的数据区域

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705150771-661c2ccf-6214-4420-97aa-b368c9fe85a7.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705150771-661c2ccf-6214-4420-97aa-b368c9fe85a7.png)

Java 引以为豪的就是它的自动内存管理机制。相比于 C++的手动内存管理、复杂难以理解的指针等，Java 程序写起来就方便的多。

所以要深入理解JVM必须理解内存虚拟化的概念。

在JVM中，JVM 内存主要分为**堆、程序计数器、方法区、虚拟机栈和本地方法栈**等。

> 堆区：只存放类对象，线程共享
方法区：又叫静态存储区，存放class文件和静态数据，线程共享
栈区：存放方法、局部变量、基本类型变量区、执行环境上下文、操作指令区，线程不共享
> 

同时按照与线程的关系也可以这么划分区域：

- **线程私有区域**：一个线程拥有单独的一份内存区域。
- **线程共享区域**：被所有线程共享，且只有一份。

### 本地方法栈

本地方法栈跟 Java 虚拟机栈的功能类似，Java 虚拟机栈用于管理 Java 函数的调用，而本地方法栈则用于管理本地方法的调用。但本地方法并不是用 Java 实现的，而是由 C 语言实现的(比如Object.hashcode方法)。

本地方法栈是和虚拟机栈非常相似的一个区域，它服务的对象是 native 方法。你甚至可以认为虚拟机栈和本地方法栈是同一个区域。虚拟机规范无强制规定，各版本虚拟机自由实现 ，HotSpot直接把本地方法栈和虚拟机栈合二为一 。

### 方法区

方法区（Method Area）是可供各条线程共享的运行时内存区域。它存储了每一个类的结构信息，例如运行时常量池（Runtime Constant Pool）字段和方法数据、构造函数和普通方法的字节码内容、还包括一些在类、实例、接口初始化时用到的特殊方法

### 程序计数器

较小的内存空间，当前线程执行的字节码的行号指示器；各线程之间独立存储，互不影响。

程序计数器是一块很小的内存空间，主要用来记录各个线程执行的字节码的地址，例如，分支、循环、跳转、异常、线程恢复等都依赖于计数器。由于 Java 是多线程语言，当执行的线程数量超过 CPU 核数时，线程之间会根据时间片轮询争夺 CPU 资源。如果一个线程的时间片用完了，或者是其它原因导致这个线程的 CPU 资源被提前抢夺，那么这个退出的线程就需要单独的一个程序计数器，来记录下一条运行的指令。

因为JVM是虚拟机，内部有完整的指令与执行的一套流程，所以在运行Java方法的时候需要使用程	序计数器（记录字节码执行的地址或行号），如果是遇到本地方法（native方法），这个方法不是JVM来具体执行，所以程序计数器不需要记录了，这个是因为在操作系统层面也有一个程序计数器，这个会记录本地代码的执行的地址，所以在执行native方法时，JVM中程序计数器的值为空(Undefined)。

另外程序计数器也是JVM中唯一不会OOM(OutOfMemory)的内存区域。

### 堆

堆是 JVM 上最大的内存区域，我们申请的几乎所有的对象，都是在这里存储的。我们常说的垃圾回收，操作的对象就是堆。堆空间一般是程序启动时，就申请了，但是并不一定会全部使用。堆一般设置成可伸缩的。随着对象的频繁创建，堆空间占用的越来越多，就需要不定期的对不再使用的对象进行回收。这个在 Java 中，就叫作 GC（Garbage Collection）。那一个对象创建的时候，到底是在堆上分配，还是在栈上分配呢？这和两个方面有关：对象的类型和在 Java 类中存在的位置。

# Java 的对象可以分为基本数据类型和普通对象

对于普通对象来说，JVM 会首先在堆上创建对象，然后在其他地方使用的其实是它的引用。比如，把这个引用保存在虚拟机栈的局部变量表中。

对于基本数据类型来说（byte、short、int、long、float、double、char)，有两种情况。当你在方法体内声明了基本数据类型的对象，它就会在栈上直接分配。其他情况，都是在堆上分配。

# 虚拟机栈

**栈的数据结构**：先进后出(FILO)的数据结构，

**虚拟机栈的作用**：在JVM运行过程中存储当前线程运行方法所需的数据，指令、返回地址。

**虚拟机栈是基于线程的**：哪怕你只有一个 main() 方法，也是以线程的方式运行的。在线程的生命周期中，参与计算的数据会频繁地入栈和出栈，栈的生命周期是和线程一样的。

**虚拟机栈的大小缺省为1M**，可用参数 –Xss调整大小，例如-Xss256k。

**栈帧**：在每个 Java 方法被调用的时候，都会创建一个栈帧，并入栈。一旦方法完成相应的调用，则出栈。

栈帧大体都包含四个区域：**局部变量表、操作数栈、动态连接、返回地址**

- 局部变量表

顾名思义就是局部变量的表，用于存放我们的局部变量的（方法中的变量）。首先它是一个32位的长度，主要存放我们的Java的八大基础数据类型，一般32位就可以存放下，如果是64位的就使用高低位占用两个也可以存放下，如果是局部的一些对象，比如我们的Object对象，我们只需要存放它的一个引用地址即可。

- 操作数据栈

存放java方法执行的操作数的，它就是一个栈，先进后出的栈结构，操作数栈，就是用来操作的，操作的的元素可以是任意的java数据类型，所以我们知道一个方法刚刚开始的时候，这个方法的操作数栈就是空的。操作数栈本质上是JVM执行引擎的一个工作区，也就是方法在执行，才会对操作数栈进行操作，如果代码不不执行，操作数栈其实就是空的。

- 动态连接

Java语言特性多态。

- 返回地址(完成出口)

正常返回（调用程序计数器中的地址作为返回）、异常的话（通过异常处理器表<非栈帧中的>来确定）同时，虚拟机栈这个内存也不是无限大，它有大小限制，默认情况下是1M。

如果我们不断的往虚拟机栈中入栈帧，但是就是不出栈的话，那么这个虚拟机栈就会爆掉。

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705568161-51a6dea0-41e9-42ff-abe5-81d25ea57506.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705568161-51a6dea0-41e9-42ff-abe5-81d25ea57506.png)

# 查看字节码

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705588831-f684c30b-bb2d-4263-bcd7-eadf8b899582.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705588831-f684c30b-bb2d-4263-bcd7-eadf8b899582.png)

下方代码就是计数器代码(需要使用dos窗口,还需要编译后的.class文件)

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705603060-c6e80717-39b0-4b5b-8b97-972697930470.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705603060-c6e80717-39b0-4b5b-8b97-972697930470.png)

具体属性可以查看: [https://cloud.tencent.com/developer/article/1333540](https://cloud.tencent.com/developer/article/1333540)

# 栈帧执行流程

java虚拟机只会执行最顶层的栈帧,所以不存在重复的问题

如果调用的静态方法this就不需要了,如果是非静态方法就需要this

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705653632-7317937c-9b68-4288-bb66-958e2bbb77c8.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705653632-7317937c-9b68-4288-bb66-958e2bbb77c8.png)

代码:

```java
public class Test {
    public int work()throws Exception{
        //iconst_5,istore_1,一行java代码代表两行字节码
        int x = 5;
        int y = 4;
        int z = (x + y) * 10;
        return z;
    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.work();
        test.hashCode();
    }
}
```

# JVM其他位置区域

### 元空间

方法区与堆空间类似，也是一个共享内存区，所以方法区是线程共享的。假如两个线程都试图访问方法区中的同一个类信息，而这个类还没有装入 JVM，那么此时就只允许一个线程去加载它，另一个线程必须等待。在 HotSpot 虚拟机、Java7 版本中已经将永久代的静态变量和运行时常量池转移到了堆中，其余部分则存储在 JVM 的非堆内存中，而 Java8 版本已经将方法区中实现的永久代去掉了，并用元空间（class metadata）代替了之前的永久代，并且元空间的存储位置是本地内存。

### 运行时常量池

运行时常量池（Runtime Constant Pool）是每一个类或接口的常量池（Constant_Pool）的运行时表示形式，它包括了若干种不同的常量：从编译期可知的数值字面量到必须运行期解析后才能获得的方法或字段引用。运行时常量池是方法区的一部分。运行时常量池相对于Class常量池的另外一个重要特征是具备动态性。

### 直接内存（堆外内存）

直接内存有一种更加科学的叫法，堆外内存。JVM 在运行时，会从操作系统申请大块的堆内存，进行数据的存储；同时还有虚拟机栈、本地方法栈和程序计数器，这块称之为栈区。操作系统剩余的内存也就是堆外内存。它不是虚拟机运行时数据区的一部分，也不是java虚拟机规范中定义的内存区域；如果使用了NIO,这块区域会被频繁使用，在java堆内可以用directByteBuffer对象直接引用并操作；这块内存不受java堆大小限制，但受本机总内存的限制，可以通过`-XX:MaxDirectMemorySize`来设置（默认与堆内存最大值一样），所以也会出现OOM异常 (这个虽然不是运行时数据区的一部分，但是会被频繁使用。可以理解成没有被虚拟机化的操作系统上的其他内存（比如操作系统上有8G内存，被JVM虚拟化了3G，那么还剩余5G,JVM是借助一些工具使用这5G内存的,这个内存部分称之为直接内存）)

代码示例：

```java
public class Test {
    //todo 静态变量
    static int age = 19;
    //todo 常量
    final static int sex = 1;
    //todo 成员变量指向(对象)在类加载的时候不会执行
    Test test = new Test();
    private boolean isFlag;

    public static void main(String[] args){
        //todo 局部变量
        int x = 18;
        long y = 1;
        Test test2 = new Test();
        test2.isFlag = true;
        test2.hashCode();
        //todo 直接分配128MB的直接内存
        ByteBuffer bb = ByteBuffer.allocateDirect(128*1024*1024);
    }
}
```

### JVM的内存区域

代码示例：

```java
public class Test {
    public final static String MAN_TYPE = "man"; // 常量
    public static String WOMAN_TYPE = "woman";  // 静态变量
    public static void  main(String[] args)throws Exception {
        Teacher T1 = new Teacher();
        T1.setName("江河1");
        T1.setSexType(MAN_TYPE);
        T1.setAge(36);
        for(int i =0 ;i<15 ;i++){
            //对象经历过垃圾回收,没有被回收掉,age+1  age到达15的时候为老年代
            //底层记录对象的年龄的字段为4位二进制,最大为1111也就是15
            System.gc();//主动触发GC 垃圾回收 15次--- T1存活  T1要进入老年代
        }
        Teacher T2 = new Teacher();
        T2.setName("江河2");
        T2.setSexType(MAN_TYPE);
        T2.setAge(18);
        Thread.sleep(Integer.MAX_VALUE);//线程休眠   T2还是在新生代
    }
}

class Teacher{
    String name;
    String sexType;
    int age;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSexType() {
        return sexType;
    }
    public void setSexType(String sexType) {
        this.sexType = sexType;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
```

### JVM内存处理流程(需要对照以上代码)

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705816117-37d613a1-e6e1-411e-83ef-15fc9b18e071.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705816117-37d613a1-e6e1-411e-83ef-15fc9b18e071.png)

JVM 的整个处理过程如下：

1. JVM 向操作系统申请内存，JVM 第一步就是通过配置参数或者默认配置参数向操作系统申请内存空间。

2. JVM 获得内存空间后，会根据配置参数分配堆、栈以及方法区的内存大小。

3. 完成上一个步骤后， JVM 首先会执行构造器，编译器会在.java 文件被编译成.class 文件时，收集	所有类的初始化代码，包括静态变量赋值语句、静态代码块、静态方法，静态变量和常量放入方法区

4. 执行方法。启动 main 线程，执行 main 方法，开始执行第一行代码。此时堆内存中会创建一个 Teacher 对象，对象引用 T1就存放在栈中。

执行其他方法时，具体的操作：栈帧执行对内存区域的影响。栈帧执行对内存区域的影响

### 堆空间分代划分(需要对照上方代码)

堆被划分为新生代和老年代（Tenured），新生代又被进一步划分为 `Eden` 和 `Survivor` 区，最后 `Survivor` 由 `FromSurvivor` 和 `To Survivor` 组成。

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705929967-0ea79bac-0836-48e7-8e56-3a581d623147.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705929967-0ea79bac-0836-48e7-8e56-3a581d623147.png)

# GC概念

GC- Garbage Collection  垃圾回收，在JVM中是自动化的垃圾回收机制，我们一般不用去关注，在JVM中GC的重要区域是堆空间。我们也可以通过一些额外方式主动发起它，比如System.gc(),主动发起。(项目中不需要主动调用)

# JHSDB工具

JHSDB是一款基于服务性代理实现的进程外调试工具。服务性代理是HotSpot虚拟机中一组用于映射Java虚拟机运行信息的，主要基于Java语言实现的API集合。

到命令行中输入  `java -cp .\sa-jdi.jar sun.jvm.hotspot.HSDB`(需要在JDK中的lib中使用)

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705974589-0ae903be-3636-43ef-b540-19020f6d728e.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705974589-0ae903be-3636-43ef-b540-19020f6d728e.png)

查看进程号

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705997477-0dd59c96-eaad-4189-a36c-1ecf7a84fbd4.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645705997477-0dd59c96-eaad-4189-a36c-1ecf7a84fbd4.png)

通过进程查看参数(默认参数)

![https://cdn.nlark.com/yuque/0/2022/png/26240361/1645706016328-4f2c6695-3030-4b2e-8f0d-4dc7c370890c.png](https://cdn.nlark.com/yuque/0/2022/png/26240361/1645706016328-4f2c6695-3030-4b2e-8f0d-4dc7c370890c.png)

# JVM,JRE,JDK关系(补充知识)

**JVM**只是一个翻译，把Class翻译成机器识别的代码，但是需要注意，JVM 不会自己生成代码，需要大家编写代码，同时需要很多依赖类库，这个时候就需要用到JRE。同时JVM是一个虚拟化的操作系统，所以除了要虚拟指令之外，最重要的一个事情就是需要虚拟化内存，

**JRE**它除了包含JVM之外，提供了很多的类库（就是我们说的jar包，它可以提供一些即插即用的功能，比如读取或者操作文件，连接网络，使用I/O等等之类的）这些东西就是JRE提供的基础类库。JVM 标准加上实现的一大堆基础类库，就组成了 Java 的运行时环境，也就是我们常说的 	JRE(Java Runtime Environment)

但对于程序员来说，JRE还不够。我写完要编译代码，还需要调试代码，还需要打包代码、有时候还需要反编译代码。所以我们会使用**JDK**，因为JDK还提供了一些非常好用的小工具，比如 javac（编译代码）、java、jar （打包代码）、javap（反编译<反汇编>）等。这个就是JDK。

JVM的作用是：从软件层面屏蔽不同操作系统在底层硬件和指令的不同。这个就是我们在宏观方面	对JVM的一个认识。

同时JVM是一个虚拟化的操作系统，类似于Linux或者Windows的操作系统，只是它架在操作系统上，接收字节码也就是class，把字节码翻译成操作系统上的机器码且进行执行。

# 总结

JVM在操作系统上启动，申请内存，先进行运行时数据区的初始化，然后把类加载到方法区，最后执行方法。方法的执行和退出过程在内存上的体现上就是虚拟机栈中栈帧的入栈和出栈。同时在方法的执行过程中创建的对象一般情况下都是放在堆中，最后堆中的对象也是需要进行垃圾回收清理的。