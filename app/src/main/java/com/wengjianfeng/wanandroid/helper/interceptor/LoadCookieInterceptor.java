package com.wengjianfeng.wanandroid.helper.interceptor;

import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.wengjianfeng.wanandroid.app.WanConstants;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wengjianfeng on 2018/4/22.
 */

public class LoadCookieInterceptor implements Interceptor {
    public static final String TAG = LoadCookieInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Set<String> cookieSet = SPUtils.getInstance(WanConstants.SP_USERINFO)
                .getStringSet(WanConstants.COOKIE);
        String path = builder.build().url().encodedPath();
        Log.e(TAG, "intercept: LoadCookieInterceptor path=" + path);
        if (!cookieSet.isEmpty()) {
            Iterator<String> iterator = cookieSet.iterator();
            while (iterator.hasNext()) {
                String cookie = iterator.next();
                Log.e(TAG, "intercept: cookie=" + cookie);
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}
