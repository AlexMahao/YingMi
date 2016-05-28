package com.mahao.alex.yingmi.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.fragment.ActorDetailCommodityFragment;
import com.mahao.alex.yingmi.ui.fragment.ActorDetailDescFragment;
import com.mahao.alex.yingmi.utils.BitmapUtils;
import com.mahao.alex.yingmi.widget.TitleBar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 电影详情页
 * Created by mdw on 2016/4/27.
 */
public class ActorDetailActivity extends BaseActivity {

    private Actor mActor;

    private String actorId;

    private String actorName;

    @Bind(R.id.actor_detial_top_bg)
    ImageView mTopBg;

    @Bind(R.id.actor_detial_top_poster)
    ImageView mPoster;

    @Bind(R.id.actor_detial_top_year)
    TextView mYearView;

    @Bind(R.id.actor_detial_top_hometown)
    TextView mHomeTownView;

    @Bind(R.id.actor_detial_top_sex)
    TextView mSexView;

    @Bind(R.id.actor_detial_top_xingzuo)
    TextView mXingZuoView;

    @Bind(R.id.titlebar)
    TitleBar mTitleBar;

    @Bind(R.id.actor_detail_tab)
    TabLayout tabLayout;

    @Bind(R.id.actor_detail_vp)
    ViewPager viewPager;

    private String[] mTab = {"单品", "资料"};

    private List<Fragment> fragmentList = new ArrayList<>();

    private MyAdapter mAdapter;

    @Override
    public void afterCreate() {

        actorId = getIntent().getStringExtra(Constant.ACTOR_ID);

        actorName = getIntent().getStringExtra(Constant.ACTOR_NAME);

        initTitleBar();

        requestActor();

    }

    private void requestActor() {
        RetrofitManager.getInstance()
                .getActor(actorId)
                .subscribe(new ProgressSubscriber<Actor>() {
                    @Override
                    public void onNext(Actor actor) {

                        mActor = actor;

                        initTopView();

                        initBottom();
                    }
                });
    }

    private void initBottom() {
        fragmentList.add(ActorDetailCommodityFragment.getInstance(mActor.getActorId()));
        fragmentList.add(ActorDetailDescFragment.getInstance(mActor));

        mAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


    }

    private void initTitleBar() {
        mTitleBar.setCenterText(actorName);
    }

    private void initTopView() {
        BitmapUtils.loadImage(mTopBg, mActor.getActorImagePath(), BitmapUtils.GAOSI);

        BitmapUtils.loadImage(mPoster, mActor.getActorImagePath());
        String time = mActor.getBirthDay();
        mYearView.setText(time.substring(0, time.indexOf("T")));

        if (TextUtils.isEmpty(mActor.getHomeTown())) {
            mHomeTownView.setText("未知");
        } else {
            mHomeTownView.setText(mActor.getHomeTown());
        }


        //mSexView.setText("未知");

        mXingZuoView.setText(mActor.getConstellation_CN());

        mTitleBar.setCenterText(mActor.getActorName());

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_actor_detail;
    }


    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTab[position];
        }

        @Override
        public int getCount() {
            return mTab.length;
        }
    }
}
