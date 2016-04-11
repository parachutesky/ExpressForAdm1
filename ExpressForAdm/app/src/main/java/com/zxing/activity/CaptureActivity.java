package com.zxing.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.bean.ExpressInfoBean;
import com.jnwat.expressforadm.tools.JudgeExpressType;
import com.jnwat.expressforadm.tools.ToastViewTools;
import com.kyleduo.switchbutton.SwitchButton;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.zxing.camera.CameraManager;
import com.zxing.dao.Dao_SqlSave;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Vector;
import java.util.regex.Pattern;

import carbon.beta.ToggleButton;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class CaptureActivity extends FragmentActivity implements Callback, NumberPickerDialogFragment.NumberPickerDialogHandlerV2 {
    private DbUtils dbUtils;
    private Dao_SqlSave mDao_sqlSave;//数据存储
    private TextView tv_capturetype;
    private SwitchButton mDelaySb;
    private carbon.beta.ToggleButton toggleButton_mutl;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ImageView cancelScanButton;
    //显示当前货架的位置
    private TextView tv_show_position, tv_show;
    //地址信息 二维码信息
    private String resultStr = "";
    private TextView title_bar_name;
    private String type;//快递类型
    //条形码信息
    private String express_id = "";
    ///批量或者单个
    private boolean isSigle = true;//false 默认批量  true 单个

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture);
        dbUtils = DbUtils.create(this);
        mDao_sqlSave = new Dao_SqlSave(dbUtils);
  /*      ModifySysTitle.ModifySysTitleStyle(R.color.color_trans,
                CaptureActivity.this);*/
        // ViewUtil.addTopView(getApplicationContext(), this,
        // R.string.scan_card);
        CameraManager.init(getApplication());
        toggleButton_mutl = (ToggleButton) findViewById
                (R.id.toggleButton_mutl);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        cancelScanButton = (ImageView) this.findViewById(R.id.iv_back);
        title_bar_name = (TextView) this.findViewById(R.id.title_bar_name);
        tv_show = (TextView) this.findViewById(R.id.tv_show);
        tv_capturetype = (TextView) this.findViewById(R.id.tv_capturetype);
        mDelaySb = (SwitchButton) findViewById(R.id.sb_use_delay);
        title_bar_name.setText("二维码");
        tv_capturetype.setText("批量扫描");
        setListener();
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        tv_show_position = (TextView) findViewById(R.id.tv_scan_nowposition);
        toggleButton_mutl.setChecked(isSigle);
//默认为批量上传 为false
        toggleButton_mutl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                isSigle = isChecked;
                toggleButton_mutl.setChecked(isChecked);


            }

        });

    }


    /**
     * 设置监听
     */
    private void setListener() {

        // work with delay
        mDelaySb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDelaySb.setEnabled(false);
                mDelaySb.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDelaySb.setEnabled(true);
                        yanshi350rec();
                    }
                }, 900);
                isSigle = isChecked;
                if (isChecked) {//批量
                    tv_capturetype.setText("批量扫描");
                } else {
                    tv_capturetype.setText("单个扫描");
                }


            }
        });
        tv_show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_show.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

        // quit the scan view
        cancelScanButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CaptureActivity.this.finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();

        // FIXME
        if (resultString.equals("")) {
            Toast.makeText(CaptureActivity.this, "扫描失败!",
                    Toast.LENGTH_SHORT).show();
        } else {
            type = JudgeExpressType.judegeType(resultString);
            if (result.getBarcodeFormat().toString().equals("QR_CODE")) {//为二维码
                resultStr = resultString;

                tv_show_position.setText("当前位置：" + resultStr);
                //扫描玩二维码
                restartCamera();
            } else {//条形码
                if (resultStr.equals("")) {//还没有扫描二维码
                    ToastViewTools.initToast(CaptureActivity.this, "请先记录当前位置!");
                    yanshi350rec();
                } else {//处理扫描的条形码
                    tv_show.setVisibility(View.GONE);
                    if (isSigle) {//不同的模式不同的处理方式

                        //提示输入手机号
                        showNubDialog();
                        express_id = resultString;
                    } else {//单个处理
                        Intent intent = new Intent();
                        intent.setClass(CaptureActivity.this, SigleUpLoadActivity.class);
                        intent.putExtra("express_type", type);
                        intent.putExtra("issigle", isSigle);
                        startActivity(intent);
                    }
                }
            }
            /*
             返回数据
			Intent resultIntent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("result", resultString);
			resultIntent.putExtras(bundle);
			this.setResult(RESULT_OK, resultIntent);*/
        }
        //CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    /**
     * 重新扫描二维码
     */
    void restartCamera() {
        initCamera();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);

        // 恢复活动监控器
    }

    /**
     * 初始化，重复扫描
     */
    public void initCamera() {
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        initCamera(surfaceHolder);
        if (handler != null)
            handler.restartPreviewAndDecode();
    }

    /**
     * 数据存储
     *
     * @param reference
     * @param number
     * @param decimal
     * @param isNegative
     * @param fullNumber
     */
    @Override
    public void onDialogNumberSet(int reference, BigInteger number, double decimal, boolean isNegative, BigDecimal fullNumber) {
        Pattern p_ = Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$");//验证手机

        if (p_.matcher(number + "").matches()) {//输出的号码是否合法 ^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$
            try {
                ExpressInfoBean mExpressInfoBean = new ExpressInfoBean();
                mExpressInfoBean.setExpress_id(express_id);
                mExpressInfoBean.setExpress_position(resultStr);//位置信息 二维码 货柜的位置
                mExpressInfoBean.setExpress_type(type);
                mExpressInfoBean.setIsHad(true);//是否取走  true 未取走
                mExpressInfoBean.setUsername("test");//用户名
                mExpressInfoBean.setExpress_handadd("地址1");//地址
                mExpressInfoBean.setExpress_handaddd("地址2");//地址2
                mExpressInfoBean.setExpress_handadddd("地址3");//地址3
                mExpressInfoBean.setExpress_sendname("test");
                mExpressInfoBean.setExpress_sendphone(number + "");
                mExpressInfoBean.setExpress_handtime("2016年3月22日 10:50:36");
                mExpressInfoBean.setUsername("test");//用户名
                mDao_sqlSave.saveThisClass(mExpressInfoBean);
                ToastViewTools.initToast(CaptureActivity.this, "保存成功");
                yanshi350rec();
            } catch (DbException e) {
                e.printStackTrace();
                ToastViewTools.initToast(CaptureActivity.this, "请重新尝试一遍");
                yanshi350rec();
            }
        } else {
            ToastViewTools.initToast(CaptureActivity.this, "手机号码输入不正确!");

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //显示dialog
                    showNubDialog();

                }

            }, 150);   //4.5秒
        }
    }

    @Override
    public void onDisMiss() {

    }

    /**
     * 延时回复350
     */
    private void yanshi350rec() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //显示dialog
                restartCamera();
            }

        }, 450);   //4.5秒
    }

    /**
     * 显示数字键盘
     */
    private void showNubDialog() {
        NumberPickerBuilder npb = new NumberPickerBuilder()
                .setFragmentManager(getSupportFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment_Light);
        npb.show();
    }

}