package com.mahao.alex.yingmi.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mahao.alex.yingmi.base.App;

/**
 * Created by mdw on 2016/4/26.
 */
public class RecycleUtils {

    /**
     * 初始化水平的Recycle
     * @param recyclerView
     */
    public static void initHorizontalRecyle(RecyclerView recyclerView){

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }


    /**
     * 初始化水平的Recycle
     * @param recyclerView
     */
    public static void initVerticalRecyle(RecyclerView recyclerView){

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}
