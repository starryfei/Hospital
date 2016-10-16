package com.cyansoft.maodou_manger.Activity;

import android.app.Application;


import org.xutils.x;




/**
 * Created by galaxy_fanfan on 2016/4/12.
 */
public class MyApplication extends Application {


    public static boolean isLogin = false;
    public String Username;
    public String UserPwd;
    public String Userphone;
    public String Useremail;
    public String Userinfo;

    public String getUserphone() {
        return Userphone;
    }

    public void setUserphone(String userphone) {
        Userphone = userphone;
    }

    public String getUseremail() {
        return Useremail;
    }

    public void setUseremail(String useremail) {
        Useremail = useremail;
    }

    public String getUserinfo() {
        return Userinfo;
    }

    public void setUserinfo(String userinfo) {
        Userinfo = userinfo;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserPwd() {
        return UserPwd;
    }

    public void setUserPwd(String userPwd) {
        UserPwd = userPwd;
    }

    /**
     * SDK初始化也可以放到Application中
     */
//    public static String APPID = "442c24db0ce2acaedba2c193c406116c";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
//        initConfig(getApplicationContext());
        setUsername("用户名:");



    }
}
