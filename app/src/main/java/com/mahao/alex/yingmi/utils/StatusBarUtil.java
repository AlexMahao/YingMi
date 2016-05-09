package com.mahao.alex.yingmi.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Alex_MaHao on 2016/5/9.
 */
public class StatusBarUtil {


    /**
     * 将acitivity中的activity中的状态栏设置为一个纯色
     *
     * @param activity 需要设置的activity
     * @param color    设置的颜色（一般是titlebar的颜色）
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0及以上，不设置透明状态栏，设置会有半透明阴影
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置statusBar的背景色
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusBarView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            //让我们的activity_main。xml中的布局适应屏幕
            setRootView(activity);
        }
    }


    /**
     * 当顶部是图片时，是图片显示到状态栏上
     *
     * @param activity
     */
    public static void setImage(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0及以上，不设置透明状态栏，设置会有半透明阴影
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //是activity_main。xml中的图片可以沉浸到状态栏上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置状态栏颜色透明。
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            //。。。。
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 设置根布局参数，让跟布局参数适应透明状态栏
     */
    private static void setRootView(Activity activity) {
        //获取到activity_main.xml文件
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);

        //如果不是设置参数，会使内容显示到状态栏上
        rootView.setFitsSystemWindows(true);
    }

    /**
     * 获取状态栏的高度
     *
     * @param acitivity
     * @return
     */
    private static int getStatusBarHeight(Activity acitivity) {

        int resourceId = acitivity.getResources().getIdentifier("status_bar_height", "dimen", "android");

        return acitivity.getResources().getDimensionPixelOffset(resourceId);
    }


    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, int color) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

}
