package com.mahao.alex.yingmi.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Talk;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.activity.AddTalkActvity;
import com.mahao.alex.yingmi.ui.adapter.TalkAdapter;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/19.
 */
public class SocialFragment extends BaseFragment {


    @Bind(R.id.titlebar)
    TitleBar titleBar;


    @Bind(R.id.talk_social_recycle)
    RecyclerView mRecycle;

    @Bind(R.id.talk_refresh)
    SwipeRefreshLayout refreshLayout;

    private List<Talk> mTalks = new ArrayList<>();

    private TalkAdapter mAdapter;


    int page = 1;

    int pageSize = 10;


    @Override
    protected void afterCreate() {

        initTitle();

        RecycleUtils.initVerticalRecyle(mRecycle);

        mAdapter = new TalkAdapter(mTalks);

        mRecycle.setAdapter(mAdapter);

        initResfrsh();

       requestTalk();
    }

    private void initResfrsh() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //延迟加载，显示刷新进度条
                        requestTalk();

                    }
                },2000);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void requestTalk() {
        RetrofitManager.getInstance()
                .getTalk(page+"",pageSize+"")
                .subscribe(new ResultSubscriber<List<Talk>>() {
                    @Override
                    public void onNext(List<Talk> talks) {

                        mAdapter.refresh(talks);
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void initTitle() {
        titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                if(App.user==null){
                    Tt.showShort("请先登录");
                    return;
                }else if(App.user.getMobilePhoneNumber().equals(App.user.getUsername())){
                    Tt.showShort("设置昵称之后才能发布说说哦");
                    return;
                }
                intent2Activity(AddTalkActvity.class);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_social;
    }
}
