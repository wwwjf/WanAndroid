package com.wengjianfeng.wanandroid.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.model.pojo.ChapterChildrenBean;
import com.wengjianfeng.wanandroid.model.pojovo.ChapterBean;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wengjianfeng on 2018/4/9.
 */

public class ChapterAdapter extends BaseQuickAdapter<ChapterBean,BaseViewHolder> {

    private Context mContext;

    public ChapterAdapter(Context context, @Nullable List<ChapterBean> data) {
        super(R.layout.adapter_chapter, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChapterBean item) {
        helper.setText(R.id.tv_chapter,item.getName());
        StringBuilder stringBuilder = new StringBuilder();
        for (ChapterChildrenBean chapterChildrenBean : item.getChildren()) {
            stringBuilder.append(chapterChildrenBean.getName()+"   ");
        }
        helper.setText(R.id.tv_chapterChildren,stringBuilder.toString());
    }
}
