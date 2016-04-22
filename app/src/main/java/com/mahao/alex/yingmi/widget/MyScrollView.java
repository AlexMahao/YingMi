package com.mahao.alex.yingmi.widget;

import android.content.Context;
import android.util.AttributeSet;
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
