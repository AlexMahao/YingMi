package com.mahao.alex.yingmi.utils;

import android.util.TypedValue;

import com.mahao.alex.yingmi.base.App;

/**
 * Created by mdw on 2016/4/20.
 */
public class DimenUtils {


    /**
     * dp 转 px
     * @param dp
     * @return
     */
    public static int dp2px(int dp){
        int  px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, App.getContext().getResources().getDisplayMetrics());

        return px;
    }
}
