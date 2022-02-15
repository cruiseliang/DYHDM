package com.yuan.dyhdm.activity


/**
 *created by liangxuedong on 2021/11/19
 *
 */
fun String.lettersCount():Int{
    var count=0
    for (char in this){
        if (char.isLetter()){
            count++
        }
    }
    return count
}