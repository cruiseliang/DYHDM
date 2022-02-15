package com.yuan.dyhdm

import android.app.Application
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.yuan.dyhdm.utils.UtilsLog


/**
 *created by liangxuedong on 2021/6/3
 *
 */
class MyApplication : Application() {



    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }





    companion object{
        lateinit var mContext: Context
        fun getSelf(): Context? {
            return mContext
        }
    }


    /**
     * toast singleton，用来统一显示toast，这样就可以实现toast的快速刷新
     */
    enum class toastMgr {
        builder;

        private var v: View? = null
        private var tv: TextView? = null
        private var toast: Toast? = null
        private var oldMsg: CharSequence? = null
        private var oneTime: Long = 0
        private var twoTime: Long = 0

        fun init(c: Context?) {
            // v = Toast.makeText(c, "", Toast.LENGTH_SHORT).getView();
            v = LayoutInflater.from(c).inflate(R.layout.toast, null)
            tv = v!!.findViewById<View>(R.id.tv_toast) as TextView
            if (toast == null) {
                toast = Toast(c)
            }
            toast!!.view = v
        }

        fun display(text: CharSequence, duration: Int) {
            try {
                if (text.length != 0) {
                    twoTime = System.currentTimeMillis()
                    tv!!.text = text
                    toast!!.duration = duration
                    toast!!.setGravity(Gravity.CENTER, 0, 0)
                    if (text == oldMsg) {
                        if (twoTime - oneTime > 2000) {
                            toast!!.show()
                            oneTime = twoTime
                        }
                    } else {
                        oldMsg = text
                        toast!!.show()
                    }
                    //                    toast.show();
                }
            } catch (e: Exception) {
                UtilsLog.e("toast", e.toString() + "")
            }
        }

        fun display(Resid: Int, duration: Int) {
            try {
                if (Resid != 0) {
                    tv!!.setText(Resid)
                    toast!!.duration = duration
                    toast!!.show()
                }
            } catch (e: Exception) {
                UtilsLog.e("toast", e.toString() + "")
            }
        }

        fun display(text: CharSequence, duration: Int, position: Int, yOffset: Int) {
            try {
                if (text.length != 0) {
                    tv!!.text = text
                    toast!!.duration = duration
                    toast!!.setGravity(position, 0, yOffset)
                    toast!!.show()
                }
            } catch (e: Exception) {
                UtilsLog.e("toast", e.toString() + "")
            }
        }
    }



}