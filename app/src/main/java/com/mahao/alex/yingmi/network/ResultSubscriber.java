package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.utils.Tt;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by mdw on 2016/4/20.
 */
public class ResultSubscriber<T> extends Subscriber<T> {


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            Tt.showShort("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            Tt.showShort("网络中断，请检查您的网络状态");
        } else {
            Tt.showShort(e.getMessage());
        }
    }

    @Override
    public  void onNext(T o) {

    }
}
