package com.mahao.alex.yingmi.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

/**
 * Created by mdw on 2016/4/20.
 */
public class CircleTransformation implements Transformation {

    private  int radius ;

    public CircleTransformation(){
        radius = DimenUtils.dp2px(25) ;
    }

    public CircleTransformation(int radius){
        /*//radius = DimenUtils.dp2px(radius);
        radius = DimenUtils.dp2px(25) ;*/
    }

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap bitmap = cutCircle(source);
        source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "user_cirlce";
    }


    /**
     * 高斯模糊
     *
     * @param bmp
     * @return
     */
    public   Bitmap cutCircle(Bitmap bmp) {
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        Bitmap result = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);

        new Canvas(result).drawCircle(50,50, 50, paint);
        return result;
    }
}
