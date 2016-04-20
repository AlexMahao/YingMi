package com.mahao.alex.yingmi.ui.fragment;

import android.widget.Button;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.ui.activity.LoginActivity;
import com.mahao.alex.yingmi.utils.Tt;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alex_MaHao on 2016/4/19.
 */
public class MineFragment extends BaseFragment {

    @Override
    protected void afterCreate() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @OnClick(R.id.text)
    public void login(){
        if(App.user==null){
            intent2Activity(LoginActivity.class);
        }else{
            Tt.showShort("用户已登录");
        }
    }
}
