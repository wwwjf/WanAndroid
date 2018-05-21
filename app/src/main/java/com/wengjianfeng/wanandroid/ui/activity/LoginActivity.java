package com.wengjianfeng.wanandroid.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.app.WanConstants;
import com.wengjianfeng.wanandroid.base.BaseActivity;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.manager.UserInfoManager;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.UserBean;
import com.wengjianfeng.wanandroid.ui.event.UserEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.iv_login_close)
    ImageView mImageViewClose;

    @BindView(R.id.clearEditText_login_userName)
    EditText mEditTextUserName;

    @BindView(R.id.clearEditText_login_password)
    EditText mEditTextPassword;

    @BindView(R.id.btn_login)
    Button mButtonLogin;

    @BindView(R.id.btn_register)
    Button mButtonRegister;
    private UserBean userBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @OnClick({R.id.iv_login_close, R.id.btn_login, R.id.btn_register})
    public void submit(View view) {
        switch (view.getId()) {
            case R.id.iv_login_close:
                finish();
                break;
            case R.id.btn_login:
                ApiUtil.requestLogin(new Callback<BaseResponse<UserBean>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<UserBean>> call, Response<BaseResponse<UserBean>> response) {
                        com.orhanobut.logger.Logger.json(response.body().toString());
                        userBean = response.body().getData();
                        if (userBean == null){
                            Log.e(TAG, "onResponse: userBean为空");
                            String errorMsg = response.body().getErrorMsg();
                            ToastUtils.showShort(errorMsg);
                            return;
                        }
                        UserInfoManager.saveUserInfo(userBean);//保存用户信息
                        UserInfoManager.saveIsLogin(true);//保存是否登录
                        List<String> headerCookieList = response.raw().headers("Set-Cookie");
                        UserInfoManager.saveUserInfoCookie(headerCookieList);//保存cookie
                        EventBus.getDefault().post(new UserEvent());
                        finish();

                    }
                    @Override
                    public void onFailure(Call<BaseResponse<UserBean>> call, Throwable t) {
                        Log.e(TAG, "onFailure: 登录失败");
                        ToastUtils.showShort("连接服务器失败");
                    }
                },mEditTextUserName.getText().toString(),
                  mEditTextPassword.getText().toString());
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            default:
                break;
        }
    }
}
