package com.yuan.dyhdm.entity


/**
 *created by liangxuedong on 2021/5/31
 *
 */
class Student(val sno:String="123", val grade:Int=0,   name:String, age:Int) :Person(name, age),Study {
    override fun read() {
        TODO("Not yet implemented")

    }

    override fun doHomework() {
        super.doHomework()
    }


    init {

    }

    fun StringBuilder.otherApply(block: StringBuilder.()  -> Unit): StringBuilder {

        block()
        return this
    }

    val stringBuilder = StringBuilder()
    val result = stringBuilder.otherApply {

        this.append("hello")
        this.append("123")
    }


    public inline fun <T> T.extFunc(block: () -> Unit): T {
        block()
        return this
    }

    }



