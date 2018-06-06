package com.wengjianfeng.wanandroid.app;

import com.wengjianfeng.wanandroid.BuildConfig;

/**
 * Created by wengjianfeng on 2018/4/1.
 */

public class WanConstants {
    // 请求URL
    public final static String REQUEST_HTTP_URL = "http://www.wanandroid.com/";

    // 接口返回结果名称
    public final static String INFO = "info";
    // 接口返回错误码
    public final static String ERROR_CODE = "errorcode";
    // 接口返回错误信息
    public final static String ERROR_MSG = "errormsg";

    /**
     * 闪屏页图片
     */
    public static final String SPLASH_HTTP_URL = "https://bing.ioliu.cn/v1/blur?d=0&r=0";

    /**
     * sp文件名称
     */
    public static final String SP_USERINFO = "USER_INFO";
    public static class UserInfo{
        /**
         * 用户登录信息
         */
        public static final String USER_INFO="mUserInfo";
        /**
         * 是否登录
         */
        public static final String IS_LOGIN = "mIsLogin";
    }
    public static final String COOKIE = "cookie";

    public static final int PERMISSION_REQUESTCODE = 1565;

}
