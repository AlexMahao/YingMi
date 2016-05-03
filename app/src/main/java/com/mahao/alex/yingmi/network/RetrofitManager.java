package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.bean.AppVersion;
import com.mahao.alex.yingmi.bean.Comment;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.bean.Talk;
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


    public static RetrofitManager getInstance() {

        return SingletonHolder.INSTANCE;
    }


    /**
     * 获取app版本号
     *
     * @return
     */
    public Observable<AppVersion> getAppVersion() {
        return mYingMiApi.getAppVersion()
                .map(new HttpResultFuc<AppVersion>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    /**
     * 获取热门电影
     *
     * @return
     */
    public Observable<List<Production>> getHotProduction() {

        return mYingMiApi.getHotProduction()
                .map(new HttpResultFuc<List<Production>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 通过电影获取同款物品
     *
     * @return
     */
    public Observable<List<Commodity>> getCommodityListByProduction(String productionId) {
        return mYingMiApi.getCommodityListByProduction(productionId)
                .map(new HttpResultFuc<List<Commodity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取热门同款物品
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Observable<List<Theme>> getHotTheme(String page, String pageSize) {
        return mYingMiApi.getHotTheme(page, pageSize)
                .map(new HttpResultFuc<List<Theme>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取热门同款物品
     *
     * @param page
     * @param pageSize
     * @return
     */
    public Observable<List<Commodity>> getHotCommodity(String page, String pageSize) {
        return mYingMiApi.getHotCommodityList(page, pageSize)
                .map(new HttpResultFuc<List<Commodity>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取热门影视
     *
     * @return
     */
    public Observable<List<Actor>> getHotActor() {
        return mYingMiApi.getHotActor()
                .map(new HttpResultFuc<List<Actor>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 根据类型获取热门影视
     *
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    public Observable<List<Production>> getProductionByType(String type, String page, String pageSize) {
        return mYingMiApi.getProductionByType(type, page, pageSize)
                .map(new HttpResultFuc<List<Production>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 根据类型获取演员列表
     *
     * @param homeTown
     * @param page
     * @param pageSize
     * @return
     */
    public Observable<List<Actor>> getActorByType(String homeTown, String page, String pageSize) {
        return mYingMiApi.getActorByType(homeTown, page, pageSize)
                .map(new HttpResultFuc<List<Actor>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取电影同款物品
     */
    public Observable<List<Theme>> getThemeByProducitonId(String production) {
        return mYingMiApi.getThemeByProductionId(production)
                .map(new HttpResultFuc<List<Theme>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取电影图片
     */
    public Observable<List<String>> getPicByProduciton(String production) {
        return mYingMiApi.getPicByProductionId(production)
                .map(new HttpResultFuc<List<String>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取电影演员
     */
    public Observable<List<Actor>> getActorByProducitonId(String production) {
        return mYingMiApi.getActorByProduction(production)
                .map(new HttpResultFuc<List<Actor>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取电影同款物品
     */
    public Observable<List<Theme>> getThemeByActorId(String actorId) {
        return mYingMiApi.getThemeByActorId(actorId)
                .map(new HttpResultFuc<List<Theme>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取演员
     */
    public Observable<List<String>> getPicByActor(String actorId) {
        return mYingMiApi.getPicByAcator(actorId)
                .map(new HttpResultFuc<List<String>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取演员电影
     */
    public Observable<List<Production>> getProductionByActor(String actorId) {
        return mYingMiApi.getProductionByActor(actorId)
                .map(new HttpResultFuc<List<Production>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 获取同款- 主题
     * @param commodityId
     * @return
     */
    public Observable<Theme> getThemeByCommodity(String commodityId) {
        return mYingMiApi.getThemeByCommodity(commodityId)
                .map(new HttpResultFuc<Theme>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    /**
     *
     */
    public Observable<Production> getProduction(String productionId) {
        return mYingMiApi.getProduction(productionId)
                .map(new HttpResultFuc<Production>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     *
     */
    public Observable<Actor> getActor(String actorId) {
        return mYingMiApi.getActor(actorId)
                .map(new HttpResultFuc<Actor>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public Observable<List<Talk>> getTalk(String page,String pageSize){
        return mYingMiApi.getTalk(page,pageSize)
                .map(new HttpResultFuc<List<Talk>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<String> saveTalk(String userId,String talkTime,String talkContent){
        return mYingMiApi.saveTalk(userId,talkTime,talkContent)
                .map(new HttpResultFuc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Talk>> getTalkByUserId(String userId){
        return mYingMiApi.getTalkByUserId(userId)
                .map(new HttpResultFuc<List<Talk>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 添加评论的点击次数
     * @param talkId
     * @return
     */
    public Observable<String> addTalkClickCount(String talkId){
        return  mYingMiApi.addTalkClickCount(talkId)
                .map(new HttpResultFuc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 添加评论
     */
    public Observable<String> addComment(String userId,String time,String commentContent,String type,
                                         String goldId){
        return  mYingMiApi.addComment(userId,time,commentContent,type,goldId)
                .map(new HttpResultFuc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取评论
     */
    public Observable<List<Comment>> getComment( String type, String goalId){
        return  mYingMiApi.getComment(type,goalId)
                .map(new HttpResultFuc<List<Comment>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    /**
     * 添加评论的评论次数
     * @param talkId
     * @return
     */
    public Observable<String> addTalkCommentCount(String talkId){
        return  mYingMiApi.addCommentCount(talkId)
                .map(new HttpResultFuc<String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
