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

import com.jude.swipbackhelper.SwipeBackHelper;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.BaseResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SwipeBackHelper.onCreate(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    @OnClick({R.id.iv_login_close, R.id.btn_login, R.id.btn_register})
    public void submit(View view) {
        switch (view.getId()) {
            case R.id.iv_login_close:
                finish();
                break;
            case R.id.btn_login:
                Toast.makeText(this, "登录", Toast.LENGTH_SHORT).show();
                ApiUtil.requestLogin(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        Log.e(TAG, "onResponse: response=");
                        response.raw().header("");
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

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
