package com.yuan.dyhdm.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import com.yuan.dyhdm.MyApplication;
import com.yuan.dyhdm.utils.StringUtils;
import com.yuan.dyhdm.utils.Utils;

/**
 * created by liangxuedong on 2021/5/26
 * <p>
 * dialog
 * toast
 * 数据初始化
 * 跳转
 * findView
 * <p>
 * 软键盘
 * 登录状态获取
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected String TAG = this.getClass().getSimpleName();

    protected Context mContext;
    protected Activity mActivity;


    /**
     * 当前Activity是否在在最顶端
     */
    protected boolean mIsFront = false;


    public BaseActivity() {
        mContext = MyApplication.Companion.getSelf();
        mActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (mActivity == this) {
            super.onCreate(savedInstanceState);
        }

        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            initParam(getIntent().getExtras());
        }

    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        registerListener();
        initData();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView();
        registerListener();
        initData();
    }

    public abstract void initView();

    public abstract void registerListener();

    public abstract void initData();


    protected void initParam(Bundle bundle) {

    }


    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }


    /**
     * toast（默认 时间Toast.LENGTH_LONG）
     *
     * @param msg 内容
     */
    protected void toast(String msg) {
        if (StringUtils.isNullOrEmpty(msg)) {
            return;
        }
        Utils.toast(mContext, msg, mIsFront);
    }


    public void toast(int resId) {
        Utils.toast(mContext, mContext.getString(resId), mIsFront);
    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mIsFront = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsFront = false;
    }

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }


    //防止快速点击  1s一次
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }


    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
//            if (ToolUtil.isShouldHideInput(v, ev)) {
//
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

}
