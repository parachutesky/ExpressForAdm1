package com.jnwat.expressforadm.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.bean.ExpressInfoBean;
import com.jnwat.expressforadm.interfaceImp.ListItemClickHelp;

import java.util.List;

/**
 * 查询历史 适配器
 * Created by chang-zhiyuan on 2016/3/3.
 */
public class ShelveHistoryAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<ExpressInfoBean> data;
    private ListItemClickHelp callback;

    public ShelveHistoryAdapter(Context mcontext,
                                List<ExpressInfoBean> mdata, ListItemClickHelp callback) {
        data = mdata;
        this.callback = callback;
        this.layoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
            return data.size();
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
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_item_common,
                    parent, false);
            mviewHolder = new ViewHolder();
            mviewHolder.tv_expresstype = (TextView) convertView
                    .findViewById(R.id.tv_expresstype);
            mviewHolder.tv_express_savetime = (TextView) convertView
                    .findViewById(R.id.tv_express_savetime);

           /*
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

        mviewHolder.tv_expresstype.setText(data.get(position).getExpress_type() + "  " + data.get(position).getExpress_id());
        mviewHolder.tv_express_savetime.setText( data.get(position).getExpress_handtime());
        /*
        mviewHolder.tv_sw_allproject_qisu.setText("期数 : "
                + data.get(position).get("Code"));
        mviewHolder.tv_sw_allproject_qisu.setText("期数 : "
                + data.get(position).get("Code"));
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
        public TextView tv_expresstype;
        public TextView tv_express_savetime;
        /*
        public TextView tv_sw_allproject_qisu;
        public TextView tv_sw_allproject_type;
        public TextView tv_sw_allproject_time;
        public TextView tv_sw_allproject_nub;*/

    }
}
