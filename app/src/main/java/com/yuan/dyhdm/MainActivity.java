package com.yuan.dyhdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.yuan.dyhdm.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Hello",
            "World", "Welcome"));
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.id_lv_main);
        mListView.setAdapter(mAdapter = new MyAdapter(this, mDatas));

    }
}
