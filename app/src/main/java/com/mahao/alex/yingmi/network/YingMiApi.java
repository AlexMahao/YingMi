package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.bean.AppVersion;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.bean.HttpResult;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.bean.Theme;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 影觅的api接口
 * Created by mdw on 2016/4/19.
 */
public interface YingMiApi {

    @POST("getAppVersion")
    Observable<HttpResult<AppVersion>> getAppVersion();


    /**
     * 获取热门电影
     * @return
     */
    @POST("getHotProduction")
    Observable<HttpResult<List<Production>>> getHotProduction();


    /**
     * 获取热门电影
     */
    @POST("getHotActor")
    Observable<HttpResult<List<Actor>>> getHotActor();

    /**
     * 根据电影id获取对应的同款物品
     */
    @FormUrlEncoded
    @POST("getCommodityListByProduction")
    Observable<HttpResult<List<Commodity>>> getCommodityListByProduction(@Field("productionId") String productionId);


    @FormUrlEncoded
    @POST("getHotTheme")
    Observable<HttpResult<List<Theme>>> getHotTheme(@Field("page") String page,@Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST("getHotThemeCommodity")
    Observable<HttpResult<List<Commodity>>> getHotCommodityList(@Field("page") String page,@Field("pageSize") String pageSize);


    @POST("getProductionByType")
    @FormUrlEncoded
    Observable<HttpResult<List<Production>>> getProductionByType(@Field("type") String type,
                                                                 @Field("page") String page,
                                                                 @Field("pageSize") String pageSize);

}
