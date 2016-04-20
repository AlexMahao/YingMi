package com.mahao.alex.yingmi.base;

import android.app.Application;
import android.content.Context;

import com.mahao.alex.yingmi.bean.User;
import com.mahao.alex.yingmi.utils.Tt;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobUser;

/**
 * 应用程序入口
 * Created by mdw on 2016/4/19.
 */
public class App  extends Application{

    public static User user;

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;

        //toast 初始化
        Tt.init();


        Bmob.initialize(this, "3afa610dc60f5bdc86f853992a3cdc65");

        BmobInstallation.getCurrentInstallation(this).save();

        user = BmobUser.getCurrentUser(this, User.class);

    }


    /**
     * 获取上下文对象
     * @return
     */
    public static Context getContext(){

        return sContext;
    }
}
