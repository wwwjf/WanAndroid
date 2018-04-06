package com.wengjianfeng.wanandroid.app;

import android.app.Application;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by wengjianfeng on 2018/3/28.
 */

public class WanApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        initAutoLayout();
    }

    private void initAutoLayout() {

    }
}
