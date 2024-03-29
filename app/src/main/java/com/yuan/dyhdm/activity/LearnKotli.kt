package com.yuan.dyhdm.activity

import com.yuan.dyhdm.entity.Cellphone
import java.util.*
import kotlin.math.max


/**
 *created by liangxuedong on 2021/5/31
 *
 */
fun main() {

    /**
     *
     * 记录kotlin各种语法糖
     * 1、当一个函数只有一行代码的时候，不必编写函数体，可以将唯一的一行代码写在函数尾部，中间用等号连接即可
     * fun maxNumber(param1: Int, param2: Int): Int = max(param1, param2)
     * 2、类型推导机制
     * fun maxNumber(param1: Int, param2: Int) = max(param1, param2)
     *
     * 3、 if语句 带返回值  返回值就是if语句每一个条件中最后一行代码的返回值
     *
     *
     */

    val b = 15
    var a = 10
    a = 18
    var c: Int = 16
    var d: Long = 16L
    var dd: Short = 16
    var ddd: Double = 14.33
    var de: Float = 16.44F
    var df: Boolean = false
    var dg: Char = 'a'
    var dh: Byte = 119

    a = a * 10

    val inevale=100
    val intvaldd=100.00
    val da=inevale+intvaldd

    println(da)
    val cellphone1 = Cellphone("dd", 19.0)
    val cellphone2 = Cellphone("dd", 15.0)
    val cellphone3 = cellphone1 + cellphone2
    println(cellphone3.price)
    cellphone1.main()

//    println("first code  " + a)
//    println("methodname  " + methodname(10, 18))
//    println("largeNunber" + largeNunber(18, 20))
//    chechNumber(a)
//    forMehtod()
    visitArrayValue()
}

fun methodname(param1: Int, param2: Int): Int {


    return max(param1, param2)
}

fun largeNumber1(param1: Int,param2: Int):Int{
    return max(param1,param2)
}

//语法糖简化
fun maxNumber(param1: Int, param2: Int) = max(param1, param2)

//条件语句 if 和java相同
//不同 可有返回值
fun largeNunber(param2: Int, param1: Int): Int {
    var value = if (param1 > param2) {
        param1

    } else {
        param2
    }
    return value
}

fun largeNunber2(param2: Int, param1: Int): Int {
    return if (param1 > param2) {
        param1

    } else {
        param2
    }
}

fun largeNunber3(param2: Int, param1: Int): Int =
    if (param1 > param2) {
        param1

    } else {
        param2

    }

fun largeNunber4(param2: Int, param1: Int): Int =
    if (param1 > param2)
        param1
    else
        param2
fun lar(param2: Int, param1: Int): Int =if (param1>param1)param1 else param2

//when语句 switch  强于switch
//when 有返回值 可以单行语法糖

fun getScore(name: String) = when (name) {


    "Tom" -> 86
    "jii" -> 99
    "jii2" -> 99
    "jjjs" -> {
        88
    }
    else -> 9
}
//is 关键字   类似java instanceof

fun chechNumber(number: Number) {
    when (number) {
        is Long -> {
            println("number is LOng" + number)
        }

        is Int -> {
            println("number is Int" + number)
        }

        is Double -> {
            println("number is Double" + number)
        }
        else ->
            println("not suppore type" + number)

    }
}

//when 不带参数
fun getScore2(name: String) = when {

    name.startsWith("Tom") -> 44
    name == "Tom" -> 86
    name == "jii" -> 99
    name == "jii2" -> 99
    name == "jjjs" -> {
        88
    }
    else -> 9
}

//循环语句 while 和java一样
// for循环特殊    区间
//for in 循环
//0..10 [0，10] 双端闭合区间   downto 降序 10 dawnTo 1 [10,1]
// 左闭右开区间 0 until 10  [0,10) 升序
//step


val range = 0..10
val rang2 = 0 until 11
fun forMehtod() {
    for (i in 0 until 17 step 2) {
        println(i)
    }
}




/*
访问数组元素
 */
fun visitArrayValue() {
    var arr1 = arrayOf("kotlin", "java", "python")
    //使用两种方式获取数组元素
    println(arr1[0])

    println(arr1.get(0))
    println(arr1.get(0))
    //使用两种方式修改数组元素
    arr1[1] = "noJava"
    arr1.set(1,"nojava")
    for (datas in arr1){
        println(datas)
    }

    for (i in arr1.indices) {
        println(arr1[i])
    }
    for(i in 0..10){
        println(i)
    }

    println(Arrays.toString(arr1))
}







