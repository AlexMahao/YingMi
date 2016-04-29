package com.mahao.alex.yingmi.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.ui.activity.LoginActivity;
import com.mahao.alex.yingmi.ui.activity.UserInfoActivity;
import com.mahao.alex.yingmi.utils.CircleTransformation;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Alex_MaHao on 2016/4/19.
 */
public class MineFragment extends BaseFragment {

    @Bind(R.id.mine_user_icon)
    ImageView mUserIconImg;

    @Bind(R.id.mine_login_no_tv)
    TextView mLoginNoTv;

    @Bind(R.id.mine_login_yes)
    View mLoginYes;

    @Bind(R.id.scroll)
    ScrollView scroll;

    @Override
    protected void afterCreate() {

        scroll.setOverScrollMode(View.OVER_SCROLL_NEVER);

        if (App.user == null) {
            Picasso.with(App.getContext()).load(R.mipmap.home_pic_photo_logout).transform(new CircleTransformation()).into(mUserIconImg);
            mLoginNoTv.setVisibility(View.VISIBLE);
            mLoginYes.setVisibility(View.GONE);
        } else {
            Picasso.with(App.getContext()).load(R.mipmap.mine_logined).transform(new CircleTransformation()).into(mUserIconImg);
            mLoginNoTv.setVisibility(View.GONE);
            mLoginYes.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @OnClick(R.id.mine_login_status)
    public void login() {
        toLogin();
    }

    @OnClick(R.id.mine_user_info)
    public void userInfo() {
        toLogin();
    }

    @OnClick(R.id.mine_love_commodity)
    public void loveCommodity() {
        //喜爱单品
    }

    @OnClick(R.id.mine_talk)
    public void myTalk() {
        //我的说说
    }

    @OnClick(R.id.mine_about_us)
    public void aboutUs() {
        //关于我们
    }

    @OnClick(R.id.mine_clear_cache)
    public void clearCache() {
        //清除缓存
    }

    private void toLogin() {

        if (App.user == null) {
            intent2Activity(LoginActivity.class);
        } else {
            intent2Activity(UserInfoActivity.class);
        }
    }
}
