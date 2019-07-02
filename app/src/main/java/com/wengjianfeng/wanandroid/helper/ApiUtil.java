package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.pojo.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.HotWordBean;
import com.wengjianfeng.wanandroid.model.pojo.UserBean;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;
import com.wengjianfeng.wanandroid.model.splash.PictureBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by wengjianfeng on 2018/3/28.
 */

public class ApiUtil {

    private static WanApiService wanApiService = RetrofitClient.getInstance().create(WanApiService.class);

    /**
     * 首页banner数据
     *
     * @param callback
     */
    public static void getBannerData(Callback<BaseResponse<List<BannerBean>>> callback) {
        Call<BaseResponse<List<BannerBean>>> call = wanApiService.banner();
        call.enqueue(callback);

    }

    /**
     * 首页文章列表数据
     *
     * @param callback
     * @param page     页数
     */
    public static void getArticleListData(Callback<BaseResponse<ArticleListBean>> callback,
                                          int page) {

        Call<BaseResponse<ArticleListBean>> call = wanApiService.articleList(page);
        call.enqueue(callback);
    }


    /**
     * 知识体系数据
     *
     * @param callback
     */
    public static void getChapterListData(Callback<BaseResponse<List<ChapterBean>>> callback) {
        Call<BaseResponse<List<ChapterBean>>> call = wanApiService.chapterList();
        call.enqueue(callback);
    }

    /**
     * 搜索热词
     *
     * @param callback
     */
    public static void getHotWordListData(Callback<BaseResponse<List<HotWordBean>>> callback) {

        Call<BaseResponse<List<HotWordBean>>> call = wanApiService.hotWordList();
        call.enqueue(callback);
    }

    /**
     * 常用网站
     * @param callback
     */
    public static void getFriendWebData(Callback<BaseResponse<List<HotWordBean>>> callback){

        Call<BaseResponse<List<HotWordBean>>> call = wanApiService.friendWebList();
        call.enqueue(callback);
    }

    /**
     * 搜索文章
     *
     * @param callback
     * @param page
     * @param keyWord
     */
    public static void getArticleSearchListData(Callback<BaseResponse<ArticleListBean>> callback,
                                                int page, String keyWord) {
        Call<BaseResponse<ArticleListBean>> call = wanApiService.searchArticleList(page, keyWord);
        call.enqueue(callback);
    }

    /**
     * 登录
     * @param callback
     * @param userName
     * @param password
     */
    public static void requestLogin(Callback<BaseResponse<UserBean>> callback,String userName,String password){

        Map<String,Object> params = new HashMap<>();
        params.put("username",userName);
        params.put("password",password);
        Call<BaseResponse<UserBean>> call = wanApiService.requestLogin(params);
//        Call<BaseResponse<UserBean>> call = wanApiService.requestLogin(userName,password);
        call.enqueue(callback);

    }

    /**
     * 注册
     * @param callback
     * @param userName
     * @param password
     * @param rePassword
     */
    public static void requestRegister(Callback<BaseResponse<UserBean>> callback,
                                       String userName,String password,String rePassword){
        Call<BaseResponse<UserBean>> call = wanApiService.requestRegister(userName,password,rePassword);
        call.enqueue(callback);
    }

    /**
     * 知识体系分类的文章
     * @param callback
     * @param cid
     */
    public static void getChapterArticleListData(Callback<BaseResponse<ArticleListBean>> callback,
                                                 String cid){
        Call<BaseResponse<ArticleListBean>> call = wanApiService.chapterArticleList(cid);
        call.enqueue(callback);
    }

    /**
     * 收藏的文章
     * @param callback
     * @param page
     */
    public static void getCollectionArticleListData(Callback<BaseResponse<ArticleListBean>> callback,
                                                    int page){
        Call<BaseResponse<ArticleListBean>> call = wanApiService.collectionArticleList(page);
        call.enqueue(callback);
    }

    /**
     * 收藏文章
     * @param id 文章id
     */
    public static void collectArticleData(Callback<BaseResponse<ArticleListBean>> callback,
                                          int id) {
        Call<BaseResponse<ArticleListBean>> call = wanApiService.collectWanArticle(id);
        call.enqueue(callback);

    }

    /**
     * 取消文章收藏
     * @param callback
     * @param id
     */
    public static void unCollectArticleData(Callback<BaseResponse<ArticleListBean>> callback,
                                            int id){
        Call<BaseResponse<ArticleListBean>> call = wanApiService.unCollectWanArticle(id);
        call.enqueue(callback);
    }

    public static void getSplash(Callback<PictureBean> callback) {
        Call<PictureBean> call = wanApiService.getSplash();
        call.enqueue(callback);
    }

    public static void getProjectList(Callback<BaseResponse<List<ChapterBean>>> callback){
        Call<BaseResponse<List<ChapterBean>>> call = wanApiService.getProjectList();
        call.enqueue(callback);
    }
}
