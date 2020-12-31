package com.yuan.dyhdm.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuan.dyhdm.R;

import java.util.List;

/**
 * created by liangxuedong on 2020/12/15
 */
public class MyAdapter2<T> extends CommonAdapter {


    public MyAdapter2(Context context, List mDatas, int mItemLayoutId) {
        super(context, mDatas, mItemLayoutId);
    }


    @Override
    public void convert(ViewHolder viewHolder, Object item) {
        viewHolder.setText(R.id.id_tv_title,(String)item);
    }
}
