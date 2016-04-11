package com.jnwat.expressforadm.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.outshow.ShowRemind;
import com.jnwat.expressforadm.tools.ModifySysTitle;
import com.jnwat.expressforadm.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;

/**
 * 基础类
 * Created by chang-zhiyuan on 2016/3/1.
 */
public abstract class BaseActivity extends FragmentActivity {

    private ImageView iv_back = null;
    private ImageView iv_message = null;
    private TextView title_bar_name = null;
    protected HttpUtils http = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setView();
        setTitleColor();
        initView();
        initData();
        setListener();

    }

    /**
     * 初始化
     */
    protected abstract void initView();

    /**
     * 设置监听器
     */
    protected abstract void setListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置视图
     */
    protected abstract void setView();


    /**
     * 设置返回Title的返回监听
     *
     * @param title
     * @param isshow
     */
    public void setBackListener(String title, boolean isshow) {
        // iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        if (isshow) {
        } else {

        }
        //title_bar_name = (TextView) findViewById(R.id.title_bar_name);
        title_bar_name.setText(title);

    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ShowRemind.HANDLER_NET_ERROE:
                    ToastViewTools.initToast(BaseActivity.this,
                            ShowRemind.LOGIN_NETERROR);
                    break;

                case ShowRemind.HANDLER_ANALYSIS_ERROE:
                    ToastViewTools.initToast(BaseActivity.this,
                            ShowRemind.ANALYSIS_ERROE);
                    break;

                case ShowRemind.HANDLER_DELER_ERROE:
                    break;

                case ShowRemind.HANDLER_LOGIN_SUCCESS:
                    break;
                case ShowRemind.HANDLER_GET_SUCCESS:
                    break;

                case ShowRemind.HANDLER_SHOW_DIALOG:
                    break;
                case ShowRemind.HANDLER_DISMISS_DIALOG:
                    break;
                case ShowRemind.HANDLER_DB_ERROR:
                    ToastViewTools
                            .initToast(BaseActivity.this, ShowRemind.DB_ERROR);
                    break;
                case ShowRemind.HANDLER_STATUS: // 网络的错误代码 400

                    ToastViewTools.initToast(BaseActivity.this,
                            ShowRemind.ErrorMessage);
                    break;
                case ShowRemind.HANDLER_NET_TYPE:// 网络类型
                    ToastViewTools
                            .initToast(BaseActivity.this, ShowRemind.NET_TYPE);
                    break;

            }
            super.handleMessage(msg);
        }
    };


    protected void setTitleColor() {
        ModifySysTitle.ModifySysTitleStyle(R.color.color_primary_dark,
                BaseActivity.this);
    }

    /**
     * 设置返回Title的返回监听
     *
     * @param
     */
    public void setBackListener(String title) {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        title_bar_name = (TextView) findViewById(R.id.title_bar_name);
        title_bar_name.setText(title);

    }
}
