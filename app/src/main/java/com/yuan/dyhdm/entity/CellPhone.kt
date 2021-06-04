package com.yuan.dyhdm.entity

import com.yuan.dyhdm.utils.beginswith


/**
 *created by liangxuedong on 2021/5/31
 *
 */
data class Cellphone(val brand: String, val price: Double) {
    //运算符重载plus方法
    operator fun plus(cellphone: Cellphone): Cellphone {
        val sum = price + cellphone.price
        return Cellphone(brand, sum)

    }

    //高阶函数  func: (String, Int) -> Unit)  底层实现 java  匿名内部类
    fun example(func: (String, Int) -> Unit) {
        func("hello", 99)
    }

    fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
        var result = operation(num1, num2)
        return result

    }

    fun plus(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    fun minus(num1: Int, num2: Int): Int {
        return num1 - num2
    }

    //内联函数 高阶函数前 添加关键字 inline
    inline  fun num1AndNum3(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
        var result = operation(num1, num2)
        return result

    }
    //oninline 不进行内联  crossline
    //infix函数
    fun infixMethod(){
        getGenType<String>()
        if("hello".startsWith("d")){

        }
        if ("jell" beginswith ""){

        }


    }
    //泛型实化 避免在编译过程中类型擦除  使用内联函数进行代码替换
    inline fun <reified T>getGenType()=T::class.java

    //协程









    fun main() {
        val num1 = 10
        val num2 = 19
        val result1 = num1AndNum2(num1, num2, ::plus)

        val result2 = num1AndNum2(num1, num2, ::minus)
        val result3 = num1AndNum2(num1, num2) { n1, n2 ->
            n1 + n2
        }

        println(result1)
        println(result2)
        println(result3)
    }


}