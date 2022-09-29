package com.yuan.dyhdm.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView


/**
 *created by liangxuedong on 2022/9/27
 *
 */

// 测试
fun main(args: Array<String>) {
    val mime = RoundImageView()

    println("str = ${mime.str1}")
    mime.str1 = ""
    println("str = ${mime.str1}")
    mime.str1 = "kotlin"
    println("str = ${mime.str1}")

    val test: Int.(String) -> String = { param ->
        "$this param=$param"
    }
    println(1.test("2"))
    println(test(1, "2"))

    data class User(val name: String)



        val user = User("云天明")
        val letResult = user.let {
            "let 输出点东西 ${it.name}"
        }
        println(letResult)
        val runResult = user.run {
            "run 输出点东西 ${this.name}"
        }
        println(runResult)

        user.also {
            println("also ${it.name}")
        }.apply { //this:User
            println("apply ${this.name}")
        }

        repeat(5) {
            println(user.name)
        }

        val withResult = with(user) { //this: User
            println("with ${this.name}")
            "with 输出点东西 ${this.name}"
        }
        println(withResult)

    val items = listOf(1, 2, 3, 4, 5)

// Lambdas 表达式是花括号括起来的代码块。
    items.fold(0, {
        // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
            acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        // lambda 表达式中的最后一个表达式是返回值：
        result
    })

// lambda 表达式的参数类型是可选的，如果能够推断出来的话：
    val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// 函数引用也可以用于高阶函数调用：
    val product = items.fold(1, Int::times)




}

fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator


    fun(s1: Int, s2: Int): Int {
        return 1
    }
}

class RoundImageView  {


    var str1 = "test"
       get() = field   // 这句可以省略，kotlin默认实现的方式
        set(value){
            field = if (value.isNotEmpty()) value else "null"
        }
}

