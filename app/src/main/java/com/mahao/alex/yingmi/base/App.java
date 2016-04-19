package com.mahao.alex.yingmi.base;

import android.app.Application;
import android.content.Context;

import com.mahao.alex.yingmi.utils.Tt;

/**
 * 应用程序入口
 * Created by mdw on 2016/4/19.
 */
public class App  extends Application{

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;

        //toast 初始化
        Tt.init();

    }


    /**
     * 获取上下文对象
     * @return
     */
    public static Context getContext(){

        return sContext;
    }
}
