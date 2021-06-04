package com.yuan.dyhdm.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.yuan.dyhdm.R
import kotlinx.android.synthetic.main.act_kotline.*
import kotlinx.android.synthetic.main.item_lv_mainact.view.*


/**
 *created by liangxuedong on 2021/6/1
 *
 */
class TitleLayout(context:Context,attrs:AttributeSet):LinearLayout(context,attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.item_lv_mainact,this)
        tv_title.setOnClickListener(){

        }
    }
}