package com.yuan.dyhdm.base.commonrvadapter;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RcyViewHolder holder, T t, int position);

}
