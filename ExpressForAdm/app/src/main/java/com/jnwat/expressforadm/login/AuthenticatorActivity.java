package com.jnwat.expressforadm.login;


import android.view.View;
import android.widget.Button;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.base.BaseActivity;

/**
 *
 * 登录
 * Created by chang-zhiyuan on 2016/3/3.
 */
public class AuthenticatorActivity extends BaseActivity {
    Button btn_login;
    @Override
    protected void initView() {
        btn_login = (Button)findViewById(R.id.btn_login);

    }

    @Override
    protected void setListener() {
        //登录
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       /*         new MaterialDialog.Builder(this)
                        .title(R.string.progress_dialog)
                        .content(R.string.please_wait)
                        .progress(true, 0)
                        .show();*/
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setView() {
        setTitleColor();
        setContentView(R.layout.activity_authenticator);
        setBackListener("登录");
    }
}
