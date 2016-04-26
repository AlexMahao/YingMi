package com.mahao.alex.yingmi.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.ui.fragment.CategoryActorFragment;
import com.mahao.alex.yingmi.ui.fragment.CategoryProductionFragment;
import com.mahao.alex.yingmi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 电影分类页面
 * Created by mdw on 2016/4/26.
 */
public class CategoryActorActivity extends BaseActivity {

    @Bind(R.id.category_tab)
    TabLayout mTabLayout;

    @Bind(R.id.category_viewpager)
    ViewPager mViewPager;

    @Bind(R.id.titlebar)
    TitleBar titleBar;


    private MyAdapter mAdapter;

    private List<Fragment> fragmentList;


    private String[] mTitle = {
            "全部演员",
            "中国",
            "日韩",
            "欧美",
            };


    @Override
    public void afterCreate() {
        titleBar.setCenterText("演员");


        initArray();


        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    private void initArray() {
        fragmentList = new ArrayList<>();
        for(int i=0;i<mTitle.length;i++){
            fragmentList.add(CategoryActorFragment.newInstance(mTitle[i]));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_production;
    }


    class MyAdapter extends FragmentPagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle[position];
        }

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mTitle.length;
        }
    }
}
