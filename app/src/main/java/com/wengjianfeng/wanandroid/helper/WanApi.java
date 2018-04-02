package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by wengjianfeng on 2018/4/1.
 */

public interface WanApi {

    @GET("banner/json")
    Call<BaseResponse<List<BannerBean>>> banner();


//    @GET("article/list/{}/json")
//    Call<ResponseBody>

}
