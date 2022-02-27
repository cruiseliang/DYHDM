package com.yuan.dyhdm.entity


/**
 *created by liangxuedong on 2021/5/31
 *
 */
class Student(val sno:String="123", val grade:Int=0) :Person(),Study {
    override fun read() {
        TODO("Not yet implemented")
    }

    override fun doHomework() {
        TODO("Not yet implemented")
    }
    init {

    }


}