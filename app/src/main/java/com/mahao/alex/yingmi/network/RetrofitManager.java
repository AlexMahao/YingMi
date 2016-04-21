package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.bean.AppVersion;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.bean.Theme;

import java.util.List;
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
               .map(new HttpResultFuc<AppVersion>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    /**
     * 获取热门电影
     * @return
     */
    public Observable<List<Production>> getHotProduction(){

        return mYingMiApi.getHotProduction()
                .map(new HttpResultFuc<List<Production>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 通过电影获取同款物品
     * @return
     */
    public Observable<List<Commodity>> getCommodityListByProduction( String productionId){
        return mYingMiApi.getCommodityListByProduction(productionId)
                .map(new HttpResultFuc<List<Commodity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取热门同款物品
     * @param page
     * @param pageSize
     * @return
     */
    public Observable<List<Theme>> getHotTheme(String page,String pageSize){
        return mYingMiApi.getHotTheme(page,pageSize)
                .map(new HttpResultFuc<List<Theme>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
