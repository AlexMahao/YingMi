package com.mahao.alex.yingmi.network;

import com.mahao.alex.yingmi.utils.Tt;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * 带有缓冲框的dialog
 * Created by mdw on 2016/4/18.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressDialogListener {


    private ProgressDialogHandler mProgressDialogHandler;


    public ProgressSubscriber() {

        this.mProgressDialogHandler = new ProgressDialogHandler(false,this);
    }

    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }


    @Override
    public void onStart() {
        showProgressDialog();
    }



    @Override
    public void onCompleted() {
        dismissProgressDialog();

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {
            Tt.showShort("网络中断，请检查您的网络状态");
        } else if (e instanceof ConnectException) {
            Tt.showShort("网络中断，请检查您的网络状态");
        } else {
            Tt.showShort("服务器异常！！！");
        }
        dismissProgressDialog();
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
