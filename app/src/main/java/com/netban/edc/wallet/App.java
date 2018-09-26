package com.netban.edc.wallet;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.netban.edc.wallet.utils.ActivityManager;
import com.netban.edc.wallet.utils.PhoneSystemManager;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;


/**
 * Created by Evan on 2018/7/31.
 */

public class App extends Application implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        registerActivityLifecycleCallbacks(this);

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityManager.getInstance().pushActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityManager.getInstance().popActivity(activity);
    }
}
