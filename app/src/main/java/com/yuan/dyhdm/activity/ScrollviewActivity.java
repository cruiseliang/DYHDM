package com.yuan.dyhdm.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.yuan.dyhdm.R;
import com.yuan.dyhdm.base.BaseActivity;
import com.yuan.dyhdm.utils.StringUtils;
import com.yuan.dyhdm.view.MyScrollView;

/**
 * created by liangxuedong on 2021/12/6
 */
public  class ScrollviewActivity  extends BaseActivity {
    private LinearLayout ll_top;
    private MyScrollView sv_content;
    private int topHeight;
    private float alpha = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_scrollview);

    }

    @Override
    public void initView() {
        ll_top=findView(R.id.ll_top);
        sv_content=findView(R.id.sv_content);

    }

    @Override
    public void registerListener() {
        sv_content.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                //滑动渐变
                int[] location = new int[2];
                ll_top.getLocationOnScreen(location);
                int locationY = location[1];
                Log.e("home", "locationY = " + locationY);
                Log.e("home", "scrollY = " + scrollY);

                if (scrollY>topHeight){
                    if (alpha < 1.0f) {
                        alpha = 1.0f;
                        ll_top.setAlpha(alpha);
                    }
                }else{
                    alpha = scrollY * 1.0f / (topHeight);
                    ll_top.setAlpha(alpha);

                }



            }
        });
    }

    @Override
    public void initData() {
        topHeight= StringUtils.dip2px(45);
    }

    @Override
    public void widgetClick(View v) {

    }
}
