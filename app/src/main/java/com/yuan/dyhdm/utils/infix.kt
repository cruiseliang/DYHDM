package com.yuan.dyhdm.utils

import android.content.Context
import android.content.Intent


/**
 *created by liangxuedong on 2021/6/3
 *
 */
infix fun String.beginswith(prefix:String)=startsWith(prefix)

inline fun <reified T>startActivity2(context: Context,block:Intent.()->Unit){
    val intent=Intent(context,T::class.java)
    intent.block()
    context.startActivity(intent)
}