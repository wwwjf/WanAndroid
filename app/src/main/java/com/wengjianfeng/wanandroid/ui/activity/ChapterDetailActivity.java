package com.wengjianfeng.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.base.BaseActivity;
import com.wengjianfeng.wanandroid.model.pojo.ChapterChildrenBean;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;
import com.wengjianfeng.wanandroid.ui.adapter.ChapterDetailPagerAdapter;
import com.wengjianfeng.wanandroid.ui.fragment.ChapterDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChapterDetailActivity extends BaseActivity {
    public static final String TAG = ChapterDetailActivity.class.getSimpleName();

    @BindView(R.id.toolbar_chapterDetail)
    Toolbar mToolBarChapterDetail;

    @BindView(R.id.tabLayout_chapterDetail)
    TabLayout mTablayoutChapterDetail;

    @BindView(R.id.viewPager_chapterDetail)
    ViewPager mViewPagerChapterDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);
        Intent intent = getIntent();
        ChapterBean chapterBean = (ChapterBean) intent.getSerializableExtra("chapter");
        mToolBarChapterDetail.setTitle(chapterBean.getName());
        List<ChapterChildrenBean> chapterChildren = chapterBean.getChildren();
        List<Fragment> mChapterDetailFragment = new ArrayList<>();

        for (ChapterChildrenBean chapterChild : chapterChildren) {
            TabLayout.Tab tab = mTablayoutChapterDetail.newTab()
                    .setText(chapterChild.getName())
                    .setTag(chapterChild.getId());
            mTablayoutChapterDetail.addTab(tab);
            ChapterDetailFragment detailFragment = ChapterDetailFragment.newInstance(chapterChild.getName(),
                    chapterChild.getId()+"");
            mChapterDetailFragment.add(detailFragment);
        }
        ChapterDetailPagerAdapter detailPagerAdapter = new ChapterDetailPagerAdapter(getSupportFragmentManager(),mChapterDetailFragment);
        mViewPagerChapterDetail.setAdapter(detailPagerAdapter);
        mTablayoutChapterDetail.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPagerChapterDetail));
        mViewPagerChapterDetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayoutChapterDetail));
    }
}
