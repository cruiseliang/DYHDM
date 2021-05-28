package com.yuan.dyhdm.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.yuan.dyhdm.R;
import com.yuan.dyhdm.adapter.lv.ChatAdapter;
import com.yuan.dyhdm.base.BaseActivity;
import com.yuan.dyhdm.entity.ChatMessage;

/**
 * created by liangxuedong on 2021/5/27
 */
public class ListActivity extends BaseActivity {
    ListView id_lv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_list);

        initView();
    }

    private void initView() {
        id_lv_list=findViewById(R.id.id_lv_list);


        id_lv_list.setDivider(null);
        id_lv_list.setAdapter(new ChatAdapter(this, ChatMessage.MOCK_DATAS));
    }
}
