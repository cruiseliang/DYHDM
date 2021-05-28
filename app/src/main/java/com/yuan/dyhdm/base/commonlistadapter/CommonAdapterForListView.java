package com.yuan.dyhdm.base.commonlistadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by fang on 2019/2/22.
 * 参考：https://blog.csdn.net/lmj623565791/article/details/38902805/
 */

public abstract class CommonAdapterForListView<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public CommonAdapterForListView(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (position > getCount() - 1 || mDatas == null) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CommonAdapterViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();

    }

    public abstract void convert(CommonAdapterViewHolder helper, T item, int position);

    private CommonAdapterViewHolder getViewHolder(int position, View convertView,
                                                  ViewGroup parent) {
        return CommonAdapterViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    public void setData(List<T> mlist) {
        this.mDatas = mlist;
        notifyDataSetChanged();
    }


}
