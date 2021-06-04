package com.yuan.dyhdm

import android.app.Application
import android.content.Context


/**
 *created by liangxuedong on 2021/6/3
 *
 */
class MyApplication : Application() {

    lateinit var mContext: Context

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }



        fun getSelf(): Context {
            return  mContext
        }



}