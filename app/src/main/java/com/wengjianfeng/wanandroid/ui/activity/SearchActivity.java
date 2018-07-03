package com.wengjianfeng.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.base.BaseActivity;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.ArticleBean;
import com.wengjianfeng.wanandroid.model.pojo.HotWordBean;
import com.wengjianfeng.wanandroid.model.pojovo.ArticleListBean;
import com.wengjianfeng.wanandroid.ui.adapter.ArticleAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    public static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.toolbar_search)
    Toolbar mToolbarSearch;

    @BindView(R.id.flowLayout_search_hotKeyWord)
    TagFlowLayout mFlowLayoutHotKeyWord;

    @BindView(R.id.flowLayout_search_friendWeb)
    TagFlowLayout mFlowLayoutFriendWeb;

    @BindView(R.id.scrollView_search_hotKeyWord)
    ScrollView mScrollViewHotWord;

    @BindView(R.id.clearEditText_search_keyWord)
    EditText mClearEditTextKeyWord;

    @BindView(R.id.recyclerView_search_article)
    RecyclerView mRecyclerViewArticle;

    private List<HotWordBean> mHotWordList;
    private TagAdapter<HotWordBean> mTagHotWordAdapter;

    private List<HotWordBean> mFriendWebList;
    private TagAdapter<HotWordBean> mTagFriendWebAdapter;

    private List<ArticleBean> mArticleList;
    private ArticleAdapter mArticleAdapter;

    private SkeletonScreen skeletonScreen;

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
        mTagHotWordAdapter = new TagAdapter<HotWordBean>(mHotWordList) {
            @Override
            public View getView(FlowLayout parent, int position, HotWordBean hotWordBean) {
                TextView tvHotWord = (TextView) getLayoutInflater().inflate(R.layout.adapter_hotword,
                        mFlowLayoutHotKeyWord, false);
                tvHotWord.setText(hotWordBean.getName());
                return tvHotWord;
            }
        };
        mFlowLayoutHotKeyWord.setAdapter(mTagHotWordAdapter);
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

        mFriendWebList = new ArrayList<>();
        mTagFriendWebAdapter = new TagAdapter<HotWordBean>(mFriendWebList){
            @Override
            public View getView(FlowLayout parent, int position, HotWordBean hotWordBean) {
                TextView tvFriendWeb = (TextView) getLayoutInflater().inflate(R.layout.adapter_hotword,
                        mFlowLayoutFriendWeb,false);
                tvFriendWeb.setText(hotWordBean.getName());
                return tvFriendWeb;
            }
        };
        mFlowLayoutFriendWeb.setAdapter(mTagFriendWebAdapter);
        mFlowLayoutFriendWeb.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                HotWordBean hotWordBean = mFriendWebList.get(position);
                String url = hotWordBean.getLink();
                String title = hotWordBean.getName();
                Intent intent = new Intent(SearchActivity.this, WebActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
                return false;
            }
        });



        mArticleList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerViewArticle.setLayoutManager(manager);
        mArticleAdapter = new ArticleAdapter(this,mArticleList);
        mRecyclerViewArticle.setAdapter(mArticleAdapter);
        mArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean article = (ArticleBean) adapter.getData().get(position);
                String url = article.getLink();
                String title = article.getTitle();
                Intent intent = new Intent(SearchActivity.this, WebActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });
        skeletonScreen = Skeleton.bind(mRecyclerViewArticle)
                .adapter(mArticleAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.item_skeleton_article)
                .show(); //default count is 10

        mClearEditTextKeyWord.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    searchArticle(mClearEditTextKeyWord.getText().toString());
                }
                return false;
            }
        });

        initData();

    }

    private void initData() {
        ApiUtil.getHotWordListData(new Callback<BaseResponse<List<HotWordBean>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<HotWordBean>>> call,
                                   Response<BaseResponse<List<HotWordBean>>> response) {
                mHotWordList.addAll(response.body().getData());
                mTagHotWordAdapter.notifyDataChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<List<HotWordBean>>> call, Throwable t) {

            }
        });

        ApiUtil.getFriendWebData(new Callback<BaseResponse<List<HotWordBean>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<HotWordBean>>> call,
                                   Response<BaseResponse<List<HotWordBean>>> response) {
                mFriendWebList.addAll(response.body().getData());
                mTagFriendWebAdapter.notifyDataChanged();
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
                mScrollViewHotWord.setVisibility(View.GONE);//热词隐藏
                Log.e(TAG, "onResponse: "+response.body().getData().toString() );
                mRecyclerViewArticle.setVisibility(View.VISIBLE);
                mArticleList.clear();
                mArticleList.addAll(response.body().getData().getDatas());
                mArticleAdapter.notifyDataSetChanged();
                mRecyclerViewArticle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        skeletonScreen.hide();
                    }
                },500);
            }

            @Override
            public void onFailure(Call<BaseResponse<ArticleListBean>> call, Throwable t) {

            }
        },0,keyWord);

    }

    @Override
    public void onBackPressed() {
        if (mScrollViewHotWord.getVisibility() == View.VISIBLE){
            super.onBackPressed();
//            finish();
        } else {
            mScrollViewHotWord.setVisibility(View.VISIBLE);
            mRecyclerViewArticle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
