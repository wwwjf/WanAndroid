package com.wengjianfeng.wanandroid.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.ArticleBean;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.ui.adapter.ArticleAdapter;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 知识体系下分类文章
 */
public class ChapterDetailFragment extends Fragment
        implements BaseQuickAdapter.RequestLoadMoreListener{
    private static final String TAG = ChapterDetailFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View rootView;
    private Unbinder bind;
    //当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
    private boolean isFragmentVisible;
    //是否是第一次开启网络加载
    public boolean isFirst;

    private ArrayList<ArticleBean> mArticleList;
    private ArticleAdapter mArticleAdapter;

    @BindView(R.id.ptrClassicFrameLayout_chapterArticle)
    PtrClassicFrameLayout mPtrChapterArticle;

    @BindView(R.id.recyclerView_chapterArticle)
    RecyclerView mRecyclerViewChapterArticle;

    public ChapterDetailFragment() {
    }


    public static ChapterDetailFragment newInstance(String param1, String param2) {
        ChapterDetailFragment fragment = new ChapterDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_chapter_detail, container, false);
        bind = ButterKnife.bind(this,rootView);

        //可见，但是并没有加载过
        if (isFragmentVisible && !isFirst) {
            onFragmentVisibleChange(true);
        }

        mArticleList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewChapterArticle.setLayoutManager(layoutManager);
        mArticleAdapter = new ArticleAdapter(getActivity(), mArticleList);
        mArticleAdapter.setEnableLoadMore(true);
        mArticleAdapter.setOnLoadMoreListener(this,mRecyclerViewChapterArticle);
        mRecyclerViewChapterArticle.setAdapter(mArticleAdapter);
        mArticleAdapter.setHeaderFooterEmpty(true,true);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mArticleAdapter.setNewData(mArticleList);
        mArticleAdapter.setEmptyView(R.layout.view_load_empty);
        mPtrChapterArticle.setLastUpdateTimeRelateObject(this);
        mPtrChapterArticle.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        initData();

    }

    private void initData() {

        ApiUtil.getChapterArticleListData(new Callback<BaseResponse<ArticleListBean>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArticleListBean>> call,
                                   Response<BaseResponse<ArticleListBean>> response) {
                mPtrChapterArticle.refreshComplete();
                mArticleList.clear();
                mArticleList.addAll(response.body().getData().getDatas());
                mArticleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<ArticleListBean>> call, Throwable t) {
                mPtrChapterArticle.refreshComplete();
            }
        },mParam2);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isFragmentVisible = true;
        }
        if (rootView == null) {
            return;
        }
        //可见，并且没有加载过
        if (!isFirst&&isFragmentVisible) {
            onFragmentVisibleChange(true);
            return;
        }
        //由可见——>不可见 已经加载过
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    public void onLoadMoreRequested() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mArticleAdapter.loadMoreEnd(true);
                mArticleAdapter.loadMoreComplete();
            }
        },2000);

    }
}
