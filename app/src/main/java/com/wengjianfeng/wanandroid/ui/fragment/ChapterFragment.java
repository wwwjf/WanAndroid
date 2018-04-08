package com.wengjianfeng.wanandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.helper.Api;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;
import com.wengjianfeng.wanandroid.ui.adapter.ChapterAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    @BindView(R.id.recyclerView_chapter)
    RecyclerView mRecyclerViewChapter;

    private List<ChapterBean> mChapterList;
    private ChapterAdapter mChapterAdapter;



    public ChapterFragment() {
    }


    public static ChapterFragment newInstance(String param1, String param2) {
        ChapterFragment fragment = new ChapterFragment();
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
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);
        unbinder = ButterKnife.bind(this,view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewChapter.setLayoutManager(linearLayoutManager);
        mChapterList = new ArrayList<>();
        mChapterAdapter = new ChapterAdapter(getActivity(),mChapterList);
        mRecyclerViewChapter.setAdapter(mChapterAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Api api = new Api();
        api.getChapterListData(new Callback<BaseResponse<List<ChapterBean>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ChapterBean>>> call,
                                   Response<BaseResponse<List<ChapterBean>>> response) {
                mChapterList.addAll(response.body().getData());
                mChapterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<List<ChapterBean>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
