package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.pojo.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by wengjianfeng on 2018/3/28.
 */

public class Api {

    private WanApi wanApi;

    public Api() {
        wanApi = HttpHelper.getInstance().create(WanApi.class);
    }

    /**
     * 首页banner数据
     * @param callback
     */
    public void getBannerData(Callback<BaseResponse<List<BannerBean>>> callback) {
        Call<BaseResponse<List<BannerBean>>> call = wanApi.banner();
        call.enqueue(callback);

    }

    /**
     * 首页文章列表数据
     * @param callback
     * @param page 页数
     */
    public void getArticleListData(Callback<BaseResponse<ArticleListBean>> callback,int page) {

        Call<BaseResponse<ArticleListBean>> call = wanApi.articleList(page);
        call.enqueue(callback);
    }


    /**
     * 知识体系数据
     * @param callback
     */
    public void getChapterListData(Callback<BaseResponse<List<ChapterBean>>> callback){
        Call<BaseResponse<List<ChapterBean>>> call = wanApi.chapterList();
        call.enqueue(callback);
    }


}
