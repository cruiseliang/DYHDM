package com.yuan.dyhdm.entity

import kotlinx.coroutines.*


/**
 *created by liangxuedong on 2021/6/4
 *
 */
fun main(){
    //创建 协程作用域   顶层协程 管理不方便 线程运行结束 来不及执行
    GlobalScope.launch {
//        print("codes run in scope")
        delay(500)
    }
//    Thread.sleep(1000)

    // 创建 协程作用域  测试用 挂起线程 实际使用影响性能
    runBlocking {
        println("codes run in scope")
        delay(500)
        println("codes run in scope finished")
        launch {
            delay(100)
        }

        launch {
            delay(100)
        }
    }
    //  suspend 挂起函数 不创建子协程
    suspend fun printDot(){
        println("codes >")
    }
    // coroutineScope 挂起函数    创建子协程 提供协程作用域
    suspend fun printDot2()= coroutineScope {
        //launch    创建 协程作用域
        launch {
            println("c")
        }
    }

    //开发常用写法
    val job= Job()
    val scope= CoroutineScope(job)
    scope.launch {
        //method
    }
    job.cancel()

    runBlocking {
        //async 创建子协程 返回Deferred 对象  await()方法返回执行结果
        val result=async {
            5+5
        }.await()
        println(result)
    }

    runBlocking {
        val result2= withContext(Dispatchers.Default){
            6-9
        }
        println(result2)
    }

}