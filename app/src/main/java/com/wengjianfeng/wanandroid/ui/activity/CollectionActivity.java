package com.wengjianfeng.wanandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.base.BaseActivity;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.ArticleBean;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.ui.adapter.ArticleAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionActivity extends BaseActivity {
    public static final String TAG = CollectionActivity.class.getSimpleName();

    @BindView(R.id.toolbar_collection)
    Toolbar mToolbarCollection;

    @BindView(R.id.ptrClassicFrameLayout_collection)
    PtrClassicFrameLayout mPtrClassicFrameLayoutCollection;

    @BindView(R.id.recyclerView_collection)
    RecyclerView mRecyclerViewCollection;

    private ArrayList<ArticleBean> mArticleList;
    private ArticleAdapter mArticleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerViewCollection.setLayoutManager(manager);
        mArticleList = new ArrayList<>();
        mArticleAdapter = new ArticleAdapter(this,mArticleList);
        mRecyclerViewCollection.setAdapter(mArticleAdapter);
        mArticleAdapter.bindToRecyclerView(mRecyclerViewCollection);
        mArticleAdapter.setHeaderFooterEmpty(true, true);
        mArticleAdapter.setEmptyView(R.layout.view_load_empty);
        mPtrClassicFrameLayoutCollection.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayoutCollection.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
            }
        });
        initData();
    }

    private void initData() {
        mArticleList.clear();
        ApiUtil.getCollectionArticleListData(new Callback<BaseResponse<ArticleListBean>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArticleListBean>> call, Response<BaseResponse<ArticleListBean>> response) {
                mPtrClassicFrameLayoutCollection.refreshComplete();
                mArticleList.addAll(response.body().getData().getDatas());
                mArticleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<ArticleListBean>> call, Throwable t) {

            }
        },0);
    }
}
