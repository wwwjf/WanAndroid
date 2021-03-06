package com.wengjianfeng.wanandroid.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.app.WanConstants;
import com.wengjianfeng.wanandroid.app.WanURL;
import com.wengjianfeng.wanandroid.base.BaseActivity;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.splash.PictureBean;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_splash)
    ImageView mImageViewSplash;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        animation=new AnimationUtils().loadAnimation(this,R.anim.anim_zoom_in);
        animation.setFillAfter(true);//动画执行完毕后停留在最后一帧

        ApiUtil.getSplash(new Callback<PictureBean>() {
            @Override
            public void onResponse(Call<PictureBean> call, Response<PictureBean> response) {

                Log.e(TAG, "onResponse=====: "+response.body().getData().getUrl());

                loadPicture(response.body().getData().getUrl());
            }

            @Override
            public void onFailure(Call<PictureBean> call, Throwable t) {
                Log.e(TAG, "onFailure: ===="+t.getMessage());

            }
        });



        mImageViewSplash.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                SplashActivity.this.finish();
            }
        },3*1000);
    }

    private void loadPicture(String url) {
        String date2String = TimeUtils.date2String(TimeUtils.getNowDate(),new SimpleDateFormat("yyyy-MM-dd"));
        Log.e(TAG, "onCreate: date2String="+date2String );

        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        mImageViewSplash.startAnimation(animation);
                        return false;
                    }
                })
//                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round))
                .apply(new RequestOptions().signature(new ObjectKey(date2String)))//缓存刷新
                .into(mImageViewSplash);
    }
}
