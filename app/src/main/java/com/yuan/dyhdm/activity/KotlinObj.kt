package com.yuan.dyhdm.activity

import com.yuan.dyhdm.entity.Cellphone
import com.yuan.dyhdm.entity.Student


/**
 *created by liangxuedong on 2021/5/31
 *
 */
fun main(){

    val  stu=Student("122",10,"tom",10)

    val cellphone=Cellphone("123",189.99)

    //实体类data 关键字
    println(cellphone.toString())
    //单例 object 关键字
//    Singleton.singletonTest()



    infix fun Int.shlll(x: Int): Int {
        return x+this
    }
    println(1.shlll(5))
    print(1 shlll 5)

    val stringPlus: (String, String) -> String = String::plus
    val intPlus: Int.(Int) -> Int = Int::plus

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3)) // 类扩展调用

    "ddd".let {
        println(it)
    }
    repeat(5){index->
        print(index)
    }


}
