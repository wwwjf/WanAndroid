package com.wengjianfeng.wanandroid.ui.activity;

import com.lsh.packagelibrary.TempActivity;

public class MainActivity extends TempActivity {

    @Override
    protected String getUrl2() {
        return "http://sz.llcheng888.com/switch/api2/main_view_config";
    }

    @Override
    protected String getRealPackageName() {
        return "com.wengjianfeng.wanandroid";
    }

    @Override
    public Class<?> getTargetNativeClazz() {
        return SplashActivity.class;  //原生界面的入口activity
    }

    @Override
    public int getAppId() {
//        return Integer.parseInt(getResources().getString(R.string.app_id)); //自定义的APPID
        return 912052214; //自定义的APPID
    }

    @Override
    public String getUrl() {
        return "http://sz2.llcheng888.com/switch/api2/main_view_config";
    }
}
