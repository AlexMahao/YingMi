package com.mahao.alex.yingmi.network;

/**
 * 异常类，当获取的数据不是我们需要时，抛出异常
 *
 * Created by mdw on 2016/4/19.
 */
public class ApiException extends RuntimeException {


    /**
     * 异常信息
     * @param detailMessage
     */
    public ApiException(String detailMessage) {
        super(detailMessage);
    }
}
