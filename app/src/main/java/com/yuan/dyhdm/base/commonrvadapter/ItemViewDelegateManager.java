package com.yuan.dyhdm.base.commonrvadapter;


import androidx.collection.SparseArrayCompat;

/**
 * Created by zhy on 16/6/22.
 */
public class ItemViewDelegateManager<T>
{
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();

    public int getItemViewDelegateCount()
    {
        return delegates.size();
    }

    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate)
    {
        int viewType = delegates.size();
        if (delegate != null)
        {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate)
    {
        if (delegates.get(viewType) != null)
        {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate)
    {
        if (delegate == null)
        {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType)
    {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0)
        {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }


    public void convert(RcyViewHolder holder, T item, int position,int viewType)
    {
        ItemViewDelegate<T> delegate = delegates.valueAt(viewType);
        if (delegate!=null){
            delegate.convert(holder, item, position);
        }
    }


    public ItemViewDelegate getItemViewDelegate(int viewType)
    {
        return delegates.get(viewType);
    }

}
