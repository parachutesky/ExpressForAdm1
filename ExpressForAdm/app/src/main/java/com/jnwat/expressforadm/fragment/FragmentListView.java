package com.jnwat.expressforadm.fragment;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.jnwat.expressforadm.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by chang-zhiyuan on 2016/3/2.
 */
public class FragmentListView extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment implements AbsListView.OnScrollListener {

    private List<String> mObjects;
    private ArrayAdapter<String> mAdapter;

    @Override
    public ListAdapter getListAdapter() {
        mObjects = new ArrayList<>(Arrays.asList(
                getString(R.string.title_item1),
                getString(R.string.title_item2),
                getString(R.string.title_item3), getString(R.string.title_item1),
                getString(R.string.title_item2),
                getString(R.string.title_item3), getString(R.string.title_item1),
                getString(R.string.title_item2),
                getString(R.string.title_item3), getString(R.string.title_item1),
                getString(R.string.title_item2),
                getString(R.string.title_item3), getString(R.string.title_item1),
                getString(R.string.title_item2),
                getString(R.string.title_item3), getString(R.string.title_item1),
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
        return new int[]{R.color.color_primary, R.color.color_primary_dark};
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
    public void onStart() {
        super.onStart();
        mListView.setOnScrollListener(this);
        //    mListView.addFooterView(getListViewFooterView());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        System.out.println("点击Item:" + position);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int itemsLastIndex = mAdapter.getCount() - 1;    //数据集最后一项的索引
        int lastIndex = itemsLastIndex + 1;             //加上底部的loadMoreView项
        System.out.println("lastIndex:" + lastIndex);
        if (scrollState == this.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {
            //如果是自动加载,可以在这里放置异步加载数据的代码
            Log.i("LOADMORE", "loading...");
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.visibleItemCount = visibleItemCount;
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
        //   System.out.println("visibleLastIndex:"+visibleLastIndex);

    }
}
