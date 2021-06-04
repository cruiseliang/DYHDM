package com.yuan.dyhdm.activity

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yuan.dyhdm.R
import com.yuan.dyhdm.ViewModel.JetViewModel
import com.yuan.dyhdm.base.BaseActivity
import com.yuan.dyhdm.entity.MyObserver
import kotlinx.android.synthetic.main.act_jetpack.*


/**
 *created by liangxuedong on 2021/6/4
 *
 * lifecyles  获取activity 生命周期
 * liveData 组件 使用在viewmodel中
 *
 *
 */
class JetpackAct : BaseActivity() {
    lateinit var mJetMv: JetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_jetpack)

//        mJetMv=ViewModelProvider(this).get(JetViewModel::class.java)//无传参
        mJetMv=ViewModelProvider(this,MainViewModerFactory(10)).get(JetViewModel::class.java)//有传参

        btnPlusOne.setOnClickListener {
            mJetMv.count++
            refreshCounter()

        }
        lifecycle.addObserver(MyObserver())

    }

   private  fun refreshCounter(){
       tv_jet_info.text=mJetMv.count.toString()
   }

    class MainViewModerFactory(private val countreserved:Int):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return JetViewModel(countreserved) as T
        }
    }

}