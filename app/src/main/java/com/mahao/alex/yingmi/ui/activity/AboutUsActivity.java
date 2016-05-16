package com.mahao.alex.yingmi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.widget.CustomDialog;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Alex_MaHao on 2016/5/1.
 */
public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.about_us_app_version)
    TextView appVersion;

    @Override
    public void afterCreate() {
        Config.dialog = new CustomDialog(AboutUsActivity.this);

        appVersion.setText(getAppVersionName(getApplicationContext()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }


    @OnClick(R.id.share_weixin)
    public void shareWeixin(){
        new ShareAction(this)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(new UMListener())
                .withText("发现影觅，探寻美好")
                .withTargetUrl("https://www.pgyer.com/nXvJ")
                .withMedia(new UMImage(getApplicationContext(),R.mipmap.app_icon))
                .share();
    }

    @OnClick(R.id.share_weixin_circle)
    public void  shareWeixinCircle(){
        new ShareAction(this)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new UMListener())
                .withText("发现影觅，探寻美好")
                .withTargetUrl("https://www.pgyer.com/nXvJ")
                .withMedia(new UMImage(getApplicationContext(),R.mipmap.app_icon))
                .share();


    }


    class UMListener implements UMShareListener{

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Tt.showShort("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Tt.showLong(throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
    }


    /**
     * 获取系统版本号
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null|| versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.i("VersionInfo", "Exception", e);
        }
        return versionName;
    }
}
