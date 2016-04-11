package com.jnwat.expressforadm.main;

import android.app.Notification;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.blunderer.materialdesignlibrary.handlers.ActionBarHandler;
import com.blunderer.materialdesignlibrary.handlers.ActionBarSearchHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerAccountsHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerAccountsMenuHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerBottomHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerStyleHandler;
import com.blunderer.materialdesignlibrary.handlers.NavigationDrawerTopHandler;
import com.blunderer.materialdesignlibrary.listeners.OnSearchListener;
import com.blunderer.materialdesignlibrary.models.Account;
import com.blunderer.materialdesignlibrary.views.ToolbarSearch;
import com.jnwat.expressforadm.R;
import com.jnwat.expressforadm.about.FragmentAbout;
import com.jnwat.expressforadm.push.PushFragment;
import com.jnwat.expressforadm.sarchresult.SearchResultActivity;
import com.jnwat.expressforadm.set.SettingFragment;
import com.jnwat.expressforadm.tools.ModifySysTitle;

public class MainActivity extends com.blunderer.materialdesignlibrary.activities.NavigationDrawerActivity {


    @Override
    public NavigationDrawerStyleHandler getNavigationDrawerStyleHandler() {
        return null;
    }

    @Override
    public NavigationDrawerAccountsHandler getNavigationDrawerAccountsHandler() {
        return new NavigationDrawerAccountsHandler(this)
                .enableSmallAccountsLayout()
                .addAccount("王晓天", "15662799889",
                        R.drawable.ic_launcher, R.drawable.profile1_background);
    }

    @Override
    public NavigationDrawerAccountsMenuHandler getNavigationDrawerAccountsMenuHandler() {
        return null;/* new NavigationDrawerAccountsMenuHandler(this)
                .addAddAccount(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                    }

                })
                .addManageAccounts(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    }

                });*/
    }

    @Override
    public void onNavigationDrawerAccountChange(Account account) {
        System.out.println(account.getTitle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources resource = this.getResources();
        String pkgName = this.getPackageName();
        ModifySysTitle.ModifySysTitleStyle(R.color.color_primary_dark,
                MainActivity.this);

        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                "ovSqYt0M3ZliMKyrb24ZuN5K");

        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(
                resource.getIdentifier(
                        "notification_custom_builder", "layout", pkgName),
                resource.getIdentifier("notification_icon", "id", pkgName),
                resource.getIdentifier("notification_title", "id", pkgName),
                resource.getIdentifier("notification_text", "id", pkgName));
        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(resource.getIdentifier(
                "simple_notification_icon", "drawable", pkgName));
        cBuilder.setNotificationSound(Uri.withAppendedPath(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
        // 推送高级设置，通知栏样式设置为下面的ID
        PushManager.setNotificationBuilder(this, 1, cBuilder);
    }

    @Override
    public NavigationDrawerTopHandler getNavigationDrawerTopHandler() {
        return new NavigationDrawerTopHandler(this)
                //  .addSection(R.string.fragment)
                .addItem(R.string.fragment_viewpager_with_tabs, R.drawable.icon_nav_home, new MainFragment())
                .addItem(R.string.fragment_viewpager_with_tabs_set, R.drawable.icon_nav_setting, new SettingFragment())
                .addItem(R.string.fragment_viewpager_with_tabs_push, R.drawable.icon_nav_news, new PushFragment())
                        //.addItem(R.string.fragment_viewpager_with_tabs_exit, R.drawable.ic_launcher, new ())
                .addItem(R.string.fragment_viewpager_with_tabs_about, R.drawable.icon_nav_about, new FragmentAbout());
                        /*.addItem(R.string.start_activity, R.drawable.abc_btn_check_to_on_mtrl_000,
                                new Intent(getApplicationContext(), MainActivity.class));*/
    }

    @Override
    public NavigationDrawerBottomHandler getNavigationDrawerBottomHandler() {
        return new NavigationDrawerBottomHandler(this)
                .addSettings(null)
                .addHelpAndFeedback(null);
    }

    @Override
    public boolean overlayActionBar() {
        return false;
    }

    @Override
    public boolean replaceActionBarTitleByNavigationDrawerItemTitle() {
        return true;
    }

    @Override
    public int defaultNavigationDrawerItemSelectedPosition() {
        return 0;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarSearchHandler(this, new OnSearchListener() {

            @Override
            public void onSearched(String text) {
                if (!text.toString().trim().equals("")) {

                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, SearchResultActivity.class);
                    intent.putExtra("search", "" + text);

                    startActivity(intent);
                }
                Toast.makeText(getApplicationContext(),
                        "Searching \"" + text + "\"", Toast.LENGTH_SHORT).show();
            }

        })
                .enableAutoCompletion()
                        //  .setAutoCompletionItems(mItems)
                .setAutoCompletionMode(ToolbarSearch.AutoCompletionMode.CONTAINS);
    }


}