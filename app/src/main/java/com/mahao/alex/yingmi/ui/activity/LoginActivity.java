package com.mahao.alex.yingmi.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.mahao.alex.yingmi.MainActivity;
import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.bean.User;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.StringUtil;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.widget.TitleBar;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {

    /**
     * 手机
     */
    @Bind(R.id.login_phone_edit)
    private EditText phoneEt;
    /**
     * 密码
     */
    @Bind(R.id.login_password_edit)
    private EditText passwordEt;

    private String password,phone;

    @Bind(R.id.titleBar)
    TitleBar titleBar;

    @Override
    public void afterCreate() {

        //设置标题可返回
        titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick(R.id.login_submit_btn)
    public void login(){
        phone = phoneEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        if(StringUtil.isEmpty(phone)|| StringUtil.isEmpty(password)){
            Tt.showShort("用户名或密码不能为空");
            return;
        }
        BmobUser.loginByAccount(this, phone, password, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (user != null) {
                   Tt.showShort("登录成功");
                    App.user = user;
                    AppManager.getAppManager().finishAllActivity();

                    intent2Activity(MainActivity.class);

                }else{
                   Tt.showLong("登陆失败："+e.getMessage());
                }
            }
        });

    }

    @OnClick(R.id.login_2resetpassword_btn)
    public void login2resetpassword(){
        //跳转重置密码
        intent2Activity(ResetPasswordActivity.class);
    }

    @OnClick(R.id.logn_2regist_btn)
    public void login2regist(){
        //跳转注册
        intent2Activity(RegistActivity.class);
    }

}
