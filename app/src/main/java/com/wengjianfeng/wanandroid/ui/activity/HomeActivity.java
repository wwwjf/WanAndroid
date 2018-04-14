package com.wengjianfeng.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.ui.adapter.MainFragmentPagerAdapter;
import com.wengjianfeng.wanandroid.ui.fragment.ChapterFragment;
import com.wengjianfeng.wanandroid.ui.fragment.HomeFragment;
import com.wengjianfeng.wanandroid.ui.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    public static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

    @BindView(R.id.navigationView)
    NavigationView mNavigationView;

    @BindView(R.id.viewPager_main)
    ViewPager mViewPager;

    @BindView(R.id.tabLayout_main)
    TabLayout mTabLayout;

    private MainFragmentPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initImmersive();

        ButterKnife.bind(this);

        setViewPager();
        mToolbar.setTitle(mPagerAdapter.getPageTitle(0));

        setSupportActionBar(mToolbar);
        //listener事件需在setSupport之后才能生效
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_search:
                        startActivity(new Intent(HomeActivity.this,SearchActivity.class));
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        //添加menu图标
        //方法1、menu图标没动画 设置actionbar图标，并重写onOptionsItemSelected方法
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        //方法2 menu图标有动画
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void setViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        HomeFragment homeFragment = HomeFragment.newInstance("首页", "articleFragment");
        ChapterFragment chapterFragment = ChapterFragment.newInstance("知识体系", "chapterFragment");
        UserFragment userFragment = UserFragment.newInstance("个人", "userFragment");
        fragmentList.add(homeFragment);
        fragmentList.add(chapterFragment);

        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        /*mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mToolbar.setTitle(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mToolbar.setTitle(tab.getText());
            }
        });*/
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeIconImgBottomMargin(mTabLayout,-0);

    }

    /**
     * TabLayout icon和文字同时存在 调整间隔
     * @param parent
     * @param px
     */
    private void changeIconImgBottomMargin(ViewGroup parent, int px){
        for(int i = 0; i < parent.getChildCount(); i++){
            View child = parent.getChildAt(i);
            if(child instanceof ViewGroup){
                changeIconImgBottomMargin((ViewGroup) child, px);
            }
            else if(child instanceof ImageView){
                ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) child.getLayoutParams());
                lp.bottomMargin = px;
                child.requestLayout();
            }
        }
    }

    private void initImmersive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                //将侧边栏顶部延伸至status bar
                mDrawerLayout.setFitsSystemWindows(true);
                //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                mDrawerLayout.setClipToPadding(false);
            }
        }
    }
}
