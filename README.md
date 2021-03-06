# java-notes
相关书籍：《On Java 8》


### 其他的知识点
### C

### C++
1. 静态加载语言

### Java
1. 动态加载语言

1. Java 没有 C 的条件编译（conditional compilation）功能，该功能使你不必更改任何程序代码而能够切换开关产生不同的行为。Java 之所以去掉此功能，可能是因为 C 在绝大多数情况下使用该功能解决跨平台问题：程序代码的不同部分要根据不同的平台来编译。而 Java 自身就是跨平台设计的，这个功能就没有必要了。

封装、复用 --> 多态（动态加载） <-- 类型信息



### other
1. 字符串只能拼接另一个字符串，所以 “+”、“+=” 在遇到引用时，会自动调用 toString() 将其转换成一个字符串



### 类的加载和初始化专题
* 第 6 章 初始化和清理
* 第 8 章 复用
* 第 9 章 多态
* 第 19 章 类型信息
#### 类初始化
19章 类型信息（Class 对象）;

#### 初始化引用
> 8章 复用（组合语法）：初始化成员变量

编译器不会为每个引用创建一个默认对象，这是有意义的，因为在许多情况下，这会导致不必要的开销。初始化引用有四种方法:
1. 当对象被定义时。这意味着它们总是在调用构造函数之前初始化。
2. 在该类的构造函数中。
3. 在实际使用对象之前。这通常称为延迟初始化。在对象创建开销大且不需要每次都创建对象的情况下，它可以减少开销。
4. 使用实例初始化。


> 初始化基类

必须正确初始化基类子对象，而且只有一种方法可以保证这一点: 通过调用基类构造函数在构造函数中执行初始化，并且该构造函数具有执行基类初始化所需的所有适当信息和特权。（如果没有显式调用基类构造函数，Java 会自动在派生类构造函数中隐式调用基类构造函数）

编译器其实很笨，只会自动寻找、调用不带参数的构造器。如果该类中没有不带参数的构造器，则需要使用 super 指定使用的是基类（父类）的哪个构造器，否则编译器就会报错！


> 类初始化和加载

详细信息查看 【第 8 章：part_09_类初始化和加载】

> 第 9 章：构造器和多态

构造器调用顺序、构造器中使用多态（构造器中使用多态是不好的编程习惯，除非必须要这样做）等，详情看【第 9 章：part_03_构造器和多态】




# 版本更新
Java SE5
1. 引入 StringBuilder（在这之前用的是 StringBuffer）
2. 引入 format() 方法和 C 语言中 printf() 风格的格式化输出功能（其实是把 format 包装了一下）
3. 新增了 Scanner 类





#### 元素初始化
> 明确两点：元素在什么位置下会被初始化，会被初始化为何值？

数组、变量





### 反编译查看 .class 对应的代码、字节码
JDK 自带的 `javap` 工具：（在要进行反编译的 .class 文件目录下执行）
```shell script
> javap -cp main Explore.class
Compiled from "values方法的神秘之处.java"
final class Explore extends java.lang.Enum<Explore> {
  public static final Explore HERE;
  public static final Explore THERE;
  public static Explore[] values();
  public static Explore valueOf(java.lang.String);
  static {};
}

> javap -p Explore.class
Compiled from "values方法的神秘之处.java"
final class Explore extends java.lang.Enum<Explore> {
  public static final Explore HERE;
  public static final Explore THERE;
  private static final Explore[] $VALUES;
  public static Explore[] values();
  public static Explore valueOf(java.lang.String);
  private Explore();
  static {};
}

> javap -c Explore.class
Compiled from "values方法的神秘之处.java"
final class Explore extends java.lang.Enum<Explore> {
  public static final Explore HERE;

  public static final Explore THERE;

  public static Explore[] values();
    Code:
       0: getstatic     #1                  // Field $VALUES:[LExplore;
       3: invokevirtual #2                  // Method "[LExplore;".clone:()Ljava/lang/Object;
       6: checkcast     #3                  // class "[LExplore;"
       9: areturn

  public static Explore valueOf(java.lang.String);
    Code:
       0: ldc           #4                  // class Explore
       2: aload_0
       3: invokestatic  #5                  // Method java/lang/Enum.valueOf:(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;
       6: checkcast     #4                  // class Explore
       9: areturn

  static {};
    Code:
       0: new           #4                  // class Explore
       3: dup
       4: ldc           #7                  // String HERE
       6: iconst_0
       7: invokespecial #8                  // Method "<init>":(Ljava/lang/String;I)V
      10: putstatic     #9                  // Field HERE:LExplore;
      13: new           #4                  // class Explore
      16: dup
      17: ldc           #10                 // String THERE
      19: iconst_1
      20: invokespecial #8                  // Method "<init>":(Ljava/lang/String;I)V
      23: putstatic     #11                 // Field THERE:LExplore;
      26: iconst_2
      27: anewarray     #4                  // class Explore
      30: dup
      31: iconst_0
      32: getstatic     #9                  // Field HERE:LExplore;
      35: aastore
      36: dup
      37: iconst_1
      38: getstatic     #11                 // Field THERE:LExplore;
      41: aastore
      42: putstatic     #1                  // Field $VALUES:[LExplore;
      45: return
}
```

