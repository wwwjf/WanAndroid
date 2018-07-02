package com.wengjianfeng.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
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
    TabLayout mTabLayoutChapterDetail;

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

//        TabLayout item分隔线 
        LinearLayout linearLayout = (LinearLayout) mTabLayoutChapterDetail.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        linearLayout.setDividerPadding(SizeUtils.dp2px(10));

        for (ChapterChildrenBean chapterChild : chapterChildren) {
            TabLayout.Tab tab = mTabLayoutChapterDetail.newTab()
                    .setText(chapterChild.getName())
                    .setTag(chapterChild.getId());
            mTabLayoutChapterDetail.addTab(tab);
            ChapterDetailFragment detailFragment = ChapterDetailFragment.newInstance(chapterChild.getName(),
                    chapterChild.getId()+"");
            mChapterDetailFragment.add(detailFragment);
        }
        ChapterDetailPagerAdapter detailPagerAdapter = new ChapterDetailPagerAdapter(getSupportFragmentManager(),mChapterDetailFragment);
        mViewPagerChapterDetail.setAdapter(detailPagerAdapter);
        mTabLayoutChapterDetail.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPagerChapterDetail));
        mViewPagerChapterDetail.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayoutChapterDetail));
        mToolBarChapterDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
