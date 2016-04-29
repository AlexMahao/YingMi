package com.mahao.alex.yingmi.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.bean.User;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.StringUtil;
import com.mahao.alex.yingmi.utils.TimeCount;
import com.mahao.alex.yingmi.utils.Tt;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

/**
 * 重置密码
 */
public class ResetPasswordActivity extends BaseActivity {
    @Bind(R.id.resetpsd_phone_edit)
    EditText mPhoneEt;

    @Bind(R.id.resetpsd_psd_edit)
    EditText mPasswordEt;

    @Bind(R.id.resetpsd_auth_code_edit)
    EditText mAuthCodeEt;


    private String phone ,authCode,password ;

    /*@Bind(R.id.titleBar)
    TitleBar titleBar;
*/
    @Override
    public void afterCreate() {
       /* titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }
        });*/
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }


    @OnClick(R.id.resetpsd_auth_code_edit)
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
        });    }

    @OnClick(R.id.resetpsd_submit_btn)
    public void resetPassword(){
        //重置密码
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

        User user = new User();
        user.setMobilePhoneNumber(phone);
        user.setPassword(password);
        BmobUser.resetPasswordBySMSCode(this, authCode, password, new ResetPasswordByCodeListener() {

            @Override
            public void done(BmobException ex) {
                // TODO Auto-generated method stub
                if (ex == null) {
                    AppManager.getAppManager().finishActivity(LoginActivity.class);
                    Tt.showShort("重置密码失败");
                    intent2Activity(LoginActivity.class);
                    AppManager.getAppManager().finishActivity(ResetPasswordActivity.class);
                } else {
                    Tt.showLong("重置失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                }
            }
        });


    }

    @OnClick(R.id.user_close_btn)
    public void close(){
        this.finish();
    }
}
