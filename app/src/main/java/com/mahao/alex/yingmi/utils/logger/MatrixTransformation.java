package com.mahao.alex.yingmi.utils.logger;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import com.mahao.alex.yingmi.base.App;
import com.squareup.picasso.Transformation;

/**
 * Created by mdw on 2016/4/20.
 */
public class MatrixTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap bitmap = scaleBitmap(source);
        source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "matrix";
    }

    public Bitmap scaleBitmap(Bitmap bitmap){
        //得到图片的分辨率，获取宽度
        DisplayMetrics dm = new DisplayMetrics();
        dm = App.getContext().getApplicationContext().getResources().getDisplayMetrics();
        int mScreenWidth = dm.widthPixels-20/2;// 获取屏幕分辨率宽度

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        //得到图片宽度比
        float num = mScreenWidth / (float)bitmapWidth;

        Matrix matrix = new Matrix();
        matrix.postScale(num, num);
        // 产生缩放后的Bitmap对象
        Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth,
                bitmapHeight, matrix, true);
        return  resizeBitmap;
    }


}
