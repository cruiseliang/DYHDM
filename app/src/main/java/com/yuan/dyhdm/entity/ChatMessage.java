package com.yuan.dyhdm.entity;



import com.yuan.dyhdm.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage
{
	private int icon;
	private String name;
	private String content;
	private String createDate;
	private boolean isComMeg;

	public int getViewType() {
		return viewType;
	}

	private int viewType;

	public final static int RECIEVE_MSG = 0;
	public final static int SEND_MSG = 1;

	public ChatMessage(int icon, String name, String content,
                       String createDate, boolean isComMeg)
	{
		this.icon = icon;
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.isComMeg = isComMeg;
	}

	public ChatMessage(int icon, String name, String content, String createDate, boolean isComMeg, int viewType) {
		this.icon = icon;
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.isComMeg = isComMeg;
		this.viewType = viewType;
	}

	public boolean isComMeg()
	{
		return isComMeg;
	}

	public void setComMeg(boolean isComMeg)
	{
		this.isComMeg = isComMeg;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getIcon()
	{
		return icon;
	}

	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	@Override
	public String toString()
	{
		return "ChatMessage [icon=" + icon + ", name=" + name + ", content="
				+ content + ", createDate=" + createDate +", isComing = "+ isComMeg()+ "]";
	}

	public static List<ChatMessage> MOCK_DATAS = new ArrayList<>();

	static {
		ChatMessage msg = null;
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you1 ",
				null, false,1);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you2 ",
				null, true,2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you3 ",
				null, false,1);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you4 ",
				null, true,2);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you5 ",
				null, false,1);
		MOCK_DATAS.add(msg);

		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you 6",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you7 ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you8 ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you9 ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you10 ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, true);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, false);
		MOCK_DATAS.add(msg);
	}


}
