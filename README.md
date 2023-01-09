# JUC 学习

> 在 Java 5.0 提供了 `java.util.concurrent`(简称JUC)包，在此包中增加了在并发编程中很常用的工具类。此包包括了几个小的、已标准化的可扩展框架，并提供一些功能实用的类，没有这些类，一些功能会很难实现或实现起来冗长乏味  

## 多线程的本质 以及 一些基础概念

> 理想状态下多线程并没有提高效率, 但是 cpu 的使用率不是百分百的, 会有空闲时间(IO 或者 网络时就需要等待). 多线程的本质就是提高资源使用效率来提高系统的效率. 某一个线程执行到 IO 或者网络操作时 另一个线程获取 cpu 执行权, 减少 CPU 等待时间提升性能
>
> ![image-20230108113709789](https://oss.yiki.tech/xh/image-20230108113709789.png)

![](https://oss.yiki.tech/oss/202212161151596.png)

| 名词     | 释意                                                         |
| -------- | ------------------------------------------------------------ |
| 软件     | 下载并安装在磁盘上的软件                                     |
| 应用程序 | 软件运行后在 `内存 ` 占用的内存空间                          |
| 进程     | `进程`是指运行中的`应用程序`, 每一个进程都有自己`独立的内存空间`; 一个应用程序可以同时运行多个进程; 系统运行的一个程序即是一个进程从创建、运行到消亡的一个过程<br /><br />进程是独立的, 进程之间的数据是不共享的. 但是一个进程中的多个线程数共享内存空间的,  从而能够完成线程间的通信 |
| 线程     | `线程`是进程中的`一个执行单元`, 负责当前进程中程序的执行, 一个程序中至少有一个线程. 一个进程中可以有多个线程, 这个程序可以称之为多线程程序<br /><br />多线程则扩展了多进程的概念，使得通一个进程可以同时并发处理多个任务。线程（Thread）也被称为轻量级进程。就像进程在操作系统中地位一样，线程在进程中也是独立的、并发的执行流。当进程被初始化后，主线程就被创建了，对于Java程序来说，main线程就是主线程，但我们可以在该进程内创建多条顺序执行路径，这些独立的顺序执行路径就是线程。 |
| 并行     | 在同一时刻，有多个任务在多个处理器上同时执行. `多个任务同时执行` |
| 串行     | 在同 一个 CPU 上`多个任务依次执行`                           |
| 并发     | 同一个时刻只能有一条指令执行，但多个进程的指令被快速轮换执行，使得在宏观上具有多个进程同时执行的效果. `多个任务交替执行` |

## 操作系统任务调度的两种常见模式

* 分时操作系统: 
  * 我们认为 CPU 是计算资源, CPU 只有一个, 那么我们会将 CPU 的计算资源进行切割, 切割成无数个微小单元. 平均分配给多个线程去执行.
  * 例如 我们现在有两个计算任务, 那么 cpu 就会将计算资源切割成多个时间片. 每个时间片是 200 ns, 第一个时间片给任务一, 第二个时间片给任务二. 第三个时间片给任务一 依次类推

* 抢占式:
  * CPU 的计算资源-时间片, 不是平均分配的, 是需要靠抢夺的, 谁的优先级高, 那么谁获取 CPU 时间片的概率就大

## 线程的生命周期

![](https://oss.yiki.tech/oss/202212161424440.png)

## 线程分类

| 名词                | 解释                                          | 优先级                                                       |
| ------------------- | --------------------------------------------- | ------------------------------------------------------------ |
| 守护线程(🧚精灵线程) | 为了保护 / 保障普通线程能够正常执行的一类线程 | 较低、比普通线程低<br />可以通过setDaemon(boolean on) 来设置某线程为守护线程(必须在 start() 调用前调用)<br />用isDaemon() 来判断是否是精灵线程<br />精灵线程不能单独运行(当应用程序的所有非精灵线程停止运行时, 即使精灵线程还在运行, 该应用程序也会终止) |
| 普通线程            | 基础线程                                      | 通常是 5                                                     |

## 8 锁问题

> synchronized实现同步的基础：Java中的每一个对象都可以作为锁

* 对于普通同步方法，锁是当前实例对象
* 对于静态同步方法，锁是当前类的Class对象
* 对于同步方法块，锁是Synchonized括号里配置的对象

> 如果**一个实例对象**的**非静态同步方法**获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁后才能获取锁；可是**不同实例对象**的非静态同步方法因为用的**是不同对象的锁**，所以毋须等待其他实例对象的非静态同步方法释放锁，就可以获取自己的锁
>
> **所有的静态同步方法用的是同一把锁——类对象本身**。不管是不是同一个实例对象，只要是一个类的对象，一旦一个静态同步方法获取锁之后，其他对象的静态同步方法，都必须等待该方法释放锁之后，才能获取锁。
>
> 静态同步方法（Class对象锁）与非静态同步方法（实例对象锁）之间是不会有竞态条件的。

## Lock

> lock 锁实现提供了比使用同步方法和语句可以获得的更广泛的锁操作。它们允许更灵活的结构(可以指定在哪里添加锁 匙放锁)，可能具有非常不同的属性，并且可能支持多个关联的条件对象

### 公平性

| 名称     | 解释                                                         |
| -------- | ------------------------------------------------------------ |
| 公平锁   | 每个线程获取锁的顺序是按照线程访问锁的先后顺序获取的，最前面的线程总是最先获取到锁 |
| 非公平锁 | 每个线程获取锁的顺序是随机的，并不会遵循先来先得的规则，所有线程会竞争获取锁 |

![](https://oss.yiki.tech/oss/202212171057675.png)

### 可重入性

> 在同一个线程在外层方法获取锁的时候，再进入该线程的内层方法会自动获取锁。Java中ReentrantLock和synchronized都是可重入锁，可重入锁的一个优点是**可一定程度避免死锁**。

![](https://oss.yiki.tech/oss/202212171058297.png)

### ReentrantLock和synchronized区别

![](https://oss.yiki.tech/oss/202212171101245.png)

### ReentrantReadWriteLock读写锁

> 在并发场景中用于解决线程安全的问题，我们几乎会高频率的使用到独占式锁，通常使用java提供的关键字synchronized或者concurrents包中实现了Lock接口的ReentrantLock。它们都是独占式获取锁，也就是在同一时刻只有一个线程能够获取锁。而在一些业务场景中，大部分只是读数据，写数据很少，如果仅仅是读数据的话并不会影响数据正确性（出现脏读），而如果在这种业务场景下，依然使用独占锁的话，很显然这将是出现性能瓶颈的地方。针对这种读多写少的情况，java还提供了另外一个实现Lock接口的**ReentrantReadWriteLock**(读写锁)。**读写锁允许同一时刻被多个读线程访问，但是在写线程访问时，所有的读线程和其他的写线程都会被阻塞**。

* 读写锁特点
  * 写写不可并发(类似于事务容易产生脏数据)
  * 读写不可并发
  * 读读可以并发

![](https://oss.yiki.tech/oss/202212171138304.png)

> 支持公平/非公平策略

![](https://oss.yiki.tech/oss/202212171132741.png)

* 支持可重入
  * 同一读线程在获取了读锁后还可以获取读锁
  * 同一写线程在获取了写锁之后既可以再次获取写锁又可以获取读锁
* 读写锁如果使用不当，很容易产生“饥饿”问题
  * 在读线程非常多，写线程很少的情况下，很容易导致写线程“饥饿”，虽然使用“公平”策略可以一定程度上缓解这个问题，但是“公平”策略是以牺牲系统吞吐量为代价的。
* Condition条件支持
  * 写锁可以通过`newCondition()`方法获取Condition对象。但是读锁是没法获取Condition对象，读锁调用`newCondition() `方法会直接抛出`UnsupportedOperationException`

## 线程间通信

![](https://oss.yiki.tech/xh/image-20230108115330999.png)

### 虚假唤醒

> 消费者被唤醒后是从wait()方法（被阻塞的地方）后面执行，而不是重新从同步块开头
>
> 中断和虚假唤醒是可能产生的，所以要用loop循环，if只判断一次，while是只要唤醒就要拉回来再判断一次。

![](https://oss.yiki.tech/oss/202212171214607.png)

### Condition

![image-20230108125817687](https://oss.yiki.tech/xh/image-20230108125817687.png)

![](https://oss.yiki.tech/oss/202212171228256.png)

![image-20230108125848889](https://oss.yiki.tech/xh/image-20230108125848889.png)

### 定制化线程通信

* 分析实现方式：
  * 有一个锁Lock，多把钥匙Condition
  * 有顺序通知（切换线程），需要有标识位
  * 判断标志位
  * 输出线程名 + 内容
  * 修改标识符，通知下一个

## 并发容器类

![](https://oss.yiki.tech/oss/202212171650417.png)



## JUC工具类

### CountDownLatch(计数器)

![image-20230108130615871](https://oss.yiki.tech/xh/image-20230108130615871.png)

```java
CountDownLatch 与 join 方法的区别
调用一个子线程的 join()方法后，该线程会一直被阻塞直到该线程运行完毕。而 CountDownLatch 则使用计数器允许子线程运行完毕或者运行中时候递减计数，也就是 CountDownLatch 可以在子线程运行任何时候让 await 方法返回而不一定必须等到线程结束；另外使用线程池来管理线程时候一般都是直接添加 Runnable 到线程池这时候就没有办法在调用线程的 join 方法了，countDownLatch 相比 Join 方法让我们对线程同步有更灵活的控制。
```

### CyclicBarrier 循环栅栏

![image-20230108130835129](https://oss.yiki.tech/xh/image-20230108130835129.png)

![image-20230108130915483](https://oss.yiki.tech/xh/image-20230108130915483.png)

```JAVA
CyclicBarrier和CountDownLatch的区别
CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法重置，可以使用多次，所以CyclicBarrier能够处理更为复杂的场景；CountDownLatch允许一个或多个线程等待一组事件的产生，而CyclicBarrier用于等待其他线程运行到栅栏位置
```

### Semaphore

![image-20230108131314439](https://oss.yiki.tech/xh/image-20230108131314439.png)

## 创建多线程之callable

```java
Thread类、Runnable接口使得多线程编程简单直接。

但Thread类和Runnable接口都不允许声明检查型**异常**，也不能定义**返回值**。没有返回值这点稍微有点麻烦。不能声明抛出检查型异常则更麻烦一些。

public void run()方法规范意味着你必须捕获并处理检查型异常。即使你小心捕获异常，也不能保证这个类（Runnable对象）的所有使用者都读取异常信息。

以上两个问题现在都得到了解决。从java5开始，提供了Callable接口，是Runable接口的增强版。用Call()方法作为线程的执行体，增强了之前的run()方法。因为call方法可以有返回值，也可以声明抛出异常。
```

![image-20230108132328005](https://oss.yiki.tech/xh/image-20230108132328005.png)

## 阻塞队列（BlockingQueue）

在多线程领域：所谓**阻塞**，在某些情况下会**挂起线程**（即阻塞），一旦条件满足，被挂起的线程又会自动被唤起

BlockingQueue即阻塞队列，是java.util.concurrent下的一个接口，因此不难理解，BlockingQueue是为了解决多线程中数据高效安全传输而提出的。从阻塞这个词可以看出，在某些情况下对阻塞队列的访问可能会造成阻塞。被阻塞的情况主要有如下两种：

1. **当队列满了的时候进行入队列操作**

2. **当队列空了的时候进行出队列操作**

因此，当一个线程试图对一个已经满了的队列进行入队列操作时，它将会被阻塞，除非有另一个线程做了出队列操作；同样，当一个线程试图对一个空队列进行出队列操作时，它将会被阻塞，除非有另一个线程进行了入队列操作。

![1562942021825](https://oss.yiki.tech/xh/1562942021825.png)

**为什么需要BlockingQueue**
		好处是我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockingQueue都给你一手包办了。在concurrent包发布以前，在多线程环境下，我们每个程序员都必须去自己控制这些细节，尤其还要兼顾效率和线程安全，而这会给我们的程序带来不小的复杂度。这块内容主要是了解,为线程池做铺垫

BlockingQueue接口主要有以下7个实现类：

1. <font color="red">ArrayBlockingQueue：由数组结构组成的有界阻塞队列。</font>
2. <font color="red">LinkedBlockingQueue：由链表结构组成的有界（但大小默认值为integer.MAX_VALUE）阻塞队列。</font>
3. PriorityBlockingQueue：支持优先级排序的无界阻塞队列。
4. DelayQueue：使用优先级队列实现的延迟无界阻塞队列。
5. <font color="red">SynchronousQueue：不存储元素的阻塞队列，也即单个元素的队列。</font>
6. LinkedTransferQueue：由链表组成的无界阻塞队列。
7. LinkedBlockingDeque：由链表组成的双向阻塞队列。

BlockingQueue接口有以下几个方法：

![image-20230108140322617](https://oss.yiki.tech/xh/image-20230108140322617.png)



## ThreadPool线程池

线程池的优势：线程复用；控制最大并发数；管理线程。

1. 降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的销耗。​​
2. 提高响应速度。当任务到达时，任务可以不需要等待线程创建就能立即执行。
3. 提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会销耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控。

![1562997049227](https://oss.yiki.tech/xh/1562997049227.png)

Executor接口是顶层接口，只有一个execute方法，过于简单。通常不使用它，而是使用ExecutorService接口：

![image-20230108150118477](https://oss.yiki.tech/xh/image-20230108150118477.png)

### Executors工具类

![image-20230108150226181](https://oss.yiki.tech/xh/image-20230108150226181.png)

### 底层原理

三个方法的本质都是 ThreadPoolExecutor 的实例化对象，只是具体参数值不同

三个方法都是静态方法可以直接通过类名直接获取

### 线程池 7 大核心参数

![image-20230108150617308](https://oss.yiki.tech/xh/image-20230108150617308.png)

1. corePoolSize：池中会保留的最多线程数
2. maximumPoolSize：线程池中能够容纳同时 执行的最大线程数，核心线程+救急线程的最大数
3. keepAliveTime：救急线程的生存时间，生存时间内没有新任务，此线程资源会释放
4. unit：救急线程的生存时间单位，如秒、毫秒等
5. workQueue：当没有空闲核心线程时，新来任务会加入到此队列排队，队列满会创建救急线程执行任务
6. threadFactory：表示生成线程池中工作线程的线程工厂， 用于创建线程，**一般默认的即可**
7. handler：拒绝策略，当所有线程都在繁忙，workQueue 也放满时，会触发拒绝策略

### 线程池底层工作原理

![image-20230108150658723](https://oss.yiki.tech/xh/image-20230108150658723.png)

1. 在创建了线程池后，线程池中的**线程数为零**。

2. 当调用execute()方法添加一个请求任务时，线程池会做出如下判断：

   1. 如果正在运行的线程数量**小于corePoolSize**，那么马上**创建线程**运行这个任务；
   2. 如果正在运行的线程数量**大于或等于corePoolSize**，那么**将这个任务放入队列**；
   3. 如果这个时候队列满了且正在运行的线程数量还**小于maximumPoolSize**，那么还是要**创建非核心线程**立刻运行这个任务；
   4. 如果队列满了且正在运行的线程数量**大于或等于maximumPoolSize**，那么线程池会**启动饱和拒绝策略**来执行。

3. 当一个线程完成任务时，它会从队列中取下一个任务来执行。

4. 当一个线程无事可做超过一定的时间（keepAliveTime）时，线程会判断：

   如果当前运行的线程数大于corePoolSize，那么这个线程就被停掉。

   所以线程池的所有任务完成后，**它最终会收缩到corePoolSize的大小**。

### 拒绝策略

![image-20230108150843151](https://oss.yiki.tech/xh/image-20230108150843151.png)

### 定义核心线程数

```
如何定义核心线程数?(corePoolSize)
		1.cpu密集型
			如果你所开发的项目主要是用于做科学计算或者科学计算占主导业务的项目/需求
			此时 corePoolSize = cpu核数 + 1
		2,IO密集型
			如果你所开发的项目主要是用于IO操作(主要读写业务 CRUD)
			此时 corePoolSize = 核数/(1-0.9)
```



## CAS

CAS：Compare and Swap。比较并交换。是实现并发算法时常用到的一种技术。CAS操作有3个基本参数：内存地址V，旧值A，新值B。它的作用是将指定内存地址V的内容与所给的旧值A相比，如果相等，则将其内容替换为指令中提供的新值B；如果不等，则更新失败。

**CAS是解决多线程并发安全问题的一种乐观锁算法。**当且仅当旧的预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做或者重来。这种重来重试的行为称为：自旋

Unsafe类是CAS的核心类，提供**硬件级别的原子操作**（目前所有CPU基本都支持硬件级别的CAS操作）我们一般不操作Unsafe类，而是操作封装好的原子类。

![image-20230109104100571](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109104100571.png)

### Unsafe代码示例：

![image-20230109104335828](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109104335828.png)

### 基本代码演示

![image-20230109104847989](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109104847989.png)

结果分析：

```java
第一次更新：i的值（1）和预期值（1）相同，所以执行了更新操作，把i的值更新为200
第二次更新：i的值（200）和预期值（1）不同，所以不再执行更新操作
第三次更新：i的值（200）和预期值（1）相同，所以执行了更新操作，把i的值更新为300
```

### 缺点

**开销大**：在并发量比较高的情况下，如果反复尝试更新某个变量，却又一直更新不成功，会给CPU带来较大的压力

**ABA问题**：当变量从A修改为B再修改回A时，变量值等于期望值A，但是无法判断是否修改，CAS操作在ABA修改后依然成功。 

**不能保证代码块的原子性**：CAS机制所保证的只是一个变量的原子性操作，而不能保证整个代码块的原子性。



## AQS

AbstractQueuedSynchronizer抽象队列同步器简称AQS，它是实现同步器的基础组件（框架），juc下面Lock（ReentrantLock、ReentrantReadWriteLock等）的实现以及一些并发工具类（Semaphore、CountDownLatch、CyclicBarrier等）就是通过AQS来实现的。具体用法是通过继承AQS实现其模板方法，然后将子类作为同步组件的内部类。

AQS的重要性：它是JUC包下大部分类的底层实现原理，是JUC的基石，主要用来解决锁分配给谁的问题

![image-20230109105204463](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105204463.png)

![image-20230109105153883](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105153883.png)

AQS = FIFO+state 实现

整体就是一个抽象的FIFO队列来完成资源获取线程的排队工作，并通过一个int类型变量表示持有锁的状态

### 框架结构

![image-20230109105302235](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105302235.png)

AQS真实的框架结构如下：

![image-20230109105323961](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105323961.png)

AQS维护了一个volatile语义(支持多线程下的可见性)的共享资源变量**state**和一个FIFO（first-in-first-out）**线程等待队列**(多线程竞争state资源被阻塞时，会进入此队列)。

### 基于AQS实现锁的思路

AQS将大部分的同步逻辑均已经实现好，继承的自定义同步器只需要实现state的获取(acquire)和释放(release)的逻辑代码就可以，主要包括下面方法：

- tryAcquire(int)：独占方式。尝试获取资源，成功则返回true，失败则返回false。
- tryRelease(int)：独占方式。尝试释放资源，成功则返回true，失败则返回false。
- tryAcquireShared(int)：共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
- tryReleaseShared(int)：共享方式。尝试释放资源，如果释放后允许唤醒后续等待结点返回true，否则返回false。
- isHeldExclusively()：该线程是否正在独占资源。只有用到condition才需要去实现它。

也就是说：

​		通过AQS可以实现独占锁（只有一个线程可以获取到锁，如：ReentrantLock），也可以实现共享锁（多个线程都可以获取到锁Semaphore/CountDownLatch等）

### JUC基石

![image-20230109105434852](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105434852.png)

### 基于AQS实现独占锁

![image-20230109105755387](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105755387.png)



在分析Mutex类中会涉及到lock方法的实现，其底层实现为如下

![image-20230109105844339](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105844339.png)

### ReentrantLock底层原理

![image-20230109105918349](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105918349.png)

在ReentrantLock类中包含了3个AQS的实现类：

1. 抽象类Sync
2. 非公平锁实现类NonfaireSync
3. 公平锁实现类FairSync

![image-20230109105951143](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109105951143.png)

#### Sync抽象类

##### nonfairTryAcquire

该方法是Sync类自定义的一个方法，并不是重写的AQS的tryAcquire方法

非公平的获取：其实就是不管现在等待队列的情况，我先自己尝试获取下。成功了最好，不成功就入队等待

![image-20230109110201790](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109110201790.png)

##### tryRelease

释放锁  该方法是重写AQS的tryRelease方法

1. 只有当前持有锁的线程才能做释放锁操作，不然会抛IllegalMonitorStateException
2. 由于锁是可重入的，释放锁其实就是state - 1， 如果最终state == 0,把获取锁的线程设置为空。
3. 如果锁空闲，返回true, 否则返回false

![0212 非公平锁的释放](G:\上课视频\JUC课件\0718视频_下午\视频_下午\图片\0212 非公平锁的释放.png)

#### NonfairSync

该类为同步对象的非公平锁

##### TryAcquire

该方法是重写AQS类中的tryAcquire方法，实际上调用的是Sync类中的nonfairTryAcquire方法

![image-20230109110646233](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109110646233.png)

##### lock

1. 不公平的lock, 进lock方法，啥都不判断，直接尝试通过cas修改锁状态。
2. 如果修改成功，说明获取到了锁，设置当前获取锁的线程为自己，然后返回
3. 如果修改失败，通过acquire方法按部就班的获取锁或入队列等待。

![image-20230109110715493](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109110715493.png)

#### FairSync

同步对象的公平锁

##### tryAcquire

和非公平的版本相比，唯一的区别就是这里多做了一个判断 **!hasQueuedPredecessors()**

![image-20230109110810994](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20230109110810994.png)