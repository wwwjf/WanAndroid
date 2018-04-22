package com.wengjianfeng.wanandroid.helper.interceptor;

import android.util.ArraySet;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.wengjianfeng.wanandroid.app.WanConstants;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wengjianfeng on 2018/4/22.
 */

public class SaveCookieInterceptor implements Interceptor {
    public static final String TAG = SaveCookieInterceptor.class.getSimpleName();
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String path = request.url().encodedPath();
        Log.e(TAG, "intercept: SaveCookieInterceptor path="+path);
        //根据response保存cookie
        List<String> headerCookieList = response.headers("Set-Cookie");
        if (!headerCookieList.isEmpty()){
            for (String cookie : headerCookieList) {
            Log.e(TAG, "intercept: Set-Cookie="+ cookie);
            }
            SPUtils.getInstance(WanConstants.SP_USERINFO)
                    .put(WanConstants.COOKIE,new HashSet<>(headerCookieList));
        }
        return response;
    }
}
