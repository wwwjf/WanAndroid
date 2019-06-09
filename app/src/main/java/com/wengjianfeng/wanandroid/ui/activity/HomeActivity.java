package com.wengjianfeng.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.app.WanConstants;
import com.wengjianfeng.wanandroid.base.BaseActivity;
import com.wengjianfeng.wanandroid.manager.UserInfoManager;
import com.wengjianfeng.wanandroid.model.pojo.UserBean;
import com.wengjianfeng.wanandroid.ui.adapter.MainFragmentPagerAdapter;
import com.wengjianfeng.wanandroid.ui.event.ScrollArticleEvent;
import com.wengjianfeng.wanandroid.ui.event.ScrollTreeEvent;
import com.wengjianfeng.wanandroid.ui.event.UserEvent;
import com.wengjianfeng.wanandroid.ui.fragment.ChapterFragment;
import com.wengjianfeng.wanandroid.ui.fragment.HomeFragment;
import com.wengjianfeng.wanandroid.ui.fragment.ProjectFragment;
import com.wengjianfeng.wanandroid.utils.ScreenBrightnessTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
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
    protected boolean isNeedSwipeBack() {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        initImmersive();


        if (!Settings.System.canWrite(this)) {
//            mPermissionChecker.requestPermissions();
            /*Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 123);*/
        } else {
            ScreenBrightnessTool builder = ScreenBrightnessTool.Builder(this);
            boolean systemAutomaticMode = builder.getSystemAutomaticMode();
            if (systemAutomaticMode) {
                builder.setMode(ScreenBrightnessTool.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
            builder.setBrightness(100);
        }

        setViewPager();
        mToolbar.setTitle(mPagerAdapter.getPageTitle(0));

        setSupportActionBar(mToolbar);
        //setOnMenuItemClickListener事件需在setSupport之后才能生效
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_search:
                        startActivity(new Intent(HomeActivity.this, SearchActivity.class));
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
                this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        mNavigationView.setNavigationItemSelectedListener(this);
        initHeaderView();

    }

    private void initHeaderView() {
        View headerView = mNavigationView.getHeaderView(0);
        ImageView ivLogin = headerView.findViewById(R.id.iv_nav_header_icon);
        TextView tvUserName = headerView.findViewById(R.id.tv_nav_header_userName);
        Button btnExit = headerView.findViewById(R.id.btn_nav_header_exit);

        if (UserInfoManager.isLogin()) {
            UserBean user = UserInfoManager.getUserInfo();
            if (user != null) {
                tvUserName.setText(user.getUsername());
            }
            btnExit.setVisibility(View.VISIBLE);
        } else {
            tvUserName.setText("未登录");
            btnExit.setVisibility(View.GONE);
            ivLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!UserInfoManager.isLogin())
                        showLogin();
                }
            });
        }

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoManager.exitUser();
                EventBus.getDefault().post(new UserEvent());
            }
        });
    }

    private void showLogin() {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    private void setViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        HomeFragment homeFragment = HomeFragment.newInstance("首页", "articleFragment");
        ChapterFragment chapterFragment = ChapterFragment.newInstance("知识体系", "chapterFragment");
        ProjectFragment projectFragment = ProjectFragment.newInstance("项目", "projectFragment");
        fragmentList.add(homeFragment);
        fragmentList.add(chapterFragment);
        fragmentList.add(projectFragment);

        mPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mToolbar.setTitle(mViewPager.getAdapter().getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    EventBus.getDefault().post(new ScrollArticleEvent());
                } else {
                    EventBus.getDefault().post(new ScrollTreeEvent());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        changeIconImgBottomMargin(mTabLayout, -0);

    }

    /**
     * TabLayout icon和文字同时存在 调整间隔
     *
     * @param parent
     * @param px
     */
    private void changeIconImgBottomMargin(ViewGroup parent, int px) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                changeIconImgBottomMargin((ViewGroup) child, px);
            } else if (child instanceof ImageView) {
                ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) child.getLayoutParams());
                lp.bottomMargin = px;
                child.requestLayout();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Class destActivity = null;
        switch (itemId) {
            case R.id.nav_collect://收藏
                //检测是否登录
                if (!UserInfoManager.isLogin()) {
                    ToastUtils.showShort("请先登录");
                    showLogin();
                    return true;
                }
                destActivity = CollectionActivity.class;
                break;
            case R.id.nav_setting://设置
                destActivity = SettingActivity.class;
                break;
            case R.id.nav_about://关于
                destActivity = WebActivity.class;
                break;
        }
        if (destActivity != null) {
            Intent intent = new Intent(this, destActivity);
            if (itemId == R.id.nav_about) {
                intent.putExtra("url", "http://www.wanandroid.com/about");
                intent.putExtra("title", "关于");
            }
            startActivity(intent);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WanConstants.PERMISSION_REQUESTCODE) {
            for (int grantResult : grantResults) {
                Log.e(TAG, "onRequestPermissionsResult: grantResult=" + grantResult);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(UserEvent event) {
        initHeaderView();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (Settings.System.canWrite(this)) {
                ScreenBrightnessTool builder = ScreenBrightnessTool.Builder(this);
                boolean systemAutomaticMode = builder.getSystemAutomaticMode();
                if (systemAutomaticMode) {
                    builder.setMode(ScreenBrightnessTool.SCREEN_BRIGHTNESS_MODE_MANUAL);
                }
                builder.setBrightness(100);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this)) {
                ScreenBrightnessTool.Builder(this).setMode(ScreenBrightnessTool.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
            } else {
                Log.e(TAG, "onDestroy: 无法修改系统设置");
            }
        }
    }
}
