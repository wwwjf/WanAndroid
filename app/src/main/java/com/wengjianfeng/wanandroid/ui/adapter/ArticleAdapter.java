package com.wengjianfeng.wanandroid.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.model.pojo.ArticleBean;

import java.util.List;

/**
 * Created by wengjianfeng on 2018/3/17.
 */

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean,BaseViewHolder> {

    private Context mContext;

    public ArticleAdapter(Context context, @Nullable List<ArticleBean> data) {
        super(R.layout.adapter_article, data);
        mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean article) {
        helper.setText(R.id.tv_title,article.getTitle());
        helper.setText(R.id.tv_time,article.getNiceDate());
        helper.setText(R.id.tv_author,article.getAuthor());
        helper.setText(R.id.tv_type,article.getChapterName());
        helper.addOnClickListener(R.id.tv_collect);

        TextView tvCollect = helper.itemView.findViewById(R.id.tv_collect);
        if (article.isCollect()) {
            tvCollect.setText(mContext.getString(R.string.ic_collect_sel));
            tvCollect.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            tvCollect.setText(mContext.getString(R.string.ic_collect_nor));
            tvCollect.setTextColor(mContext.getResources().getColor(R.color.text3));
        }
    }
}
