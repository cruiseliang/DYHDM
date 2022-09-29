package com.yuan.dyhdm.utils


/**
 *created by liangxuedong on 2022/9/23
 *
 */
class ArrayUtils {

    var arr4= arrayOfNulls<String>(10)
    fun arraysCreate(){
        //创建包含指定元素的数组，相当于Java的静态初始化
        var arr1 = arrayOf("java", "c", "c++", "python")
        //创建指定长度、数组中元素为null的数组，相当于java的动态初始化
        var arr2 = arrayOfNulls<Int>(5)
        var arr3 = arrayOfNulls<Double>(4)
        //创建长度为0的数组
        var arr4 = emptyArray<Short>()
    }
}