package com.yuan.dyhdm.activity

import com.yuan.dyhdm.entity.Cellphone
import com.yuan.dyhdm.entity.Person
import kotlin.math.max


/**
 *created by liangxuedong on 2021/5/31
 *
 */
fun main() {

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
    var dh: Byte = 111

    a = a * 10


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
}

fun methodname(param1: Int, param2: Int): Int {
    return max(param1, param2)
}

fun maxNumber(param1: Int, param2: Int): Int = max(param1, param2)

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

//循环语句 while for  区间
val range = 0..10
val rang2 = 0 until 11
fun forMehtod() {
    for (i in 0 until 17 step 2) {
        println(i)
    }
}

















