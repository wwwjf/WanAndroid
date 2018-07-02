package com.wengjianfeng.wanandroid.ui.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.wengjianfeng.wanandroid.R;
import com.wengjianfeng.wanandroid.base.BaseActivity;
import com.wengjianfeng.wanandroid.helper.ApiUtil;
import com.wengjianfeng.wanandroid.model.BaseResponse;
import com.wengjianfeng.wanandroid.model.pojo.UserBean;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.btn_register_register)
    Button mButtonRegister;

    @BindView(R.id.iv_register_close)
    ImageView mImageViewClose;

    @BindView(R.id.clearEditText_register_userName)
    EditText mEditTextUserName;

    @BindView(R.id.clearEditText_register_password)
    EditText mEditTextPassword;

    @BindView(R.id.clearEditText_register_rePassword)
    EditText mEditTextRePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @OnClick({R.id.btn_register_register,R.id.iv_register_close})
    public void submmit(View view){
        switch (view.getId()){
            case R.id.iv_register_close:
                onBackPressed();
                break;
            case R.id.btn_register_register:
                String userName = mEditTextUserName.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();
                String rePassword = mEditTextRePassword.getText().toString().trim();
                if (TextUtils.isEmpty(userName)){
                    ToastUtils.showShort("请输入用户名");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(rePassword)){
                    ToastUtils.showShort("请输入确认密码");
                    return;
                }
                if (!password.equals(rePassword)){
                    ToastUtils.showShort("两次密码输入不一致");
                    return;
                }

                ApiUtil.requestRegister(new Callback<BaseResponse<UserBean>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<UserBean>> call, Response<BaseResponse<UserBean>> response) {

                    }

                    @Override
                    public void onFailure(Call<BaseResponse<UserBean>> call, Throwable t) {

                    }
                },userName,password,rePassword);
                break;
            default:
                break;
        }
    }

}
