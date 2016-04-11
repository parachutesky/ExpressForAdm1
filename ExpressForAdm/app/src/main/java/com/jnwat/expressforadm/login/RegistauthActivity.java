package com.jnwat.expressforadm.login;


import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.base.BaseActivity;

/**
 * Created by chang-zhiyuan on 2016/3/3.
 */
public class RegistauthActivity extends BaseActivity {
    private Button btn_vercode;
    private TimeCount time;//时间控制器
    private TextView tv_daisou_protocol;

    @Override
    protected void initView() {
        btn_vercode = (Button) findViewById(R.id.btn_vercode);
        tv_daisou_protocol = (TextView) findViewById(R.id.tv_daisou_protocol);
    }

    @Override
    protected void setListener() {
        btn_vercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.start();// 开始计时
            }
        });
        tv_daisou_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegistauthActivity.this,ShowProtocolActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setView() {
        time = new TimeCount(60000, 1000);
        setTitleColor();
        setContentView(R.layout.activity_registauth);
        setBackListener("注册帐号");
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btn_vercode.setText("获取验证码");
            btn_vercode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btn_vercode.setClickable(false);//防止重复点击
            btn_vercode.setText(millisUntilFinished / 1000 + "秒后重新获取");
        }
    }


}
