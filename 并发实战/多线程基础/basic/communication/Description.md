当线程在系统内运行时，线程的调度具有一定的透明性，程序通常无法准确控制线程的轮换执行，但 Java 也提供了一些机制来保证线程协调运行。

### 线程通讯信
在传统的线程通讯中（Java 5 之前），可由同步监视器使用 Object 提供的 wait、notify、notifyAll 方法来实现线程之间的通信。
因线程间的通讯操作必须由同步监视器对象来调用，Object 的方法可分为以下两种情况：
- 使用 synchronized 修饰的同步方法，其同步监视器是 this，所以可以在同步方法中直接使用 Object 的相关方法。
- 使用 synchronized 修饰的同步代码块，需要显式指定同步监视器，所以 Object 的相关方法必须由同步监视器对象来调用。
> 详情：Traditional.java
Notice：同步监视器可以是任何对象，如练习中的 Test2.java

在 Java 1.5 之后，如果直接使用 Lock 对象来保证同步，则系统中*不存在隐式的同步监视器*（也就不能使用 wait、notifyAll 操作进行线程通信了）。
当使用 Lock 对象来保证同步时，Java 提供了一个 Condition 类来保持协调。在这种情况下，Lock 替代了同步方法或同步代码块，Condition 替代了同步监视器的功能。

> 详情：LockWithCondition.java


