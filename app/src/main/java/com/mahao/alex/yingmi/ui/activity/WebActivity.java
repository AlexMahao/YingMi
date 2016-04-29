package com.mahao.alex.yingmi.ui.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.widget.TitleBar;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/28.
 */
public class WebActivity extends BaseActivity {

    @Bind(R.id.titlebar)
    TitleBar titleBar;

    @Bind(R.id.web)
    WebView web;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    private String url = "";

    private String title = "";

    @Override
    public void afterCreate() {

        url = getIntent().getStringExtra(Constant.WEB_LINK);
        title = getIntent().getStringExtra(Constant.WEB_TITLE);
        titleBar.setCenterText(title);
        web.getSettings().setJavaScriptEnabled(true);

        web.loadUrl(url);

        web.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
               // Log.i("info",newProgress+"");
               /* if(newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }*/

            }
        });

        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.i("info",url);
                if(url.startsWith("tmall")){
                    return true;
                }
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()){
            web.goBack();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
