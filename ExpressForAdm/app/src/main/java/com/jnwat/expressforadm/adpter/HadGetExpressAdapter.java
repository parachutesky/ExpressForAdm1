package com.jnwat.expressforadm.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.interfaceImp.ListItemClickHelp;
import com.lidroid.xutils.util.LogUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 已取快递适配器
 * Created by chang-zhiyuan on 2016/3/3.
 */
public class HadGetExpressAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<HashMap<String, String>> data;
    private ListItemClickHelp callback;

    public HadGetExpressAdapter(Context mcontext,
                                List<HashMap<String, String>> mdata, ListItemClickHelp callback) {
        data = mdata;
        this.layoutInflater = LayoutInflater.from(mcontext);
        this.callback = callback;
        LogUtils.d("初始化课程表适配器");
    }

    @Override
    public int getCount() {
        //    return data.size();
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        ViewHolder mviewHolder = null;
   /*     Log.e("getView=====>>>>>>>>>", "开始时间===>>>>>>"
                + data.get(position).get("Startdate"));*/
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_item_info,
                    parent, false);
            mviewHolder = new ViewHolder();
            mviewHolder.lin_showerweima = (LinearLayout) convertView.findViewById(R.id.lin_showerweima);
            mviewHolder.lin_intent1 = (LinearLayout) convertView.findViewById(R.id.lin_intent1);
           /* mviewHolder.tv_sw_allproject_title = (TextView) convertView
                    .findViewById(R.id.tv_sw_allproject_title);
            mviewHolder.tv_sw_allproject_qisu = (TextView) convertView
                    .findViewById(R.id.tv_sw_allproject_qisu);
            mviewHolder.tv_sw_allproject_type = (TextView) convertView
                    .findViewById(R.id.tv_sw_allproject_type);
            mviewHolder.tv_sw_allproject_nub = (TextView) convertView
                    .findViewById(R.id.tv_sw_allproject_nub);
            mviewHolder.tv_sw_allproject_time = (TextView) convertView
                    .findViewById(R.id.tv_sw_allproject_time);*/
            convertView.setTag(mviewHolder);
        } else {
            mviewHolder = (ViewHolder) convertView.getTag();
        }

        final View view = convertView;
        final int p = position;
        final int one = mviewHolder.lin_showerweima.getId();
        mviewHolder.lin_showerweima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(view, parent, p, one);
            }
        });
        final int two = mviewHolder.lin_intent1.getId();
        mviewHolder.lin_intent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(view, parent, p, two);
            }
        });
        /*
        mviewHolder.tv_sw_allproject_qisu.setText("期数 : "
                + data.get(position).get("Code"));
        mviewHolder.tv_sw_allproject_type.setText("类型 : "
                + data.get(position).get("Traintype"));
        mviewHolder.tv_sw_allproject_time.setText("时间 : "
                + data.get(position).get("Startdate") + "至"
                + data.get(position).get("Enddate"));
        mviewHolder.tv_sw_allproject_nub.setText("人数 : "
                + data.get(position).get("Pronum"));

        if (data.get(position).get("hasDBC").equals("1")) {
            mviewHolder.tv_sw_allproject_title
                    .setTextColor(android.graphics.Color.RED);
            mviewHolder.tv_sw_allproject_title.setText("(搭)"
                    + data.get(position).get("Projectname"));
        } else {
            mviewHolder.tv_sw_allproject_title
                    .setTextColor(android.graphics.Color.BLACK);
            mviewHolder.tv_sw_allproject_title.setText(data.get(position).get(
                    "Projectname"));
        }*/

        return convertView;
    }

    class ViewHolder {
        //显示二维码
        public LinearLayout lin_showerweima;
        public LinearLayout lin_intent1;
        /*
        public TextView tv_sw_allproject_title;
        public TextView tv_sw_allproject_qisu;
        public TextView tv_sw_allproject_type;
        public TextView tv_sw_allproject_time;
        public TextView tv_sw_allproject_nub;*/

    }
}
