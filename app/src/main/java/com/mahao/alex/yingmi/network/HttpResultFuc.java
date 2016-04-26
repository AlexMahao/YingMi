package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.bean.HttpResult;

import rx.functions.Func1;

/**
 * 获取网络数据的处理
 * Created by mdw on 2016/4/19.
 */
public class HttpResultFuc<T> implements Func1<HttpResult<T>,T> {
    @Override
    public T call(HttpResult<T> httpResult) {
        if(!("1".equals(httpResult.getStatus()))){
            throw new ApiException(httpResult.getMessage());
        }
        return httpResult.getData();
    }
}
