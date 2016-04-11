package com.jnwat.expressforadm.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.fragment.FragmentAc_Adm;
import com.jnwat.expressforadm.shelvehistory.ShelveHistoryActivity;
import com.jnwat.expressforadm.tackout.TackOutActivity;
import com.zxing.activity.CaptureActivity;


/**
 * Created by chang-zhiyuan on 2016/3/4.
 */
public class MainFragment extends com.blunderer.materialdesignlibrary.fragments.AFragment {
    ImageView iv_laihuoshangjia;
    ImageView iv_kehuqujian;
    ImageView iv_qujianjilu;
    ImageView iv_shelve_shistory; //上架历史
    //设置
    ImageView iv_message;

    protected void setListener() {
        //来货上架，打开摄像头
        iv_laihuoshangjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        });
        //客户取件
        iv_kehuqujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TackOutActivity.class);
                startActivity(intent);

            }
        });
        //取件记录
        iv_qujianjilu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FragmentAc_Adm.class);
                startActivity(intent);
            }
        });
        //上架历史
        iv_shelve_shistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ShelveHistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
   //     super.onCreateView(inflater, container, savedInstanceState);
     //   initView(getView());
        View view = inflater.inflate(R.layout.activity_main, container, false);
        initView(view);
        setListener();
        return view ;
    }

    protected void initView(View view) {
        iv_qujianjilu = (ImageView)view. findViewById(R.id.iv_qujianjilu);
        iv_kehuqujian = (ImageView)view. findViewById(R.id.iv_kehuqujian);
        iv_laihuoshangjia = (ImageView)view. findViewById(R.id.iv_laihuoshangjia);
        iv_shelve_shistory = (ImageView)view. findViewById(R.id.iv_shelve_shistory);
        iv_message = (ImageView)view. findViewById(R.id.iv_message);

    }



}
