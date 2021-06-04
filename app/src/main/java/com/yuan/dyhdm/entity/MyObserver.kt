package com.yuan.dyhdm.entity

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 *created by liangxuedong on 2021/6/4
 *
 */
class MyObserver :LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart(){
        Log.e("MyObserver","activityStart")

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activitySop(){
        Log.e("MyObserver","activitySop")
    }
}