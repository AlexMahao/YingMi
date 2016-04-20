package com.mahao.alex.yingmi.utils;

import android.widget.Toast;

import com.mahao.alex.yingmi.base.App;

/**
 * Toast工具类
 * Created by mdw on 2016/4/19.
 */
public class Tt {

    private static android.widget.Toast sToast;
    /**
     * 初始化，在Application的onCreate中调用
     */
    public static void init(){
        if(sToast==null){
            sToast = Toast.makeText(App.getContext(),"toast",Toast.LENGTH_SHORT);

        }

    }


    /**
     * 显示短时间的toast
     * @param msg
     */
    public static void showShort(String msg){

        if(sToast==null){
            throw new RuntimeException("T  not init");
        }
        sToast.setDuration(android.widget.Toast.LENGTH_SHORT);
        sToast.setText(msg);
        sToast.show();
    }

    /**
     * 长时间Toast
     * @param msg
     */
    public static void showLong(String msg){
        if(sToast==null){
            throw new RuntimeException("T  not init");
        }
        sToast.setDuration(android.widget.Toast.LENGTH_LONG);
        sToast.setText(msg);
        sToast.show();
    }

}
