package com.mahao.alex.yingmi.ui.activity;

import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.ui.fragment.CategoryFragment;
import com.mahao.alex.yingmi.ui.fragment.HomeBottomFragment;
import com.mahao.alex.yingmi.ui.fragment.HomeFragment;
import com.mahao.alex.yingmi.ui.fragment.MineFragment;
import com.mahao.alex.yingmi.ui.fragment.SocialFragment;
import com.mahao.alex.yingmi.utils.FragmentTranslateManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 首页
 * Created by mdw on 2016/4/19.
 */
public class HomeActivity extends BaseActivity {

    @Bind(R.id.rg_main_menu)
    RadioGroup rg_mian_menu;



    @Override
    public void afterCreate() {
        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new HomeFragment());
        fragmentList.add(new HomeBottomFragment());
        fragmentList.add(new SocialFragment());
        fragmentList.add(new MineFragment());

        new FragmentTranslateManager(rg_mian_menu,getSupportFragmentManager(),fragmentList,R.id.fragment_main).init();


    }


    @Override
    public int getLayoutId() {

        return R.layout.activity_home;

    }
}
