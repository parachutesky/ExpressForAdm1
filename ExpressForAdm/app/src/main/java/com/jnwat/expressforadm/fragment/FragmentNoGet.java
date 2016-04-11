package com.jnwat.expressforadm.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.login.AuthenticatorActivity;
import com.jnwat.expressforadm.login.RegistauthActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chang-zhiyuan on 2016/3/2.
 * 未收快递列表
 */
public class FragmentNoGet extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<String> mObjects;
    private ArrayAdapter<String> mAdapter;

    @Override
    public ListAdapter getListAdapter() {
      //  setRefreshing(true);

        mObjects = new ArrayList<>(Arrays.asList("登录页面","注册页面",
                getString(R.string.title_item1),
                getString(R.string.title_item2),
                getString(R.string.title_item3)
        ));
        return (mAdapter = new ArrayAdapter<>(getActivity(), R.layout.listview_row, mObjects));
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
                mObjects.add("Item " + (mObjects.size() + 1));
                mAdapter.notifyDataSetChanged();
                setRefreshing(false);
            }

        }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(position ==0){
            Intent intent  = new Intent();
            intent.setClass(getActivity(), AuthenticatorActivity.class);
            startActivity(intent);
        }
        if(position ==1){
            Intent intent  = new Intent();
            intent.setClass(getActivity(), RegistauthActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        return false;
    }

}
