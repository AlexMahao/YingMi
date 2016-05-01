package com.mahao.alex.yingmi.widget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.utils.AppManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自定义购买的Dialog
 * Created by mdw on 2016/4/28.
 */
public class CustomDialog extends Dialog {


    private View view;


    public CustomDialog(Context context) {
        this(context, R.style.CustomDialog);

    }


    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();

    }



    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_progress, null);
        setContentView(view);
    }

}
