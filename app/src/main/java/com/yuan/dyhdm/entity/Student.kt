package com.yuan.dyhdm.entity


/**
 *created by liangxuedong on 2021/5/31
 *
 */
class Student(val sno:String, val grade:Int) :Person(),Study {
    override fun read() {
        TODO("Not yet implemented")
    }

    override fun doHomework() {
        TODO("Not yet implemented")
    }


}