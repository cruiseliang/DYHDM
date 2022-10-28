package com.yuan.dyhdm.interfaceP

import com.yuan.dyhdm.entity.Book
import retrofit2.Call
import retrofit2.http.GET


/**
 *created by liangxuedong on 2021/6/3
 *
 */
interface BookService {
    @GET("")
    fun getBookData():Call<List<Book>>
}