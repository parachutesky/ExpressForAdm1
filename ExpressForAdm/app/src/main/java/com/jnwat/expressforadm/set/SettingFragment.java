package com.jnwat.expressforadm.set;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.tools.GetFileCacheSize;
import com.jnwat.expressforadm.tools.SDCardTools;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;


/**
 * Created by chang-zhiyuan on 2016/3/4.
 */
public class SettingFragment extends com.blunderer.materialdesignlibrary.fragments.ScrollViewFragment {
    private Button btn_passwdmana;//密码管理
    private Button btn_linkus;
    private Button btn_sharesoftDestn;//分享好友
    private TextView txt_cachesize;
    private long size;
    private LinearLayout lin_clearcache;//清除缓存
    private View mDialog, progress_wheel_clearcache;
    private BitmapUtils mBitmapUtils;

    @Override
    public int getContentView() {
        return R.layout.setting_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        btn_passwdmana = (Button) view.findViewById(R.id.btn_passwdmana);
        btn_linkus = (Button) view.findViewById(R.id.btn_linkus);
        btn_sharesoftDestn = (Button) view.findViewById(R.id.btn_sharesoftDestn);
        // 缓存大小
        txt_cachesize = (TextView) view.findViewById(R.id.txt_cachesize);
        // 清除缓存
        lin_clearcache = (LinearLayout) view.findViewById(R.id.lin_clearcache);
        progress_wheel_clearcache = view.findViewById(R.id.progress_wheel_clearcache);
        setListener(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBitmapUtils = new BitmapUtils(getActivity().getApplicationContext());
    }

    @Override
    public boolean pullToRefreshEnabled() {
        return false;
    }

    @Override
    public int[] getPullToRefreshColorResources() {
        return new int[]{R.color.color_primary};
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                setRefreshing(false);
            }

        }, 2000);
    }


    /**
     * 监听事件
     */
    private void setListener(View view) {
        //设置密码
        btn_passwdmana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //联系我们
        btn_linkus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //分享
        btn_sharesoftDestn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //清除缓存
        lin_clearcache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_cachesize.setVisibility(View.INVISIBLE);
                progress_wheel_clearcache.setVisibility(View.VISIBLE);
                // 清除缓存
                clearCache();
                setCacheSize();
                mainHandler.sendEmptyMessageDelayed(201, 600);
            }
        });

    }


    /**
     * 清除内存和磁盘缓存
     */
    private void clearCache() {
        try {
            GetFileCacheSize.delete(new File(SDCardTools.getFilePath()
                    .toString()));
        } catch (Exception e) {
            // TODO: handle exception
        }
        mBitmapUtils.clearCache();

    }

    Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            int x = msg.what;
            switch (x) {
                case 101:
                    break;
                case 201:// 清除缓存
                    txt_cachesize.setText(size + "M");
                    txt_cachesize.setVisibility(View.VISIBLE);
                    progress_wheel_clearcache.setVisibility(View.INVISIBLE);
                    break;

            }
        }

    };

    /**
     * 设置缓存大小
     */
    private void setCacheSize() {
        try {
            // 文件大小
            size = (long) (GetFileCacheSize.getFolderSize(new File(SDCardTools
                    .getFilePath().toString())) + 0.00);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
