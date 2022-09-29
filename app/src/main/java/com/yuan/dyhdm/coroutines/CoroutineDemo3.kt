package com.yuan.dyhdm.coroutines

import kotlinx.coroutines.*


/**
 *created by liangxuedong on 2022/9/29
 *
 */

fun main() = runBlocking { // this: CoroutineScope


    coroutineScope { // 创建一个协程作用域
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }


    launch {
        delay(200L)
        println("Task from runBlocking")
    }
    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}