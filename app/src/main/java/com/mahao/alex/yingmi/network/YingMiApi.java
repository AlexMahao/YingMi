package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.bean.AppVersion;
import com.mahao.alex.yingmi.bean.Comment;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.bean.HttpResult;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.bean.Talk;
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
     *
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
    Observable<HttpResult<List<Theme>>> getHotTheme(@Field("page") String page, @Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST("getHotThemeCommodity")
    Observable<HttpResult<List<Commodity>>> getHotCommodityList(@Field("page") String page, @Field("pageSize") String pageSize);


    @POST("getProductionByType")
    @FormUrlEncoded
    Observable<HttpResult<List<Production>>> getProductionByType(@Field("type") String type,
                                                                 @Field("page") String page,
                                                                 @Field("pageSize") String pageSize);

    @POST("getActorByType")
    @FormUrlEncoded
    Observable<HttpResult<List<Actor>>> getActorByType(@Field("homeTown") String homeTown,
                                                       @Field("page") String page,
                                                       @Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST("getThemeByProductionId")
    Observable<HttpResult<List<Theme>>> getThemeByProductionId(@Field("productionId") String productionId);

    @FormUrlEncoded
    @POST("getProductionPic")
    Observable<HttpResult<List<String>>> getPicByProductionId(@Field("productionId") String productionId);

    @FormUrlEncoded
    @POST("getActorByProduction")
    Observable<HttpResult<List<Actor>>> getActorByProduction(@Field("productionId") String productionId);


    @FormUrlEncoded
    @POST("getThemeByActor")
    Observable<HttpResult<List<Theme>>> getThemeByActorId(@Field("actorId") String actorId);


    @FormUrlEncoded
    @POST("getPicByActor")
    Observable<HttpResult<List<String>>> getPicByAcator(@Field("actorId") String actorId);

    @FormUrlEncoded
    @POST("getProductionByActor")
    Observable<HttpResult<List<Production>>> getProductionByActor(@Field("actorId") String actorId);

    //--------------未实现

    @FormUrlEncoded
    @POST("getThemeByCommodity")
    Observable<HttpResult<Theme>> getThemeByCommodity(@Field("commodityId") String commodityId);

    @FormUrlEncoded
    @POST("getProduction")
    Observable<HttpResult<Production>> getProduction(@Field("productionId") String productionId);


    @FormUrlEncoded
    @POST("getActor")
    Observable<HttpResult<Actor>> getActor(@Field("actorId") String actorId);


    @FormUrlEncoded
    @POST("getTalk")
    Observable<HttpResult<List<Talk>>> getTalk(@Field("page") String page, @Field("pageSize") String pageSize);

    @FormUrlEncoded
    @POST("saveTalk")
    Observable<HttpResult<String>> saveTalk(@Field("userId") String userId, @Field("talkTime") String talkTime, @Field("talkContent") String talkContent);

    @FormUrlEncoded
    @POST("getTalkByUserId")
    Observable<HttpResult<List<Talk>>> getTalkByUserId(@Field("userId") String userId);


    @FormUrlEncoded
    @POST("addTalkClickCount")
    Observable<HttpResult<String>> addTalkClickCount(@Field("talkId") String talkId);


    @FormUrlEncoded
    @POST("addComment")
    Observable<HttpResult<String>> addComment(@Field("userId") String userId, @Field("time") String time, @Field("commentContent") String commentContent
            , @Field("type") String type, @Field("goldId") String goldId);


    @FormUrlEncoded
    @POST("getComment")
    Observable<HttpResult<List<Comment>>> getComment(@Field("type") String type,@Field("goldId") String goalId);

    @FormUrlEncoded
    @POST("addCommentCount")
    Observable<HttpResult<String>> addCommentCount(@Field("talkId") String talkId);
}
