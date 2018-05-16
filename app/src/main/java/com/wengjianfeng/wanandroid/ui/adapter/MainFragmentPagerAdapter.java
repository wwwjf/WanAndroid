package com.wengjianfeng.wanandroid.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wengjianfeng.wanandroid.ui.fragment.HomeFragment;
import com.wengjianfeng.wanandroid.ui.fragment.ChapterFragment;
import com.wengjianfeng.wanandroid.ui.fragment.UserFragment;

import java.util.List;

/**
 * Created by wengjianfeng on 2018/3/24.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

//    private String[] mTabTitles = new String[]{"首页","知识体系"};
    private List<Fragment> mFragmentList;

    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Bundle arguments = mFragmentList.get(position).getArguments();
        return arguments.getString("param1");//标题
    }
}
