package com.jnwat.expressforadm.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.blunderer.materialdesignlibrary.adapters.ViewPagerAdapter;
import com.blunderer.materialdesignlibrary.handlers.ViewPagerHandler;
import com.blunderer.materialdesignlibrary.models.ViewPagerItem;
import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.tools.ModifySysTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chang-zhiyuan on 2016/3/11.
 */
public class FragmentAc_Adm extends FragmentActivity implements com.blunderer.materialdesignlibrary.interfaces.ViewPager {
    FragmentGet mFragmentGet;
    FragmentNoGet mFragmentNoGet;
    private TextView title_bar_name = null;
    private ImageView iv_back = null;
    protected ViewPager mViewPager;
    protected PagerSlidingTabStrip mViewPagerTabs;
    private List<ViewPagerItem> mViewPagerItems;
    private ViewPager.OnPageChangeListener mUserOnPageChangeListener;
    private ViewPagerAdapter mViewPagerAdapter;

    public FragmentAc_Adm() {
        mViewPagerItems = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPagerHandler viewPagerHandler = getViewPagerHandler();
        if (viewPagerHandler == null) viewPagerHandler = new ViewPagerHandler(this);
        mViewPagerItems = viewPagerHandler.getViewPagerItems();
        setContentView(R.layout.adm_fragment_view_pager_with_tabs);


        setTitleColor();
        setBackListener("取件记录");


        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mViewPagerItems);
        mViewPager.setAdapter(mViewPagerAdapter);
        int defaultViewPagerPageSelectedPosition = defaultViewPagerPageSelectedPosition();
        if (defaultViewPagerPageSelectedPosition >= 0 &&
                defaultViewPagerPageSelectedPosition < mViewPagerItems.size()) {
            selectPage(defaultViewPagerPageSelectedPosition);
        }

        mViewPagerTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        if (!mViewPagerItems.isEmpty()) showTabs(mViewPager);

    }

    protected void setTitleColor() {
        ModifySysTitle.ModifySysTitleStyle(com.jnwat.expressforadm.R.color.color_primary_dark,
                this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mViewPagerItems != null && mViewPagerItems.size() > 0 && mViewPager != null) {
            int tabPosition = mViewPager.getCurrentItem();
            if (tabPosition >= 0 && tabPosition < mViewPagerItems.size()) {
                mViewPagerItems.get(tabPosition).getFragment()
                        .onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    public ViewPagerHandler getViewPagerHandler() {


        if (null == mFragmentGet) {
            mFragmentGet = new FragmentGet();
        }
        if (null == mFragmentNoGet) {
            mFragmentNoGet = new FragmentNoGet();
        }


        return new ViewPagerHandler(this)
                .addPage(com.jnwat.expressforadm.R.string.title_noget,
                        //   MainFragment.newInstance("Material Design Fragment ViewPager with Tabs"));
                        mFragmentNoGet)
                .addPage(com.jnwat.expressforadm.R.string.title_hadget,
                        //    MainFragment.newInstance("Material Design Fragment ViewPager with Tabs"))
                        mFragmentGet);

    }

    @Override
    public void selectPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener userOnPageChangeListener) {
        mUserOnPageChangeListener = userOnPageChangeListener;
    }

    @Override
    public void updateNavigationDrawerTopHandler(ViewPagerHandler viewPagerHandler,
                                                 int defaultViewPagerPageSelectedPosition) {
        if (viewPagerHandler == null) viewPagerHandler = new ViewPagerHandler(this);
        mViewPagerItems.clear();
        mViewPagerItems.addAll(viewPagerHandler.getViewPagerItems());
        mViewPagerAdapter.notifyDataSetChanged();

        selectPage(defaultViewPagerPageSelectedPosition);

        if (!mViewPagerItems.isEmpty()) showTabs(mViewPager);
    }

    @Override
    public int defaultViewPagerPageSelectedPosition() {
        return 0;
    }

    private void showTabs(ViewPager pager) {
        mViewPagerTabs.setTextColor(getResources().getColor(android.R.color.white));
        mViewPagerTabs.setShouldExpand(true);
        mViewPagerTabs.setOnPageChangeListener(mUserOnPageChangeListener);
        mViewPagerTabs.setViewPager(pager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mViewPagerTabs.setTabBackground(android.R.attr.selectableItemBackground);
        }
    }

    /**
     * 设置返回Title的返回监听
     *
     * @param title
     */
    public void setBackListener(String title) {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        title_bar_name = (TextView) findViewById(R.id.title_bar_name);
        title_bar_name.setText(title);

    }

}
