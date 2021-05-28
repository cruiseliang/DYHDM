package com.yuan.dyhdm.adapter.lv;

import android.content.Context;


import com.yuan.dyhdm.base.commonlistadapter.MultiItemTypeAdapter;
import com.yuan.dyhdm.entity.ChatMessage;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapter extends MultiItemTypeAdapter<ChatMessage>
{
    public ChatAdapter(Context context, List<ChatMessage> datas)
    {
        super(context, datas);
        addItemViewDelegate(new MsgComingItemDelagate());
        addItemViewDelegate(new MsgSendItemDelagate());

    }

}
