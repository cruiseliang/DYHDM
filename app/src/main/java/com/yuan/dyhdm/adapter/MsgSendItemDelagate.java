package com.yuan.dyhdm.adapter;


import com.yuan.dyhdm.R;
import com.yuan.dyhdm.base.commonrvadapter.ItemViewDelegate;
import com.yuan.dyhdm.base.commonrvadapter.RcyViewHolder;
import com.yuan.dyhdm.entity.ChatMessage;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgSendItemDelagate implements ItemViewDelegate<ChatMessage>
{

    @Override
    public void convert(RcyViewHolder holder, ChatMessage chatMessage, int position)
    {
        holder.setText(R.id.chat_send_content, chatMessage.getContent());
        holder.setText(R.id.chat_send_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_send_icon, chatMessage.getIcon());
    }
}
