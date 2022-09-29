package com.yuan.dyhdm.coroutines

import kotlinx.coroutines.*


/**
 *created by liangxuedong on 2022/9/29
 *
 */

fun main(){
    val job=Job()
    val scope= CoroutineScope(job)
    scope.launch {
        println("ddddd")
    }
    job.cancel()

    runBlocking {
        val result= withContext(Dispatchers.Default){
            5+5
        }
        println(result)
    }


}