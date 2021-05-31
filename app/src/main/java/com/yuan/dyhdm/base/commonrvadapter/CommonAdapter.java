package com.yuan.dyhdm.base.commonrvadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;

import com.yuan.dyhdm.base.commonlistadapter.ViewHolder;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T>  extends RecyclerView.Adapter<RcyViewHolder>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;


    public CommonAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public RcyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        RcyViewHolder viewHolder = RcyViewHolder.createViewHolder(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RcyViewHolder holder, int position)
    {
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(RcyViewHolder holder, T t);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }



}
