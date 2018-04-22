package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.pojo.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.HotWordBean;
import com.wengjianfeng.wanandroid.model.pojo.UserBean;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;

import java.util.List;

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
    public static void getArticleListData(Callback<BaseResponse<ArticleListBean>> callback, int page) {

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

    public static void requestLogin(Callback<BaseResponse<UserBean>> callback,String userName,String password){
        Call<BaseResponse<UserBean>> call = wanApiService.requestLogin(userName,password);
        call.enqueue(callback);

    }
}
