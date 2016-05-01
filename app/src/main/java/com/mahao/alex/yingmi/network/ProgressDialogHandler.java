package com.mahao.alex.yingmi.network;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.widget.CustomDialog;

/**
 * 控制缓冲框显示与隐藏
 * Created by mdw on 2016/4/18.
 */
public class ProgressDialogHandler extends Handler {

    public static final int SHOW_DIALOG = 1;

    public static final int DISMISS_DIALOG =2;


    private boolean mCancelable;

    private ProgressDialogListener mProgressDialogListener;

    private Activity mActivity;

    private Dialog pd;



    public ProgressDialogHandler(boolean mCancelable, ProgressDialogListener mProgressDialogListener) {
        this.mCancelable = mCancelable;
        this.mProgressDialogListener = mProgressDialogListener;
        mActivity = AppManager.getAppManager().currentActivity();
    }


    /**
     * 用于显示Dialog
     */
    private void initProgressDialog(){
        if (pd == null) {
            pd = new CustomDialog(mActivity);


            pd.setCancelable(mCancelable);

            if (mCancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressDialogListener.onCancelProgress();
                    }
                });
            }

            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }


    private void dismissProgressDialog(){
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
