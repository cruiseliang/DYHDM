package com.yuan.dyhdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yuan.dyhdm.R;
import com.yuan.dyhdm.adapter.MyAdapter;
import com.yuan.dyhdm.base.BaseActivity;
import com.yuan.dyhdm.base.commonlistadapter.CommonAdapterForListView;
import com.yuan.dyhdm.base.commonlistadapter.CommonAdapterViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView mListView;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("multitemlistview",
            "World", "Welcome"));
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.id_lv_main);
//        mListView.setAdapter(mAdapter = new MyAdapter(this, mDatas));
        mListView.setAdapter(new CommonAdapterForListView<String>(mContext, mDatas, R.layout.item_lv_mainact) {
            @Override
            public void convert(CommonAdapterViewHolder holder, String item, int position) {
                holder.setText(R.id.tv_title, item);
//                TextView tvTitle=holder.getView(R.id.tv_title);
//                tvTitle.setText(item);
            }

        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    startActivity(new Intent(MainActivity.this,ListActivity.class));

                }

            }
        });

    }
}
