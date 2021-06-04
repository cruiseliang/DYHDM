package com.yuan.dyhdm.activity

import com.yuan.dyhdm.entity.Cellphone
import com.yuan.dyhdm.entity.Person
import com.yuan.dyhdm.entity.Singleton
import com.yuan.dyhdm.entity.Student


/**
 *created by liangxuedong on 2021/5/31
 *
 */
fun main(){
    val person= Person()
    person.age=19
    person.eat()
    val  stu=Student("122",10)

    val cellphone=Cellphone("123",189.99)
    //实体类data 关键字
    println(cellphone.toString())
    //单例 object 关键字
    Singleton.singletonTest()
}