package com.mahao.alex.yingmi.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.ui.activity.AboutUsActivity;
import com.mahao.alex.yingmi.ui.activity.LikeCommodityActivity;
import com.mahao.alex.yingmi.ui.activity.LoginActivity;
import com.mahao.alex.yingmi.ui.activity.MyTalkActivity;
import com.mahao.alex.yingmi.ui.activity.UserInfoActivity;
import com.mahao.alex.yingmi.utils.CircleTransformation;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.utils.UpdataManager;
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

    @Bind(R.id.mine_user_id_tv)
    TextView mUserId;

    @Bind(R.id.mine_user_name_tv)
    TextView mNameTv;

    @Bind(R.id.scroll)
    ScrollView scroll;

    @Override
    protected void afterCreate() {

        scroll.setOverScrollMode(View.OVER_SCROLL_NEVER);


    }

    @Override
    public void onResume() {
        super.onResume();

        if (App.user == null) {
            Picasso.with(App.getContext()).load(R.mipmap.home_pic_photo_logout).transform(new CircleTransformation()).into(mUserIconImg);
            mLoginNoTv.setVisibility(View.VISIBLE);
            mLoginYes.setVisibility(View.GONE);
        } else {
            if (TextUtils.isEmpty(App.user.getUserIcon()))
                Picasso.with(App.getContext()).load(R.mipmap.mine_logined).transform(new CircleTransformation()).into(mUserIconImg);
            else
                Picasso.with(App.getContext()).load(App.user.getUserIcon()).transform(new CircleTransformation()).into(mUserIconImg);


            mLoginNoTv.setVisibility(View.GONE);
            mLoginYes.setVisibility(View.VISIBLE);
            mUserId.setText(App.user.getUserId() + "");

            if(TextUtils.isEmpty(App.user.getUsername())){
                mNameTv.setText("未设置");
            }else{
                mNameTv.setText(App.user.getUsername());
            }


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

        if (App.user == null)
            Tt.showShort("请先登录");
        else
            intent2Activity(LikeCommodityActivity.class);
    }

    @OnClick(R.id.mine_talk)
    public void myTalk() {
        //我的说说

        if (App.user == null)
            Tt.showShort("请先登录");
        else
            intent2Activity(MyTalkActivity.class);
    }

    @OnClick(R.id.mine_about_us)
    public void aboutUs() {
        //关于我们
        intent2Activity(AboutUsActivity.class);
    }


    private void toLogin() {

        if (App.user == null) {
            intent2Activity(LoginActivity.class);
        } else {
            intent2Activity(UserInfoActivity.class);
        }
    }


    @OnClick(R.id.mine_updata_app)
    public void updateApp(){
        //检查更新
        new UpdataManager().requestVer();
    }
}
