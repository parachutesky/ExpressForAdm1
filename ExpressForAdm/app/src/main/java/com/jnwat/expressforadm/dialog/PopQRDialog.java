package com.jnwat.expressforadm.dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.google.zxing.WriterException;
import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.encoding.EncodingHandler;

/**
 * Created by chang-zhiyuan on 2016/3/7.
 * 显示二维码
 */
public class PopQRDialog {

    private PopupWindow popupWindow;

    public PopQRDialog() {
    }

    /**
     * @param ac
     * 显示
     */
    public void showQrDialog(Activity ac,String content) {

        if (popupWindow == null) {
            popupWindow = new PopupWindow(ac);
         //   popupWindow.setBackgroundDrawable(new ColorDrawable());
    /*        popupWindow.setTouchable(false); // 设置PopupWindow可触摸
            popupWindow.setFocusable(false);// 不能取消
            popupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸*/
            //获取popwindow焦点
            popupWindow.setFocusable(true);
            //设置popwindow如果点击外面区域，便关闭。
            popupWindow.setOutsideTouchable(true);
        }


        View view = ac.getLayoutInflater().inflate(R.layout.pop_showqrcode,
                null);
        ImageView qrImgImageView = (ImageView)view.findViewById(R.id.iv_qr_image);
        String contentString = "18363017176：田庄西一区八号楼";
        Bitmap qrCodeBitmap = null;
        try {
            qrCodeBitmap = EncodingHandler.createQRCode(contentString, 350);
            qrImgImageView.setImageBitmap(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }



        popupWindow.setContentView(view);
        // 软键盘不会挡着popupwindow
        // popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
      //  popupWindow.setAnimationStyle(R.style.popuStyle_zoom); // 设置
        popupWindow.showAtLocation(
                ac.getWindow().getDecorView()
                        .findViewById(android.R.id.content), Gravity.CENTER, 0,
                0);


        // 加上下面两行可以用back键关闭popupwindow，否则必须调用dismiss();
        popupWindow.update();

    }

    /**
     * 隐藏Dialog
     */
    public void dismisPop() {

        if (popupWindow != null) {
            try {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
