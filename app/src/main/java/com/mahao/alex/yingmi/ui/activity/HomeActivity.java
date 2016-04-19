package com.mahao.alex.yingmi.ui.activity;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;

/**
 * 首页
 * Created by mdw on 2016/4/19.
 */
public class HomeActivity extends BaseActivity {

    @Override
    public void afterCreate() {

    }


  /*
    public void onUpdate(){

        RetrofitManager
                .getInstance()
                .getAppVersion()
                .subscribe(new ProgressSubscriber<AppVersion>(this) {
                    @Override
                    public void onNext(AppVersion appVersion) {
                        tv_versionName.setText(appVersion.getVersionName());
                        tv_versionDesc.setText(appVersion.getVersionDesc());
                    }
                });

    }*/

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }
}
