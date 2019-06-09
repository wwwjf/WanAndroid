package com.wengjianfeng.wanandroid.base;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wengjianfeng.wanandroid.R;

public abstract class BaseFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view,savedInstanceState);
        return view;
    }

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void initView(View view, Bundle saveInstanceState);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);

    }

    protected abstract void initData(@Nullable Bundle savedInstanceState);



}
