package com.jnwat.expressforadm.expressdetils;

import android.content.Intent;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.base.BaseActivity;

/**
 * 快递细节
 * Created by chang-zhiyuan on 2016/3/3.
 */
public class ExpressdetilsActivity extends BaseActivity {
    private boolean isSigle;//是批量还是单个

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setView() {
        Intent intent = getIntent();
        isSigle = intent.getBooleanExtra("issigle", false);//默认为批量
        setContentView(R.layout.activity_expressdetils);
        setBackListener("快递详情");


    }
}
