package com.mahao.alex.yingmi.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by mdw on 2016/4/19.
 */
public abstract class BaseActivity  extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);

        afterCreate();

    }

    /**
     * 加载完布局之后，初始化操作
     */
    public abstract void  afterCreate();


    /**
     * 获取布局资源id
     * @return
     */
    public abstract  int getLayoutId();
}
