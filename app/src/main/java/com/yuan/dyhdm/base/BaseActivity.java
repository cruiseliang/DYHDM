package com.yuan.dyhdm.base;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * created by liangxuedong on 2021/5/26
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();
    protected Context mContext;


    public BaseActivity() {
        mContext = this;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mContext == this) {
            super.onCreate(savedInstanceState);
        }
    }



}
