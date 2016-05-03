package com.mahao.alex.yingmi.widget;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.mahao.alex.yingmi.R;

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


        final ObjectAnimator anim = ObjectAnimator.ofFloat(view.findViewById(R.id.iv_load_circle), "rotation",
                0f, 360f);


        anim.setDuration(500);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.RESTART);
        anim.start();
    }

}
