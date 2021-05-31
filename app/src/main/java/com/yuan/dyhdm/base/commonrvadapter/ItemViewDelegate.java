package com.yuan.dyhdm.base.commonrvadapter;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    void convert(RcyViewHolder holder, T t, int position);

}
