package com.mahao.alex.yingmi.ui.activity;

import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

    private String url = "";

    @Override
    public void afterCreate() {

        url = getIntent().getStringExtra(Constant.WEB_LINK);

        web.getSettings().setJavaScriptEnabled(true);

        web.loadUrl(url);

        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleBar.setCenterText(title);
            }
        });

        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;
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
