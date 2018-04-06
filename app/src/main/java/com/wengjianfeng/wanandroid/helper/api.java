package com.wengjianfeng.wanandroid.helper;

import com.wengjianfeng.wanandroid.model.pojo.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by wengjianfeng on 2018/3/28.
 */

public class api {

    private WanApi wanApi;

    public api() {
        wanApi = HttpHelper.getInstance().create(WanApi.class);
    }

    public void getBannerData(Callback<BaseResponse<List<BannerBean>>> callback) {
        Call<BaseResponse<List<BannerBean>>> call = wanApi.banner();
        call.enqueue(callback);

    }

    public void getArticleListData(Callback<BaseResponse<ArticleListBean>> callback,int page) {

        Call<BaseResponse<ArticleListBean>> call = wanApi.articleList(page);
        call.enqueue(callback);
    }


}
