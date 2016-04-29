package com.mahao.alex.yingmi.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by mdw on 2016/4/29.
 */
public class TimeCount extends CountDownTimer {

    private TextView tv;

    public TimeCount(long millisInFuture, long countDownInterval,TextView tv) {
        super(millisInFuture, countDownInterval);
        this.tv = tv;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tv.setClickable(false);
        tv.setText(millisUntilFinished / 1000 + "秒");
    }

    @Override
    public void onFinish() {
        tv.setText("重新获取");
        tv.setClickable(true);
    }
}
