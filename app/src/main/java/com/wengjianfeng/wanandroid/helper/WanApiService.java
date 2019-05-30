package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.BannerBean;
import com.wengjianfeng.wanandroid.model.pojo.ChapterChildrenBean;
import com.wengjianfeng.wanandroid.model.pojo.HotWordBean;
import com.wengjianfeng.wanandroid.model.pojo.UserBean;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;
import com.wengjianfeng.wanandroid.model.splash.PictureBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by wengjianfeng on 2018/4/1.
 */

public interface WanApiService {

    /**
     * banner数据
     * @return
     */
    @GET("banner/json")
    Call<BaseResponse<List<BannerBean>>> banner();


    /**
     * 首页文章列表
     * @param page
     * @return
     */
    @GET("article/list/{page}/json")
    Call<BaseResponse<ArticleListBean>> articleList(@Path("page") int page);

    /**
     * 知识体系
     * @return
     */
    @GET("tree/json")
    Call<BaseResponse<List<ChapterBean>>> chapterList();

    /**
     * 热门搜索词
     * @return
     */
    @GET("hotkey/json")
    Call<BaseResponse<List<HotWordBean>>> hotWordList();


    /**
     * 常用网站
     * @return
     */
    @GET("/friend/json")
    Call<BaseResponse<List<HotWordBean>>> friendWebList();

    /**
     * 搜索文章
     * @param page
     * @param keyWord 搜索关键字
     * @return
     */
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Call<BaseResponse<ArticleListBean>> searchArticleList(@Path("page") int page,
                                                          @Field("k") String keyWord);

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @POST("user/login")
    @FormUrlEncoded
    Call<BaseResponse<UserBean>> requestLogin(@Field("username") String userName,
                                              @Field("password") String password);

    /**
     * 注册
     * @param userName
     * @param password
     * @param rePassword
     * @return
     */
    @POST("/user/register")
    @FormUrlEncoded
    Call<BaseResponse<UserBean>> requestRegister(@Field("username") String userName,
                                                 @Field("password") String password,
                                                 @Field("repassword") String rePassword);

    /**
     * 知识体系分类下的文章列表
     * @param cid 分类ID
     * @return
     */
    @GET("article/list/0/json")
    Call<BaseResponse<ArticleListBean>> chapterArticleList(@Query("cid") String cid);

    /**
     * 收藏的文章列表
     * @param page
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Call<BaseResponse<ArticleListBean>> collectionArticleList(@Path("page") int page);

    /**
     * 收藏文章（站内）
     * @param id 文章ID
     * @return
     */
    @POST("lg/collect/{id}/json")
    Call<BaseResponse<ArticleListBean>> collectWanArticle(@Path("id") int id);


    /**
     * 收藏文章（站外）
     * @param title 标题
     * @param author 作者
     * @param link 链接
     * @return
     */
    @POST("lg/collect/add/json")
    Call<BaseResponse<ArticleListBean>> collectUnWanArticle(@Query("title") String title,
                                                            @Query("author") String author,
                                                            @Query("link") String link);

    /**
     * 取消收藏文章
     * @param id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Call<BaseResponse<ArticleListBean>> unCollectWanArticle(@Path("id") int id);


    /***
     * 启动图
     * @return
     */
    @POST("https://bing.ioliu.cn/v1/?type=json")
    Call<PictureBean> getSplash();

    /**
     * 项目分类
     * @return
     */
    @GET("project/tree/json")
    Call<BaseResponse<List<ChapterBean>>> getProjectList();
}
