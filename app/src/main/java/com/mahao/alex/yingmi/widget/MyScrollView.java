package com.mahao.alex.yingmi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by mdw on 2016/4/22.
 */
public class MyScrollView extends ScrollView {

    private ScrollChangeListener scrollChangeListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface  ScrollChangeListener{
        void scrollY(int y);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.i("info","scrollView---dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        Log.i("info","scrollView---onTouchEvent");

        return  super.onTouchEvent(e);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if(scrollChangeListener!=null){
            scrollChangeListener.scrollY(getScrollY());
        }
    }

    public void setScrollChangeListener(ScrollChangeListener scrollChangeListener) {
        this.scrollChangeListener = scrollChangeListener;
    }
}
