package com.mahao.alex.yingmi.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by mdw on 2016/4/19.
 */
public abstract class BaseActivity  extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        AppManager.getAppManager().addActivity(this);

        StatusBarUtil.setColor(this, Color.parseColor("#0e0e0e"));

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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getAppManager().finishActivity(this);

    }


    public void intent2Activity(Class classes){
        Intent intent = new Intent(this,classes);
        startActivity(intent);
    }
}
