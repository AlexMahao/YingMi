package com.mahao.alex.yingmi.base;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alex.dao.DaoMaster;
import com.alex.dao.DaoSession;
import com.mahao.alex.yingmi.bean.User;
import com.mahao.alex.yingmi.utils.Tt;
import com.umeng.socialize.PlatformConfig;

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

    public static App sApp;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;
        sApp = this;
        //toast 初始化
        Tt.init();


        PlatformConfig.setWeixin("wx737fc1974ed101e9", "66e100838fa81ccc0731f7e00c1b9dec");


        Bmob.initialize(this, "3afa610dc60f5bdc86f853992a3cdc65");

        BmobInstallation.getCurrentInstallation(this).save();

        user = BmobUser.getCurrentUser(this, User.class);

        setupDatabase();
    }


    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "yingmi-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    /**
     * 获取上下文对象
     * @return
     */
    public static Context getContext(){

        return sContext;
    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
}
