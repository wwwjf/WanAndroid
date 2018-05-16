package com.wengjianfeng.wanandroid.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.base.BaseAgentWebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseAgentWebActivity {
    public static final String TAG = WebActivity.class.getSimpleName();

    @BindView(R.id.web_linearLayout_container)
    LinearLayout mViewContainer;

    @BindView(R.id.web_toolbar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mToolbar.setTitle(getIntent().getStringExtra("title"));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.this.finish();

            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getIndicatorColor() {
        return Color.parseColor("#ff0000");
    }

    @Override
    protected int getIndicatorHeight() {
        return 3;
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return findViewById(R.id.web_linearLayout_container);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Nullable
    @Override
    protected String getUrl() {
        return getIntent().getStringExtra("url");
    }
}
