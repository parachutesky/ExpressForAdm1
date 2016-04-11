package com.jnwat.expressforadm.tackout;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.base.BaseActivity;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.zxing.activity.CaptureActivity;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 取件
 * Created by chang-zhiyuan on 2016/3/9.
 */
public class TackOutActivity extends BaseActivity implements NumberPickerDialogFragment.NumberPickerDialogHandlerV2 {
    private NumberPickerBuilder mNumberPickerBuilder;
    private MaterialAutoCompleteTextView button_getexpress_nub;
    private Button button_erweimaqujian;//二维码取件
    private Button btn_verification;//二维码取件

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

        button_getexpress_nub.setInputType(InputType.TYPE_NULL);
        button_getexpress_nub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NumberPickerBuilder npb = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                npb.show();

            }
        });
        button_erweimaqujian.setOnClickListener(new View.OnClickListener() {//二维码扫描取件
            @Override
            public void onClick(View v) {
                //来货上架，打开摄像头
                Intent intent = new Intent();
                intent.setClass(TackOutActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });
        btn_verification.setOnClickListener(new View.OnClickListener() {//二维码扫描取件
            @Override
            public void onClick(View v) {
                NumberPickerBuilder npb = new NumberPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                npb.show();

            }
        });

    }

    @Override
    protected void initData() {

        mNumberPickerBuilder = new NumberPickerBuilder()
                .setStyleResId(R.style.BetterPickersDialogFragment_Light);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_takeout);
        setBackListener("取件");
        button_getexpress_nub = (MaterialAutoCompleteTextView) findViewById(R.id.button_getexpress_nub);
        button_erweimaqujian = (Button) findViewById(R.id.button_erweimaqujian);
        btn_verification = (Button) findViewById(R.id.btn_verification);
    }

    @Override
    public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
        button_getexpress_nub.setText("" + number);//getString(R.string.number_picker_result_value, number, decimal, isNegative, fullNumber) + "\n" +
    }

    @Override
    public void onDisMiss() {
        button_getexpress_nub.setText("取消");
    }


}
