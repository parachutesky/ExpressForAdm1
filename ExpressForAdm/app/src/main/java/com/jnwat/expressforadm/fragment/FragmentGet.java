package com.jnwat.expressforadm.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.adpter.HadGetExpressAdapter;
import com.jnwat.expressforadm.dialog.PopQRDialog;
import com.jnwat.expressforadm.expressdetils.ExpressdetilsActivity;
import com.jnwat.expressforadm.interfaceImp.ListItemClickHelp;
import com.jnwat.expressforadm.tools.ToastViewTools;

import java.util.List;

/**
 * Created by chang-zhiyuan on 2016/3/2.
 * 已收快递列表
 */

public class FragmentGet extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment implements ListItemClickHelp {

    private List<String> mObjects;
    private ArrayAdapter<String> mAdapter;
    private PopQRDialog mPopQRDialog ;

    //自定义适配器
    private HadGetExpressAdapter mHadGetExpressAdapter;

    @Override
    public ListAdapter getListAdapter() {
        mPopQRDialog = new PopQRDialog();
        mHadGetExpressAdapter = new HadGetExpressAdapter(getActivity(), null,this);
        return mHadGetExpressAdapter;


        /*
        mObjects = new ArrayList<>(Arrays.asList(
                getString(R.string.title_item1),
                getString(R.string.title_item2),
                getString(R.string.title_item3),
                getString(R.string.title_item1),
                getString(R.string.title_item2),
                getString(R.string.title_item3)
        ));
        return (mAdapter = new ArrayAdapter<>(getActivity(), R.layout.listview_row, mObjects));
        */
    }

    @Override
    public boolean useCustomContentView() {
        return false;
    }

    @Override
    public int getCustomContentView() {
        return 0;
    }

    @Override
    public boolean pullToRefreshEnabled() {
        return true;
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
                //  mObjects.add("Item " + (mObjects.size() + 1));
                mHadGetExpressAdapter.notifyDataSetChanged();
                setRefreshing(false);
            }

        }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
/*        Intent intent = new Intent();
        intent.setClass(getActivity(), ExpressdetilsActivity.class);
        startActivity(intent);*/
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        return false;
    }

    @Override
    public void onClick(View item, View widget, int position, int which) {
        switch (which) {
            //点击后显示二维码
            case R.id.lin_showerweima:
                ToastViewTools.initToast(getActivity(), "点击位置" + position);
                mPopQRDialog.showQrDialog(getActivity(),"显示的内容");
                break;
            case R.id.lin_intent1:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ExpressdetilsActivity.class);
                startActivity(intent);
                ToastViewTools.initToast(getActivity(),"点击位置"+position);
                break;
            default:
                break;
        }
    }
}
