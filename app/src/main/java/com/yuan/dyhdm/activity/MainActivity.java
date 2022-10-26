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
import com.yuan.dyhdm.entity.HomeNavigationInfo;
import com.yuan.dyhdm.utils.ReflexionUtils;
import com.yuan.dyhdm.utils.TestHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView mListView;

    private MyAdapter mAdapter;
    private List<HomeNavigationInfo> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestHelper.testConstructor();
    }

    @Override
    public  void initData() {
        initListData();

//        mListView.setAdapter(mAdapter = new MyAdapter(this, mDatas));
        mListView.setAdapter(new CommonAdapterForListView<HomeNavigationInfo>(mContext, mList, R.layout.item_lv_mainact) {
            @Override
            public void convert(CommonAdapterViewHolder holder, HomeNavigationInfo item, int position) {
                holder.setText(R.id.tv_title, item.title);
//                TextView tvTitle=holder.getView(R.id.tv_title);
//                tvTitle.setText(item);
            }

        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(MainActivity.this, mList.get(position).cls));

            }
        });
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public  void initView() {
        mListView = (ListView) findViewById(R.id.id_lv_main);
    }

    @Override
    public void registerListener() {

    }


    private void  initListData(){

        mList=new ArrayList<>();
        mList.add(new HomeNavigationInfo("multitemlistview",ListActivity.class));
        mList.add(new HomeNavigationInfo("multircylist",RcyActivity.class));
        mList.add(new HomeNavigationInfo("kotlin",KotlinActivity.class));
        mList.add(new HomeNavigationInfo("MaterialDesign",MaterialDesign.class));
        mList.add(new HomeNavigationInfo("JetpackAct",JetpackAct.class));
        mList.add(new HomeNavigationInfo("自定义view",ZDYViewActivity.class));
        mList.add(new HomeNavigationInfo("自定义scrollview",ScrollviewActivity.class));
        mList.add(new HomeNavigationInfo("滑动置顶recylerview",CoordinatorLayoutActivity.class));
    }

}
