package com.wengjianfeng.wanandroid.manager;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.wengjianfeng.wanandroid.app.WanConstants;
import com.wengjianfeng.wanandroid.model.pojo.UserBean;

import java.util.HashSet;
import java.util.List;

/**
 * Created by wengjianfeng on 2018/4/22.
 */

public class UserInfoManager {

    private static SPUtils spUtils = SPUtils.getInstance(WanConstants.SP_USERINFO);

    public static void saveUserInfo(UserBean userBean){

        String userInfo = new Gson().toJson(userBean);
        spUtils.put(WanConstants.UserInfo.USER_INFO,userInfo);

    }

    public static UserBean getUserInfo(){
        String userInfoString = spUtils.getString(WanConstants.UserInfo.USER_INFO);
        if (TextUtils.isEmpty(userInfoString)){
            return null;
        }
        UserBean userBean = new Gson().fromJson(userInfoString, UserBean.class);
        return userBean;
    }

    public static void saveIsLogin(boolean isLogin){
        spUtils.put(WanConstants.UserInfo.IS_LOGIN,isLogin);
    }

    public static void saveUserInfoCookie(List<String> cookieList){

        spUtils.put(WanConstants.COOKIE,new HashSet<>(cookieList));
    }

    public static boolean isLogin(){
        return spUtils.getBoolean(WanConstants.UserInfo.IS_LOGIN);
    }


    public static void exitUser(){
        spUtils.put(WanConstants.UserInfo.USER_INFO,"");
        spUtils.put(WanConstants.UserInfo.IS_LOGIN,false);
        spUtils.put(WanConstants.COOKIE,new HashSet<String>());
    }
}
