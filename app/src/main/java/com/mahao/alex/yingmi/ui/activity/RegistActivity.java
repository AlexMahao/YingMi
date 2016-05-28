package com.mahao.alex.yingmi.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.bean.User;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.StringUtil;
import com.mahao.alex.yingmi.utils.TimeCount;
import com.mahao.alex.yingmi.utils.Tt;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * 注册页面
 */
public class RegistActivity extends BaseActivity{

    @Bind(R.id.regist_phone_edit)
    EditText mPhoneEt;

    @Bind(R.id.regist_password_edit)
    EditText mPasswordEt;

    @Bind(R.id.regist_authcode_edit)
    EditText mAuthCodeEt;

    private int userId;

    private String phone ,authCode,password ;


   /* @Bind(R.id.titleBar)
    TitleBar titleBar;*/

    @Override
    public void afterCreate() {
      /*  titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }
        });*/
    }

    @OnClick(R.id.regist_send_authcode_btn)
    public void sendAuthCode(final View view){
        //获取短信验证码
        phone = mPhoneEt.getText().toString().trim();

        if(StringUtil.isEmpty(phone)){
            mPhoneEt.setError("手机号为空");
            return;
        }
        //获取短信验证码
        BmobSMS.requestSMSCode(this, phone, "regist", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if(e==null){
                 Tt.showLong("已发送短信验证码");
                    new TimeCount(60*1000,1000, (TextView) view).start();
                }else{
                    Tt.showLong("获取短信验证码失败");
                    e.printStackTrace();
                }
            }
        });

    }

    @OnClick(R.id.regist_submit_btn)
    public void regist(){
        //注册
        authCode = mAuthCodeEt.getText().toString().trim();
        password = mPasswordEt.getText().toString().trim();
        if(StringUtil.isEmpty(authCode)){
            mAuthCodeEt.setError("验证码为空");
            return;
        }
        if(StringUtil.isEmpty(password)){
            mPasswordEt.setError("密码为空");
            return;
        }

        BmobQuery<User> query = new BmobQuery<User>();
        query.order("-userId");
        query.setLimit(1);
        query.findObjects(getApplicationContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                userId = list.get(0).getUserId()+1;
                final User user = new User();
                user.setMobilePhoneNumber(phone);
                user.setPassword(password);
                user.setUserId(userId);
                user.signOrLogin(RegistActivity.this, authCode, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Tt.showShort("注册成功");
                       App.user = user;
                        AppManager.getAppManager().finishAllActivity();
                        intent2Activity(HomeActivity.class);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Tt.showLong(s);
                    }
                });
            }

            @Override
            public void onError(int i, String s) {

            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_regist;
    }


    @OnClick(R.id.user_close_btn)
    public void close(){
        this.finish();
    }
}

