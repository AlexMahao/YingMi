package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.bean.AppVersion;
import com.mahao.alex.yingmi.bean.HttpResult;

import retrofit2.http.POST;
import rx.Observable;

/**
 * 影觅的api接口
 * Created by mdw on 2016/4/19.
 */
public interface YingMiApi {

    @POST("getAppVersion")
    Observable<HttpResult<AppVersion>> getAppVersion();

}
