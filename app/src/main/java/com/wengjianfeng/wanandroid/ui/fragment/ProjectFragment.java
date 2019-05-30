package com.wengjianfeng.wanandroid.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.ChapterChildrenBean;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;
import com.wengjianfeng.wanandroid.ui.adapter.ChapterDetailPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProjectFragment extends Fragment {
    private static final String TAG = ProjectFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.tabLayout_project)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager_project)
    ViewPager mViewPager;
    private Unbinder bind;

    public ProjectFragment() {
        // Required empty public constructor
    }


    public static ProjectFragment newInstance(String param1, String param2) {
        ProjectFragment fragment = new ProjectFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        bind = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {

//        TabLayout item分隔线 
        LinearLayout linearLayout = (LinearLayout) mTabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        linearLayout.setDividerPadding(SizeUtils.dp2px(10));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }

    private void initData() {
        ApiUtil.getProjectList(new Callback<BaseResponse<List<ChapterBean>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ChapterBean>>> call,
                                   Response<BaseResponse<List<ChapterBean>>> response) {
                if (response.body() != null &&
                        response.body().getData() != null){
                    initViewPager(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<ChapterBean>>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void initViewPager(List<ChapterBean> data) {
        List<Fragment> fragmentList = new ArrayList<>();
        for (ChapterBean chapterChild : data) {
            TabLayout.Tab tab = mTabLayout.newTab()
                    .setText(chapterChild.getName())
                    .setTag(chapterChild.getId());
            mTabLayout.addTab(tab);
            ChapterDetailFragment detailFragment = ChapterDetailFragment.newInstance(chapterChild.getName(),
                    chapterChild.getId()+"");
            fragmentList.add(detailFragment);
        }
        ChapterDetailPagerAdapter detailPagerAdapter = new ChapterDetailPagerAdapter(getActivity().getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(detailPagerAdapter);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null){
            bind.unbind();
        }
    }
}
