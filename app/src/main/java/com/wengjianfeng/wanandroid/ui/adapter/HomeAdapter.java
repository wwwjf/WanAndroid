package com.wengjianfeng.wanandroid.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wengjianfeng.wanandroid.R;

import java.util.List;

/**
 * Created by wengjianfeng on 2018/3/17.
 */

public class HomeAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public HomeAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_home_textView,item);
    }
}
