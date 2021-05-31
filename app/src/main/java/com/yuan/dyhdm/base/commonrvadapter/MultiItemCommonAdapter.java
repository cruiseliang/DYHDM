package com.yuan.dyhdm.base.commonrvadapter;

import android.content.Context;
import android.view.ViewGroup;

import com.yuan.dyhdm.base.commonlistadapter.ViewHolder;

import java.util.List;

/**
 * created by liangxuedong on 2021/5/28
 */
public  abstract  class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas,
                                  MultiItemTypeSupport<T> multiItemTypeSupport)
    {
        super(context, -1, datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position)
    {
        return mMultiItemTypeSupport.getItemViewType(position, mDatas.get(position));
    }

    @Override
    public RcyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        RcyViewHolder holder = RcyViewHolder.createViewHolder(mContext, parent, layoutId);
        return holder;
    }



}
