package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.pojo.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wengjianfeng on 2018/4/1.
 */

public interface WanApi {

    @GET("banner/json")
    Call<BaseResponse<List<BannerBean>>> banner();


    @GET("article/list/{page}/json")
    Call<BaseResponse<ArticleListBean>> articleList(@Path("page") int page);

}
