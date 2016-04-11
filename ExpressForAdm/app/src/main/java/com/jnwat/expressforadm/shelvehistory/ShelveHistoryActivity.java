package com.jnwat.expressforadm.shelvehistory;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.adpter.ShelveHistoryAdapter;
import com.jnwat.expressforadm.base.BaseActivity;
import com.jnwat.expressforadm.bean.ExpressInfoBean;
import com.jnwat.expressforadm.tools.ToastViewTools;
import com.lidroid.xutils.DbUtils;
import com.zxing.dao.Dao_SqlFind;

import java.util.List;

/**
 * Created by chang-zhiyuan on 2016/3/21.
 * 上架历史查询
 */
public class ShelveHistoryActivity extends BaseActivity {
    DbUtils dbUtils;
    Dao_SqlFind dao_sqlFind;//数据查询
    List<ExpressInfoBean> mListExpressInfoBeans;//查询到的列表
    ShelveHistoryAdapter mShelveHistoryAdapter;
    TextView tv_showyou;

    private ListView lv_shelvehitory;


    @Override
    protected void initView() {
        lv_shelvehitory = (ListView) findViewById(R.id.lv_shelvehitory);
        tv_showyou = (TextView) findViewById(R.id.tv_showyou);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        try {
            mListExpressInfoBeans = dao_sqlFind.getListExpressList();
            ToastViewTools.initToast(ShelveHistoryActivity.this, "已经存储:" + mListExpressInfoBeans.size());
            if (mListExpressInfoBeans.size() > 0) {
                mShelveHistoryAdapter = new ShelveHistoryAdapter(ShelveHistoryActivity.this, mListExpressInfoBeans, null);
                lv_shelvehitory.setAdapter(mShelveHistoryAdapter);
            } else {
                tv_showyou.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void setView() {
        dbUtils = DbUtils.create(this);
        dao_sqlFind = new Dao_SqlFind(dbUtils);

        setContentView(R.layout.activity_shelvehistory);
        setBackListener("历史结果");

    }
}
