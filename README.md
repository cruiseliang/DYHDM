MultiItemTypeAdapter hongyang adapter

https://blog.csdn.net/lmj623565791/article/details/51118836/

 参考：https://blog.csdn.net/lmj623565791/article/details/38902805/

 kotlin 关键字记录

 val(value) 声明一个不可变变量  
 var(variable)声明一个可变的变量  
 对象数据类型 Int Long Short Float Double Boolean Char Byte  

判断语句
 if  条件语句： if 和java基本相同，不同的是 可有返回值
 when   when语句 switch  强于switch；when 有返回值 可以单行语法糖
 循环语句
  while  和java一样
  for  for in 循环
for-in 循环可以自动遍历数组的每个元素
  区间
 0 ..10 [0，10] 双端闭合区间   downto 降序 10 dawnTo 1 [10,1]
 左闭右开区间 0 until 10  [0,10)
   ..  until in downto
关键字 step  跳过几 用于循环
判断对象或者字符串是否相等使用==
抽象类可以被继承 非抽象类  添加 open 关键字进行继承 
 
主构造函数   逻辑部分在init{}方法中写
次构造函数 constructor

继承 ： 实现 ，
接口中数允许定义的函进行默认实现
修饰符 public  private  protected internal(统一模块可用)  
data class 声明bean类
object 单例

listof()  不可变的数据list 只可以读取 不可以删除 添加 修改
mutablelistof()    可以删除 添加 修改
setof() 
mutablesetof()  
mapof() 
mutablemapof()


lambda编程
定义：一小段可以作为参数传递的代码
语法结构： {参数名1：参数类型,参数名2：参数类型 ->函数体}  函数体的最后一行作为表达式返回值
函数 filter 过滤
any(集合中存在一个满足条件) 
all （集合中 全部满足条件）
 
java 函数式API：kotlin 调用java方法，且该方法接收一个java单抽象方法接口（接口中只有一个待实现方法）参数
 匿名内部类 object 关键字

空指针检查机制  操作符 ？ 可以为空 
判空辅助工具 ?.  不为空 可执行后续方法
?:   左右两侧表达式  左侧不为空返回左侧 否则返回右侧
!! 非空断言 强制非空判断 可以通过编译 


kotlin标准函数 standard.kt文件中的函数  
let 辅助判空 obj?.let{
obj2->具体操作
}
${} 字符串内嵌表达式 ，表达式中仅有一个变量的时候大括号可以省略
函数的参数默认值 fun pri(par:Int=9)

标准函数  同一对象重复调用简化
with 接收2个参数 第一个 任意类型对象 第二个Lambda表达式  并将第一个的上下文传递给Lambda
run 函数 只接收一个lambda参数 
apply  类似run  只接收一个lambda参数  无法指定返回值 返回调用对象本身



静态方法 java  static 
kotlin软化了静态方法 1、使用单例类进行替换 object 关键字 单例类方法都可以直接调用
                   2、不适用单例类   companinon object 定义方法 可以直接调用
class Util{
companinon object{
fun doaction(){
}}
}

Util.doaction()
3、kotlin实现静态方法   类似java 静态方法  添加注解@jvmStatic 真正的静态方法 注解只能添加在单例类或者 companinon object
关键字中  
创建FIle 使用顶层方法  都是静态

关键字 lateinit(变量延迟初始化) 
判断变量是否初始化 :: 对象.isInitialized
密封类关键字 sealed class 减少when 选择语句时候 else

 扩展函数  :在不修改某个类的源码的情况下，仍然可以打开这个类，想该类添加新的函数；pg:String 增加dp2px方法
 运算符重载 关键字 operator

kotlin高级用法：
高阶函数 ：如果一个函数接收另一个函数作为参数，或者返回值的类型是另一个函数，那么该函数就称为高阶函数
函数类型表达式 （String,Int） ->Unit
高阶函数引用使用 ::plus
Cellphone.kt
对应java 的实现方法是 匿名内部类 每次使用创建类 增加内存和性能开销
内联函数 关键字 inline 去除开销  非内联函数 noinline 
内联函数使用return进行返回  非内联进行局部返回




泛型和委托
泛型 只要是泛型类 或者泛型方法 kotlin 自动推到类型  可以对泛型进行限制 
Any? 泛型可以为空  Any 泛型不可以为空

委托： 类委托 一个类的实现交给另外一个类  by 关键字 简化代码
      委托属性  一个属性的实现交给另外一个类 关键字 operator

infix函数构建更可读的语法
infix 不能定义为顶层函数  只能接受一个参数 
infix 语法糖允许 小数点 括号等语法去除
mapof(A to B ) to 是infix函数 构建键值对

协程 高效并发处理

1、GlobalScope.launch函数 创建 协程作用域   顶层协程 管理不方便 线程运行结束 来不及执行
GlobalScope.launch {
//        print("codes run in scope")
delay(500)
}

2、 runBlocking函数 创建 协程作用域  测试用 挂起协程和线程 实际使用影响性能 不推荐使用
runBlocking {
println("codes run in scope")
delay(500)
println("codes run in scope finished")
}

3、launch函数 创建多个协程  在协程作用域下调用，创建子协程 主协程结束立马结束

runBlocking {

launch {
delay(100)
}

launch {
            delay(100)
        }
    }
4、suspend 关键字 将任意函数声明为挂起函数 挂起函数之间可以互相调用 不提供作用域
5、coroutineScope函数 是挂起函数    继承外部协程作用域，创建子协程 提供协程作用域
阻塞当前协程 不影响其他协程和线程

suspend fun printDot2()= coroutineScope {
//launch    创建 协程作用域
launch {
println("c")
}
}

6、async函数 创建子协程 返回Deferred 对象  await()方法返回执行结果  await()方法阻塞当前协程
runBlocking {
//async 创建子协程 返回Deferred 对象  await()方法返回执行结果
val result=async {
5+5
}.await()
println(result)
}
7、withContext()函数 接收一个线程参数    
Dispatchers.Default 计算密集型任务
Dispatchers.IO  使用一种高并发线程策略 网络请求
Dispatchers.Main 在android主线程调用

立马执行代码块 阻塞当前协程 最后一行代码做为返回值 类似async的简化版

8、协程简化回调 
suspendCoroutine函数 在协程作用域或者挂起函数中使用 接收lambda表达式 领了
suspend(暂停 挂起)Coroutine（协程）


DSL

android 中可以优化
kotlin-android-extensions 自动findviewbyid
在adapter也可以使用 view.id
使用apply函数简化 intent传承
使用java函数式API 简化 thread  onclick 方法
java中调用kotlin静态方法需要使用注解 @jvmstatic 或者顶层方法

const 常量关键字  只有在单例类或者 companinon object 或者顶层方法使用
判断全局变量是否初始化 if(::msg.isInitialized){

使用泛型实例化优化startactivity  418页
                    }

kotlin各种语法糖
1、当一个函数只有一行代码的时候，不必编写函数体，可以将唯一的一行代码写在函数尾部，中间用等号连接即可
fun maxNumber(param1: Int, param2: Int): Int = max(param1, param2)
2、类型推导机制
fun maxNumber(param1: Int, param2: Int) = max(param1, param2)
3、 if语句 带返回值  返回值就是if语句每一个条件中最后一行代码的返回值
