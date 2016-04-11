package com.jnwat.expressforadm.sarchresult;

import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.base.BaseActivity;

/**
 * Created by chang-zhiyuan on 2016/3/18.
 * 搜索结果
 */
public class SearchResultActivity extends BaseActivity {
    private ListView lv_search;
    TextView textView;
    private String searchKey = "";//搜索关键字
    @Override
    protected void initView() {
        //搜索的列表
        lv_search = (ListView)findViewById(R.id.lv_search);
    }

    @Override
    protected void setListener() {
        setBackListener("搜索");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_searchresult);
        textView = (TextView)findViewById(R.id.textView);

        Intent intent = getIntent();
        //获取传过来的数据
        searchKey = intent.getStringExtra("search");

        textView .setText(searchKey);
    }
}
