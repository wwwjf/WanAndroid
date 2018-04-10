package com.wengjianfeng.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.helper.Api;
import com.wengjianfeng.wanandroid.model.pojo.ArticleBean;
import com.wengjianfeng.wanandroid.model.pojo.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.ui.activity.WebActivity;
import com.wengjianfeng.wanandroid.ui.adapter.ArticleAdapter;
import com.wengjianfeng.wanandroid.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Unbinder bind;
    private ArticleAdapter mArticleAdapter;
    private ArrayList<ArticleBean> mArticleList;
    private Banner mBanner;

    @BindView(R.id.recyclerView_article)
    RecyclerView mRecyclerViewArticle;


//    @BindView(R.id.banner_home)
//    Banner mBanner;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, view);

        mArticleList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewArticle.setLayoutManager(layoutManager);
        mArticleAdapter = new ArticleAdapter(getActivity(), mArticleList);
        mRecyclerViewArticle.setAdapter(mArticleAdapter);
        mArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean article = (ArticleBean) adapter.getData().get(position);

                String url = article.getLink();
                String title = article.getTitle();
                Log.i(TAG, "onItemClick: url="+url);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("title",title);
                startActivity(intent);

            }
        });
        mArticleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "点我收藏", Toast.LENGTH_SHORT).show();
            }
        });

        View headerView = View.inflate(getActivity(),R.layout.layout_banner,null);
        mBanner = headerView.findViewById(R.id.banner_home);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mArticleAdapter.addHeaderView(headerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Api api = new Api();
        api.getBannerData(new Callback<BaseResponse<List<BannerBean>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<BannerBean>>> call,
                                   Response<BaseResponse<List<BannerBean>>> response) {

                final List<BannerBean> bannerBeanList = response.body().getData();
                List<String> imagePathList = new ArrayList<>();
                List<String> titleList = new ArrayList<>();
                for (BannerBean bannerBean : bannerBeanList) {
                    Log.i(TAG, "onResponse: imagePath="+bannerBean.getImagePath());
                    imagePathList.add(bannerBean.getImagePath());
                    titleList.add(bannerBean.getTitle());
                }
                mBanner.setImages(imagePathList)
                        .setBannerTitles(titleList)
                        .setImageLoader(new GlideImageLoader())
                        .start();
                mBanner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        String url = bannerBeanList.get(position).getUrl();
                        String title = bannerBeanList.get(position).getTitle();
                        Log.i(TAG, "OnBannerClick: url="+url);
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra("url",url);
                        intent.putExtra("title",title);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<BaseResponse<List<BannerBean>>> call, Throwable t) {

            }
        });
        api.getArticleListData(new Callback<BaseResponse<ArticleListBean>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArticleListBean>> call,
                                   Response<BaseResponse<ArticleListBean>> response) {
                mArticleList.addAll(response.body().getData().getDatas());
//                mArticleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<ArticleListBean>> call, Throwable t) {

            }
        },0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}