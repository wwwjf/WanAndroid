package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.app.WanConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wengjianfeng on 2018/4/1.
 */

public class HttpHelper {
    private static HttpHelper httpHelper;
    private Retrofit retrofit;

    private HttpHelper() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(WanConstants.REQUEST_HTTP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static HttpHelper getInstance() {
        if (httpHelper == null) {
            httpHelper = new HttpHelper();
        }
        return httpHelper;
    }

    public <T> T create(Class<T> service) {

        return retrofit.create(service);
    }

}
