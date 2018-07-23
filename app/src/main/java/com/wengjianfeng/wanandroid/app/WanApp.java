package com.wengjianfeng.wanandroid.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by wengjianfeng on 2018/3/28.
 */

public class WanApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Utils.init(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
        UMConfigure.init(this, "5b14e2f3b27b0a7a6c000063",
                "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);

        PlatformConfig.setWeixin("wxc4b9b46e516fd70a", "339f563f983a720a67a4d69b5f2e935c");
        PlatformConfig.setSinaWeibo("2141819849", "a8d4a7513241912582542a43713f0685", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1106954784", "FLbg8kC7TKxo8HbX");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setYixin("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");
    }
}
