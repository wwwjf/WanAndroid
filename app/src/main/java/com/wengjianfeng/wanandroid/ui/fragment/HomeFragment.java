package com.wengjianfeng.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.helper.ApiBanner;
import com.wengjianfeng.wanandroid.model.BannerBean;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.ui.adapter.HomeAdapter;
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
    private HomeAdapter mHomeAdapter;
    private ArrayList<String> data;
    private Banner mBanner;

    @BindView(R.id.recyclerView_home)
    RecyclerView mRecyclerView;


//    @BindView(R.id.banner_home)
//    Banner mBanner;

    public HomeFragment() {
        // Required empty public constructor
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

        data = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(R.layout.adapter_home, data);
        mRecyclerView.setAdapter(mHomeAdapter);

        View headerView = View.inflate(getActivity(),R.layout.layout_banner,null);
        mBanner = headerView.findViewById(R.id.banner_home);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        mHomeAdapter.addHeaderView(headerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        for (int i = 0; i < 100; i++) {
            data.add(i + "");
        }
        mHomeAdapter.notifyDataSetChanged();

        ApiBanner apiBanner = new ApiBanner();
        apiBanner.getBannerData(new Callback<BaseResponse<List<BannerBean>>>() {
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
                        Log.i(TAG, "OnBannerClick: url="+url);
                    }
                });
            }

            @Override
            public void onFailure(Call<BaseResponse<List<BannerBean>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
