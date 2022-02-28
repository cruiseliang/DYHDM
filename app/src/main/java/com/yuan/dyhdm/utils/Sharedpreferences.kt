package com.yuan.dyhdm.utils

import android.content.SharedPreferences


/**
 *created by liangxuedong on 2021/6/2
 *
 */
fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
    //这是一个带接受者的函数 SharedPreferences.Editor.() -> Unit  我们需要向函数中传递一个SharedPreferences.Editor类型的
    //实例 ，在函数中可以内部调用改实例的成员，该接收者可以通过this进行访问
    val editor = edit()
    editor.block()//将editor 对象传递到 block函数中
    editor.apply()

}