package com.mahao.alex.yingmi.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.fragment.ProductionDetailCommodityFragment;
import com.mahao.alex.yingmi.ui.fragment.ProductionDetailDescFragment;
import com.mahao.alex.yingmi.utils.BitmapUtils;
import com.mahao.alex.yingmi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 电影详情页
 * Created by mdw on 2016/4/27.
 */
public class ProducitonDetailActivity extends BaseActivity {

    private Production mProduction;

    private String productinId;

    private String productionName;

    @Bind(R.id.production_detial_top_bg)
    ImageView mTopBg;

    @Bind(R.id.production_detial_top_poster)
    ImageView mPoster;

    @Bind(R.id.production_detial_top_type)
    TextView mTypeView;

    @Bind(R.id.production_detial_top_language)
    TextView mLanguageView;

    @Bind(R.id.production_detial_top_time)
    TextView mTimeView;

    @Bind(R.id.titlebar)
    TitleBar mTitleBar;

    @Bind(R.id.production_detail_tab)
    TabLayout tabLayout;

    @Bind(R.id.production_detail_vp)
    ViewPager viewPager;

    private String[]  mTab = {"单品","资料"};

    private List<Fragment> fragmentList = new ArrayList<>();

    private MyAdapter mAdapter;
    @Override
    public void afterCreate() {

        productionName = getIntent().getStringExtra(Constant.PRODUCTION_NAME);

        productinId = getIntent().getStringExtra(Constant.PRODUCTION_ID);

        initTitleBar();

        requesProduction();


    }

    private void requesProduction() {
        RetrofitManager.getInstance()
                .getProduction(productinId)
                .subscribe(new ProgressSubscriber<Production>() {
                    @Override
                    public void onNext(Production production) {
                        mProduction =production;

                        initTopView();

                        initBottom();
                    }
                });
    }

    private void initBottom() {
        fragmentList.add(ProductionDetailCommodityFragment.getInstance(mProduction.getProductionInfoId()));
        fragmentList.add(ProductionDetailDescFragment.getInstance(mProduction));

        mAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


    }

    private void initTitleBar() {
        mTitleBar.setCenterText(productionName);
    }

    private void initTopView() {


        BitmapUtils.loadImage(mTopBg,mProduction.getFileImagePath(),BitmapUtils.GAOSI);

        BitmapUtils.loadImage(mPoster,mProduction.getFileImagePath());

        mTypeView.setText(mProduction.getTypeName());

        mLanguageView.setText(mProduction.getLanguage());

        mTimeView.setText(mProduction.getProduceYear());

        mTitleBar.setCenterText(mProduction.getFileName());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_production_detail;
    }


    class MyAdapter extends FragmentPagerAdapter{

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
