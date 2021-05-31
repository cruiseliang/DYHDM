package com.yuan.dyhdm.base.commonrvadapter;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;


import androidx.recyclerview.widget.RecyclerView;


import com.yuan.dyhdm.R;
import com.yuan.dyhdm.entity.ChatMessage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<RcyViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;

    protected HashMap<Integer,Integer>mTypeMap=new HashMap<>();


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();

    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position,mDatas.get(position));
    }


    @Override
    public RcyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutId(viewType);
        RcyViewHolder holder = RcyViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
        setListener(parent, holder, viewType);
        return holder;
    }

    public void onViewHolderCreated(RcyViewHolder holder, View itemView){

    }

    public void convert(RcyViewHolder holder, T t,int viewType) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition(),viewType);
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final RcyViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(RcyViewHolder holder, int position) {
        convert(holder, mDatas.get(position),getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = mDatas.size();
        return itemCount;
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType,int layoutID, ItemViewDelegate<T> itemViewDelegate) {
        mTypeMap.put(viewType,layoutID);
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    public int getLayoutId(int itemType) {
        //根据itemType返回item布局文件id
        if (mTypeMap!=null&&mTypeMap.size()>0){
            if (mTypeMap.containsKey(itemType)){
                return mTypeMap.get(itemType);
            }
        }
        return mTypeMap.get(0);

    }

    public  abstract  int getItemViewType(int postion, T msg);



    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void init(){
        MultiItemTypeSupport   multiItemSupport = new MultiItemTypeSupport<ChatMessage>() {
            @Override
            public int getLayoutId(int itemType) {
                //根据itemType返回item布局文件id
                switch (itemType) {
                    case 0:
                        return R.layout.main_chat_send_msg;

                    case 1:
                        return R.layout.main_chat_from_msg;

                    default:
                        return R.layout.main_chat_send_msg;
                }
            }

            @Override
            public int getItemViewType(int postion, ChatMessage msg) {
                //根据当前的bean返回item type
                return msg.getViewType();
            }
        };
    }



}
