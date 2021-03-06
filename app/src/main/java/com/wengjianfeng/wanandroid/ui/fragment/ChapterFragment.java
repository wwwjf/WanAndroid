package com.wengjianfeng.wanandroid.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.base.FragmentKeyDown;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;
import com.wengjianfeng.wanandroid.ui.activity.ChapterDetailActivity;
import com.wengjianfeng.wanandroid.ui.adapter.ChapterAdapter;
import com.wengjianfeng.wanandroid.ui.event.ScrollTreeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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

public class ChapterFragment extends Fragment implements FragmentKeyDown{
    public static final String TAG = ChapterFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    @BindView(R.id.recyclerView_chapter)
    RecyclerView mRecyclerViewChapter;

    @BindView(R.id.ptrClassicFrameLayout_chapter)
    PtrClassicFrameLayout mPtrClassicFrameLayoutChapter;

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
        mChapterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChapterBean chapterBean = mChapterAdapter.getData().get(position);
                Intent intent = new Intent(getActivity(), ChapterDetailActivity.class);
                intent.putExtra("chapter",chapterBean);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPtrClassicFrameLayoutChapter.setLastUpdateTimeRelateObject(this);
        mPtrClassicFrameLayoutChapter.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        mChapterAdapter.bindToRecyclerView(mRecyclerViewChapter);
        mChapterAdapter.setEmptyView(R.layout.view_load_empty);
        initData();
    }

    private void initData() {
        ApiUtil.getChapterListData(new Callback<BaseResponse<List<ChapterBean>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<ChapterBean>>> call,
                                   Response<BaseResponse<List<ChapterBean>>> response) {
                mPtrClassicFrameLayoutChapter.refreshComplete();
                mChapterList.addAll(response.body().getData());
                mChapterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<List<ChapterBean>>> call, Throwable t) {
                mPtrClassicFrameLayoutChapter.refreshComplete();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onFragmentKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onFragmentKeyDown: keCode="+keyCode);
        return false;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScrollEvent(ScrollTreeEvent event){
        mRecyclerViewChapter.smoothScrollToPosition(0);
    }
}
