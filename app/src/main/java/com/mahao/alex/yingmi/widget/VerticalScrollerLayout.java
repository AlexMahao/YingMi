package com.mahao.alex.yingmi.widget;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;


/**
 * 垂直滚动条
 * Created by Alex_MaHao on 2016/3/15.
 */
public class VerticalScrollerLayout extends ViewGroup {


    public static final int TOP = 1;
    public static final int BOTTOM = 2;


    private ScrollChangeListener scrollChangeListener;

    /**
     * 屏幕高度
     */
    private int mScreenHeight;

    /**
     * 手指上次触摸事件的y轴位置
     */
    private int mLastY;

    /**
     * 点击时y轴的偏移量
     */
    private int mStart;

    /**
     * 滚动控件
     */
    private Scroller mScroller;

    /**
     * 最小移动距离判定
     */
    private int mTouchSlop;

    /**
     * 滑动结束的偏移量
     */
    private int mEnd;

    /**
     * 初始按下y轴坐标
     */
    private int mDownStartX;

    /**
     * 记录当前y轴坐标
     */
    private int y;


    /**
     * 控件的高度
     */
    private int mHeight;

    public VerticalScrollerLayout(Context context) {
        super(context, null);
    }

    public VerticalScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenHeight = dm.heightPixels;

        mScroller = new Scroller(context);


        /**
         * 获取最小移动距离
         */
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();
        mHeight = 0;
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            // 手动获取childView的高度，此为大坑    EXACTLY精确值
            // AT_MOST为让子View去测量，同时给其一个最大值
            int childHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

            measureChild(childView, widthMeasureSpec, childHeight);

            mHeight = getChildAt(i).getMeasuredHeight() + mHeight;
        }
       Log.i("info", "child1:" + getChildAt(0).getMeasuredHeight() + " child2:" + getChildAt(1).getMeasuredHeight());
        //setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mHeight,MeasureSpec.EXACTLY));

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();

        int childHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() != View.GONE) {
                child.layout(l, childHeight, r, childHeight + child.getMeasuredHeight());
                childHeight = +child.getMeasuredHeight();
            }
        }

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownStartX = (int) ev.getY();
                 break;
            case MotionEvent.ACTION_MOVE:
                Log.i("info","scrollY:"+getScrollY());
                if (Math.abs(y - mDownStartX) > mTouchSlop) {
                    mLastY = y;
                    mStart = getScrollY();

                    Log.i("addinfo","parent");
                    return true;
                }
               /* if(getScrollY()>getChildAt(0).getMeasuredHeight()&&get){
                    return true;
                }*/

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // Log.i("info","vetrical---dispatchTouchEvent");
        y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                int dy = mLastY - y;
                Log.i("info", "dy:" + dy + " scrollY:" + getScrollY());
                if (getScrollY() + dy < 0 || getScrollY() + getBottom() + dy > mHeight) {

                } else {
                    scrollBy(0, dy);
                    mLastY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                int dScrollY = getScrollY();

                int scrollAbs = dScrollY - getChildAt(0).getMeasuredHeight() + getBottom();

               Log.i("info", "dscrollY:" + dScrollY + " scrollAbs:" + scrollAbs);
                if (scrollAbs > 0 && dScrollY - getChildAt(0).getMeasuredHeight() <= 0) {

                    if (scrollAbs < mScreenHeight / 3) { //返回之前的偏移量
                        scrollChangeListener.onScollStateChange(TOP);
                        mScroller.startScroll(0, getScrollY(), 0, -scrollAbs);
                    } else {
                        scrollChangeListener.onScollStateChange(BOTTOM);
                        mScroller.startScroll(0, getScrollY(), 0, getBottom() - scrollAbs);
                    }
                }

                break;
        }
        postInvalidate();

        return super.onTouchEvent(event);
    }


    public void scrollToTop(){
        mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
        scrollChangeListener.onScollStateChange(TOP);
        postInvalidate();

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断scroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(0, mScroller.getCurrY());
            //刷新试图
            postInvalidate();
        }
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

    public interface  ScrollChangeListener{
        void scrollY(int y);
        void onScollStateChange(int type);
    }
}

