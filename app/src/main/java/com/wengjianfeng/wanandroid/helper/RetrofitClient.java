package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.app.WanURL;
import com.wengjianfeng.wanandroid.helper.interceptor.LoadCookieInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wengjianfeng on 2018/4/1.
 */

public class RetrofitClient {
    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    private RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoadCookieInterceptor())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(WanURL.REQUEST_HTTP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public <T> T create(Class<T> service) {

        return retrofit.create(service);
    }

}
