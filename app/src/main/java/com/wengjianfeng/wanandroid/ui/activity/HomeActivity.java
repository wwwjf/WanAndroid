package com.wengjianfeng.wanandroid.ui.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.ui.adapter.HomeAdapter;
import com.wengjianfeng.wanandroid.ui.adapter.MainFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


//    @BindView(R.id.recyclerView_main)
//    RecyclerView mRecyclerView;

    @BindView(R.id.navigationView)
    NavigationView mNavigationView;

    @BindView(R.id.viewPager_main)
    ViewPager mViewPager;

    @BindView(R.id.tabLayout_main)
    TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        //添加menu图标
        //方法1、menu图标没动画 设置actionbar图标，并重写onOptionsItemSelected方法
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);*/
//        https://chanyouji.com/api/destinations/45.json
        //方法2 menu图标有动画
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        /*LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        ArrayList<String> data = new ArrayList();
        for (int i = 0;i<100;i++) {
            data.add(i+"");
        }
        HomeAdapter homeAdapter = new HomeAdapter(R.layout.adapter_home,data);
        mRecyclerView.setAdapter(homeAdapter);*/

        MainFragmentPagerAdapter pagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

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
}
