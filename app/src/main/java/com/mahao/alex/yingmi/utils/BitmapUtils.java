package com.mahao.alex.yingmi.utils;

import android.widget.ImageView;

import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.utils.logger.MatrixTransformation;
import com.squareup.picasso.Picasso;

/**
 * Created by mdw on 2016/4/20.
 */
public class BitmapUtils {

    public static final int NORMAL=1;
    /**
     * 高斯模糊
     */
    public static final int GAOSI = 2;


    /**
     * 对图片进行缩放
     */
    public static final int MATRIX = 3;


    /**
     * 剪切头像的圆形
     */
    public static final int CIRCLE = 4;

    /**
     * 封装此方法，便于修改图片网络框架
     * @param img
     * @param url
     */
    public static void loadImage(ImageView img,String url){

        loadImage(img,url,NORMAL);
    }

    public static void loadImage(ImageView img,String url,int status){
        switch (status){
            case NORMAL:picassoLoader(img,url);break;
            case GAOSI:picassoLoader2Gaosi(img,url);break;
            case MATRIX:picassoLoader2Matrix(img,url);break;
            case CIRCLE:picassoLoader2Circle(img,url);break;
        }

    }

    private static void picassoLoader2Matrix(ImageView img, String url) {
        Picasso.with(App.getContext()).load(url).transform(new MatrixTransformation()).into(img);


    }

    private static void picassoLoader(ImageView img,String url){
        Picasso.with(App.getContext()).load(url).into(img);
    }

    private static void picassoLoader2Circle(ImageView img,String url){
        Picasso.with(App.getContext()).load(url).transform(new CircleTransformation()).into(img);
    }




    private static void picassoLoader2Gaosi(ImageView img, String url) {
        Picasso.with(App.getContext()).load(url).transform(new GaosiTransformation()).into(img);
    }


}
