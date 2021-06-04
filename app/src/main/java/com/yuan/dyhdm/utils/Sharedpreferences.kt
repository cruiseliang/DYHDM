package com.yuan.dyhdm.utils

import android.content.SharedPreferences


/**
 *created by liangxuedong on 2021/6/2
 *
 */
fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.block()
    editor.apply()

}