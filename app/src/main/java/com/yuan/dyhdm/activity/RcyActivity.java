package com.yuan.dyhdm.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuan.dyhdm.R;
import com.yuan.dyhdm.adapter.ChatAdapterForRv;
import com.yuan.dyhdm.base.BaseActivity;
import com.yuan.dyhdm.entity.ChatMessage;

/**
 * created by liangxuedong on 2021/5/28
 */
public class RcyActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_rcy);




    }

    @Override
    public void initView() {
        mRecyclerView=findViewById(R.id.rcy_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mRecyclerView.setAdapter(new ChatAdapterForRv(this, ChatMessage.MOCK_DATAS));
    }

    @Override
    public void registerListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void widgetClick(View v) {

    }


}
