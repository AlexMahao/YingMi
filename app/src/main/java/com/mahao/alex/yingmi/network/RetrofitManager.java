package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.bean.AppVersion;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 * 网络服务管理类
 * Created by mdw on 2016/4/19.
 */
public class RetrofitManager {

    /**
     * 根地址
     */
    public static final String BASE_URL = "http://cloud.bmob.cn/2dc6934ae2cd0704/";

    public static final int DEFAULT_TIMEOUT = 5;

    private Retrofit mRetrofit;

    private YingMiApi mYingMiApi;

    private RetrofitManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        mYingMiApi = mRetrofit.create(YingMiApi.class);
    }


    private static class SingletonHolder {

        private static final RetrofitManager INSTANCE = new RetrofitManager();

    }


    public static  RetrofitManager getInstance(){

        return SingletonHolder.INSTANCE;
    }


    /**
     * 获取app版本号
     * @return
     */
    public Observable<AppVersion> getAppVersion(){
       return mYingMiApi.getAppVersion()
                .subscribeOn(Schedulers.io())
                .map(new HttpResultFuc<AppVersion>())
                .observeOn(AndroidSchedulers.mainThread());

    }





}
