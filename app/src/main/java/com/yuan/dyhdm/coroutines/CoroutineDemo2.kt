package com.yuan.dyhdm.coroutines


import android.util.Log
import androidx.core.util.Consumer
import io.reactivex.Observable
import io.reactivex.Observable.just
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception



/**
 *created by liangxuedong on 2022/9/29
 *
 */

fun main() {
    val TAG="textrxjava"
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("World!") // 在延迟后打印输出
    }

    Thread.sleep(500L) // 阻塞主线程 2 秒钟来保证 JVM 存活
    println("Hello,") // 协程已在等待时主线程还在继续

    runBlocking {     // 但是这个表达式阻塞了主线程
        delay(2000L)  // ……我们延迟 2 秒来保证 JVM 的存活
    }

    runBlocking { // this: CoroutineScope
        launch { // 在 runBlocking 作用域中启动一个新协程
            delay(1000L)
            println("World!")
        }
    }

    just("hellodddd").subscribe(object : io.reactivex.functions.Consumer<String> {
        // 每次接收到Observable的事件都会调用Consumer.accept（）

        override fun accept(t: String) {
            println(t)
        }
    })


// 主要在观察者 Observer中 实现

// 主要在观察者 Observer中 实现
    val observer: Observer<Int?> = object : Observer<Int?> {
        // 1\. 定义Disposable类变量
        private var mDisposable: Disposable? = null
        override   fun onSubscribe(d: Disposable?) {
            Log.d(TAG, "开始采用subscribe连接")
            // 2\. 对Disposable类变量赋值
            mDisposable = d
        }

        override fun onNext(value: Int?) {
            Log.d(TAG, "对Next事件" + value + "作出响应")
            if (value == 2) {
                // 设置在接收到第二个事件后切断观察者和被观察者的连接
                mDisposable?.dispose()
                Log.d(TAG, "已经切断了连接：" + mDisposable?.isDisposed())
            }
        }

        override  fun onError(e: Throwable?) {
            Log.d(TAG, "对Error事件作出响应")
        }

        override fun onComplete() {
            Log.d(TAG, "对Complete事件作出响应")
        }


    }

}

