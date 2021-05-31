package com.yuan.dyhdm.base.commonrvadapter;

/**
 * created by liangxuedong on 2021/5/28
 */
public interface MultiItemTypeSupport<T> {

    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);



}
