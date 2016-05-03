package com.mahao.alex.yingmi.ui.activity;

import android.support.v7.widget.RecyclerView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.bean.Talk;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.TalkAdapter;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Alex_MaHao on 2016/5/1.
 */
public class MyTalkActivity extends BaseActivity {


    @Bind(R.id.titlebar)
    TitleBar titleBar;


    @Bind(R.id.talk_social_recycle)
    RecyclerView mRecycle;

    private List<Talk> mTalks = new ArrayList<>();

    private TalkAdapter mAdapter;


    @Override
    public void afterCreate() {


        titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }

        });

        RecycleUtils.initVerticalRecyle(mRecycle);

        mAdapter = new TalkAdapter(mTalks);

        mRecycle.setAdapter(mAdapter);

        requestTalk();
    }


    private void requestTalk() {
        RetrofitManager.getInstance()
                .getTalkByUserId(App.user.getUserId()+"")
                .subscribe(new ProgressSubscriber<List<Talk>>() {
                    @Override
                    public void onNext(List<Talk> talks) {
                        mAdapter.refresh(talks);
                    }


                });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_talk;
    }
}
