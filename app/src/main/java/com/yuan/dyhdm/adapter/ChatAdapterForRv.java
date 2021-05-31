package com.yuan.dyhdm.adapter;

import android.content.Context;


import com.yuan.dyhdm.R;
import com.yuan.dyhdm.base.commonrvadapter.MultiItemTypeAdapter;
import com.yuan.dyhdm.base.commonrvadapter.MultiItemTypeSupport;
import com.yuan.dyhdm.entity.ChatMessage;


import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<ChatMessage> {

    public ChatAdapterForRv(Context context, List<ChatMessage> datas) {
        super(context, datas);

        addItemViewDelegate(0,R.layout.main_chat_send_msg, new MsgSendItemDelagate());
        addItemViewDelegate(1, R.layout.main_chat_from_msg,new MsgComingItemDelagate());

        //增加type  layoutid  itemDelagate
    }

    @Override
    public int getItemViewType(int postion, ChatMessage msg) {

        return msg.getViewType();
    }


}
