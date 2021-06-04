package com.yuan.dyhdm.service

import android.app.Service
import android.content.Intent
import android.os.IBinder


/**
 *created by liangxuedong on 2021/6/3
 *
 */
class MyService :Service (){
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")

    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}