package com.yuan.dyhdm.activity

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.yuan.dyhdm.R
import com.yuan.dyhdm.base.BaseActivity
import kotlinx.android.synthetic.main.act_materialdesign.*


/**
 *created by liangxuedong on 2021/6/4
 *
 */
class MaterialDesign : BaseActivity() {

    /**
     *  MaterialDesign 控件
     *  Toolbar
     *  DrawerLayout 侧滑
     *  Navigation View 菜单
     *  floatingActionbutton 悬浮按钮
     *  Snackbar 高级toast 可交互
     *  Coordinationlayout 加强版Framelayout
     *  materialCardView 卡片布局 圆角 阴影
     *  appbarlayout 垂直方向的linearlayout
     *  CollapsingToolbarlayout 折叠式状态栏
     *
     *
     *
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_materialdesign)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { v ->
            Snackbar.make(v, "dada", Snackbar.LENGTH_SHORT).setAction("undo") {
                Toast.makeText(this, "dataddddd", Toast.LENGTH_SHORT).show()
            }.show()
        }
    }


}