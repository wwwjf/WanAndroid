package com.wengjianfeng.wanandroid.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.base.BaseActivity;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.HotWordBean;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    public static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.toolbar_search)
    Toolbar mToolbarSearch;

    @BindView(R.id.flowLayout_search_hotKeyWord)
    TagFlowLayout mFlowLayoutHotKeyWord;

    @BindView(R.id.linearLayout_search_hotKeyWord)
    LinearLayout mLinearLayoutHotWord;

    @BindView(R.id.clearEditText_search_keyWord)
    EditText mClearEditTextKeyWord;

    private List<HotWordBean> mHotWordList;
    private TagAdapter<HotWordBean> mTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setSupportActionBar(mToolbarSearch);
        mToolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mHotWordList = new ArrayList<>();
        mTagAdapter = new TagAdapter<HotWordBean>(mHotWordList) {
            @Override
            public View getView(FlowLayout parent, int position, HotWordBean hotWordBean) {
                TextView tvHotWord = (TextView) getLayoutInflater().inflate(R.layout.adapter_hotword,
                        mFlowLayoutHotKeyWord, false);
                tvHotWord.setText(hotWordBean.getName());
                return tvHotWord;
            }
        };
        mFlowLayoutHotKeyWord.setAdapter(mTagAdapter);
        mFlowLayoutHotKeyWord.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                HotWordBean hotWordBean = (HotWordBean) mFlowLayoutHotKeyWord.getAdapter().getItem(position);
                Log.e(TAG, "onTagClick: name="+hotWordBean.getName());
                mClearEditTextKeyWord.setText(hotWordBean.getName()+" ");
                mClearEditTextKeyWord.setSelection(hotWordBean.getName().length()+1);
                searchArticle(hotWordBean.getName());
                return false;
            }
        });



        mClearEditTextKeyWord.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    searchArticle(mClearEditTextKeyWord.getText().toString());
                }
                return false;
            }
        });

        ApiUtil.getHotWordListData(new Callback<BaseResponse<List<HotWordBean>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<HotWordBean>>> call,
                                   Response<BaseResponse<List<HotWordBean>>> response) {
                mHotWordList.addAll(response.body().getData());
                mTagAdapter.notifyDataChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<List<HotWordBean>>> call, Throwable t) {

            }
        });

    }


    /**
     * 搜索
     * @param keyWord
     */
    private void searchArticle(String keyWord){

        ApiUtil.getArticleSearchListData(new Callback<BaseResponse<ArticleListBean>>() {
            @Override
            public void onResponse(Call<BaseResponse<ArticleListBean>> call, Response<BaseResponse<ArticleListBean>> response) {
                if (response == null)
                Log.e(TAG, "onResponse: total=0" );
                if (response.body() == null)
                Log.e(TAG, "onResponse: response.body is null" );
                mLinearLayoutHotWord.setVisibility(View.GONE);//热词隐藏
                Log.e(TAG, "onResponse: "+response.body().getData().toString() );

            }

            @Override
            public void onFailure(Call<BaseResponse<ArticleListBean>> call, Throwable t) {

            }
        },0,keyWord);

    }

    @Override
    public void onBackPressed() {
        if (mLinearLayoutHotWord.getVisibility() == View.VISIBLE){
            super.onBackPressed();
//            finish();
        } else {
            mLinearLayoutHotWord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
