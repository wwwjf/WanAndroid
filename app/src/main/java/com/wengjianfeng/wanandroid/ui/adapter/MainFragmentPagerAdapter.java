package com.wengjianfeng.wanandroid.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wengjianfeng.wanandroid.ui.fragment.HomeFragment;
import com.wengjianfeng.wanandroid.ui.fragment.KnowledgeFragment;
import com.wengjianfeng.wanandroid.ui.fragment.UserFragment;

/**
 * Created by wengjianfeng on 2018/3/24.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles = new String[]{"首页","知识体系","我的"};

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return HomeFragment.newInstance("0","homeFragment");
            case 1:
                return KnowledgeFragment.newInstance("1","knowledgeFragment");
            case 2:
                return UserFragment.newInstance("2","userFragment");
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
