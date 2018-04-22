package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.pojo.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.HotWordBean;
import com.wengjianfeng.wanandroid.model.pojo.UserBean;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by wengjianfeng on 2018/4/1.
 */

public interface WanApiService {

    @GET("banner/json")
    Call<BaseResponse<List<BannerBean>>> banner();


    @GET("article/list/{page}/json")
    Call<BaseResponse<ArticleListBean>> articleList(@Path("page") int page);

    @GET("tree/json")
    Call<BaseResponse<List<ChapterBean>>> chapterList();

    @GET("hotkey/json")
    Call<BaseResponse<List<HotWordBean>>> hotWordList();

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Call<BaseResponse<ArticleListBean>> searchArticleList(@Path("page") int page, @Field("k") String keyWord);

    @POST("user/login")
    @FormUrlEncoded
    Call<BaseResponse<UserBean>> requestLogin(@Field("username") String userName, @Field("password") String password);
}
