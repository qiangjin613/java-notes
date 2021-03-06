/*
每个类的编译代码都存在与自己独立的文件中。
该文件只有在使用程序代码时才会被加载。
 *
一般可以说“类的代码在首次使用时加载“。
通常是指创建类的第一个对象，或者是访问了类的 static 属性或方法。
（构造器也是一个 static 方法尽管它的 static 关键字是隐式的）
因此，准确地说，一个类当它任意一个 static 成员被访问时，就会被加载。
 *
首次使用时就是 static 初始化发生时。
所有的 static 对象和 static 代码块在加载时按照文本顺序依次初始化。
static 变量只被初始化一次。
 */

class Insect {

    {
        // ”非法的前向引用“
        // System.out.println(i);
        // System.out.println(j);
        // System.out.println(K);
        System.out.println("Insect 的第 1 个初始化块");
    }

    private int i = f();
    protected int j;
    // 编译时常量
    private final int K = 47;

    Insect() {
        System.out.println("i = " + i + ", j = " + j + ", K = " + K);
        j = 39;
    }

    {
        System.out.println("Insect 的第 2 个初始化块");
    }

    int f() {
        System.out.println("InsectObj.f() 调用...");
        return 9;
    }


    static {
        // 对于 static 数据，也会出现”非法的前向引用“现象
        // System.out.println(statici);
        // System.out.println(x1);

        System.out.println("Insect 的第 1 个 static 块执行");
    }

    // 当运行到这里时，发现没有进行初始化，编译器为 statici 进行隐式赋值
    private static int statici;
    private static int x1 = printInit("static Insect.x1 initialized");

    static {
        System.out.println(statici);
        System.out.println("Insect 的第 2 个 static 块执行");
    }

    static void method() {
        int i;
        // 编译异常
        // 不可以这样做是因为 i 没有被初始化过！在 Java 中，不能调用没有被初始化过的值。
        // int j = i + 1;
        System.out.println("Insect 中未被使用的 static 方法");
    }

    static int printInit(String s) {
        System.out.println(s);
        return 47;
    }
}

class Beetle extends Insect {
    private int k = printInit("Beetle.k.initialized");

    public Beetle() {
        System.out.println("k = " + k);
        System.out.println("j = " + j);
    }

    private static int x2 = printInit("static Beetle.x2 initialized");

    static void metnod() {
        System.out.println("Beetle 中未被使用的 static 方法");
    }

    public static void main(String[] args) {
        /*
        调用 Beetle 类的 main 方法，发现 Beetle extend Insect，
        于是先初始化基类的 static 数据。
        （由于并未创建对象，所以这时并未执行二者的构造器）
        [output]
        Insect 的第 1 个 static 块执行
        static Insect.x1 initialized
        123
        Insect 的第 2 个 static 块执行
        static Beetle.x2 initialized
        由此可知，static 数据的加载是按照文本顺序先基类后派生类进行加载的。
         */

        System.out.println("\nBeetle main() 开始执行\n");
        new Beetle();
        /*
        [output]
        Insect 的第 1 个初始化块
        InsectObj.f() 调用...
        Insect 的第 2 个初始化块
        i = 9, j = 0, K = 47
        Beetle.k.initialized
        k = 47
        j = 39
        由此可知，对象数据的加载顺序：
        是先按照文本顺序加载成员变量、（实例）初始化块，
        再加载构造器。
         */
    }
}

/*
对上述例子的运行分析：
执行 Beetle.main() 之前需要做的工作：
1. 准备调用 Beetle.main() 静态方法，就要初始化 Beetle 类的 static 成员
2. 这时，发现 Beetle 还有父类，所以初始化父类 Insect 中的静态变量
3. 在初始化父类静态成员 Insect.x1 的时候调用了静态方法 printInit()
4. 将父类的 static 类成员按照文本顺序初始化完成后，初始化子类 Beetle 的 static 类成员
5. 对父类、子类中所有的类变量初始化完成时，类初始化暂告一段落
开始执行 Beetle.main() 方法：
1. 当使用构造器创建 Beetle 对象时（先按照上面的类初始化 Bettle 类，发现已经被初始化过了，就跳过这个环节，因为只被初始化一次）
2. 调用 Beetle 的构造器，这个时候发现隐式的 super()，随之先调用父类 Insect 的构造器
3. 在调用 Insect 的构造器之前，返现 Insect 有成员变量，所以队成员变量进行初始化（没有值的时候，基本类型是 0，引用类型是 null）
4. 父类构造器调用完毕后（使用构造器的目的之一便是为了该对象的成员变量赋值），
5. 就要执行 Beetle 构造器（在这之前也是要对 Beetle 对象的成员变量进行默认初始化），后再执行 super() 后面的代码
6. 构造器执行完毕后，类的初始化和加载告一段落。


初始化顺序的总结：
1. 先是类相关的数据，然后是对象相关的数据
2. 有父类的时候要先处理父类
3. 对于类/对象数据，会有默认初始化这一环节（如果没有在定义时赋初值，就会进行隐式赋值）
 */
