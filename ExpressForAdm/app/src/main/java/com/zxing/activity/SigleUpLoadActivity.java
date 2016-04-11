package com.zxing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.base.BaseActivity;
import com.jnwat.expressforadm.tools.NativeUtil;
import com.jnwat.expressforadm.tools.ToastViewTools;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by chang-zhiyuan on 2016/3/23.
 */
public class SigleUpLoadActivity extends BaseActivity implements NumberPickerDialogFragment.NumberPickerDialogHandlerV2 {
    private GridView gv_addimage;
    private TextView tv_phonenub;
    private LinearLayout lin_setphonenub;
    private final int IMAGE_OPEN = 1;        //打开图片标记
    private String pathImage;                //选择图片路径
    private ArrayList<HashMap<String, Object>> imageItem;
    private SimpleAdapter simpleAdapter;     //适配器
    ImageLoader imageLoader;
    DisplayImageOptions options;

    @Override
    protected void initView() {
        gv_addimage = (GridView) findViewById(R.id.gv_addimage);
        lin_setphonenub = (LinearLayout) findViewById(R.id.lin_setphonenub);
        tv_phonenub = (TextView) findViewById(R.id.tv_phonenub);
    }

    @Override
    protected void setListener() {
  /*
         * 监听GridView点击事件
         * 报错:该函数必须抽象方法 故需要手动导入import android.view.View;
         */
        gv_addimage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (imageItem.size() == 5) { //第一张为默认图片
                    if (position != 0) {
                        dialog(position);
                    } else {
                        Toast.makeText(SigleUpLoadActivity.this, "图片数4张已满", Toast.LENGTH_SHORT).show();
                    }
                } else if (position == 0) { //点击图片位置为+ 0对应0张图片
                    Toast.makeText(SigleUpLoadActivity.this, "添加图片", Toast.LENGTH_SHORT).show();
                    //选择图片
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, IMAGE_OPEN);
                    //通过onResume()刷新数据
                } else {
                    dialog(position);
                    //Toast.makeText(MainActivity.this, "点击第"+(position + 1)+" 号图片",
                    //		Toast.LENGTH_SHORT).show();
                }
            }
        });
        //设置电话号码
        lin_setphonenub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNubDialog();
            }
        });
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

    @Override
    protected void initData() {

    }

    @Override
    protected void setView() {
        imageLoader = ImageLoader.getInstance();
        setContentView(R.layout.activity_sigleupload);
        setBackListener("单个上传");
        initOptionUIL();
        initView();
        //获取资源图片加号
        imageItem = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("itemImage", "drawable://" + R.drawable.photo_new);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this,
                imageItem, R.layout.griditem_addpic,
                new String[]{"itemImage"}, new int[]{R.id.imageView1});

        /*
         * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如
         * map.put("itemImage", R.drawable.img);
         * 解决方法:
         *              1.自定义继承BaseAdapter实现
         *              2.ViewBinder()接口实现
         *  参考 http://blog.csdn.net/admin_/article/details/7257901
         */
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if (view instanceof ImageView && data instanceof String) {
                    ImageView i = (ImageView) view;
                    System.out.println("data:" + data);
                    System.out.println("options:" + options);
                    System.out.println("imageLoader:" + imageLoader);
                    imageLoader.displayImage((String) data, i, options);
                    return true;
                }
                return false;
            }
        });
        gv_addimage.setAdapter(simpleAdapter);
    }

    //获取图片路径 响应startActivityForResult
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //打开图片
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            Uri uri = data.getData();
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                //查询选择图片
                Cursor cursor = getContentResolver().query(
                        uri,
                        new String[]{MediaStore.Images.Media.DATA},
                        null,
                        null,
                        null);
                //返回 没找到选择图片
                if (null == cursor) {
                    ToastViewTools.initToast(SigleUpLoadActivity.this, "请重新尝试!");
                    return;
                }
                //光标移动至开头 获取图片路径
                cursor.moveToFirst();

                pathImage = "file:///" + cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }  //end if 打开图片
    }

    //刷新图片
    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", pathImage);
            imageItem.add(map);
            /*
            simpleAdapter = new SimpleAdapter(this,
                    imageItem, R.layout.griditem_addpic,
                    new String[]{"itemImage"}, new int[]{R.id.imageView1});
            simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if (view instanceof ImageView && data instanceof String) {
                        ImageView i = (ImageView) view;
                        System.out.println("data:" + data);
                        System.out.println("options:" + options);
                        System.out.println("imageLoader:" + imageLoader);
                        imageLoader.displayImage("file:///" + (String) data, i, options);
                        return true;
                    }
                    return false;
                }
            });
            gv_addimage.setAdapter(simpleAdapter);

            */
            simpleAdapter.notifyDataSetChanged();
            //刷新后释放防止手机休眠后自动添加
            pathImage = null;
        }
    }

    /*
         * Dialog对话框提示用户删除操作
         * position为删除图片位置
         */
    protected void dialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SigleUpLoadActivity.this);
        builder.setMessage("确认移除已添加图片吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                imageItem.remove(position);
                simpleAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void testJpeg() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    int quality = 90;//original
                    quality = 50;//同学们可以与原生的压缩方法对比一下，同样设置成50效果如何
                    InputStream in = getResources().getAssets()
                            .open("test2.jpg");
                    Bitmap bit = BitmapFactory.decodeStream(in);
            /*
                    File dirFile = getExternalCacheDir();
					if (!dirFile.exists()) {
						dirFile.mkdirs();
					}*/
                    String dirFile = Environment.getExternalStorageDirectory().toString();
                    File originalFile = new File(dirFile, "original.jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            originalFile);
                    bit.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
                    File jpegTrueFile = new File(dirFile, "jpegtrue.jpg");
                    File jpegFalseFile = new File(dirFile, "jpegfalse.jpg");
                    NativeUtil.compressBitmap(bit, quality,
                            jpegTrueFile.getAbsolutePath(), true);

                    NativeUtil.compressBitmap(bit, quality,
                            jpegFalseFile.getAbsolutePath(), false);
//					Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
    }


    //--------------使用Universal-Image-Loader框架加载图片
    private void initOptionUIL() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)            //加载图片时的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.ic_launcher)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                        //   .displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形
                .build();
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
            tv_phonenub.setText(number + "");
        }
    }

    @Override
    public void onDisMiss() {

    }

}
