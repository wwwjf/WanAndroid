package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by wengjianfeng on 2018/3/28.
 */

public class ApiBanner {

    private WanApi wanApi;

    public ApiBanner() {
        wanApi = HttpHelper.getInstance().create(WanApi.class);
    }

    public void getBannerData(Callback<BaseResponse<List<BannerBean>>> callback) {
        Call<BaseResponse<List<BannerBean>>> call = wanApi.banner();
        call.enqueue(callback);

    }


}
