package com.wengjianfeng.wanandroid.app;

/**
 * Created by wengjianfeng on 2018/4/1.
 */

public class WanConstants {

    // 接口返回结果名称
    public final static String INFO = "info";
    // 接口返回错误码
    public final static String ERROR_CODE = "errorcode";
    // 接口返回错误信息
    public final static String ERROR_MSG = "errormsg";

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
