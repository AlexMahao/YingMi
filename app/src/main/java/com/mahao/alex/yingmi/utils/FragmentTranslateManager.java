package com.mahao.alex.yingmi.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by mdw on 2016/4/19.
 */
public class FragmentTranslateManager implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg_menu;

    private int mFragmentLayoutId;

    private FragmentManager mFragmentMgr;

    private List<Fragment> mFragments;

    public FragmentTranslateManager(RadioGroup rg_menu, FragmentManager mFragmentMgr, List<Fragment> mFragments, int mFragmentLayoutId) {
        this.rg_menu = rg_menu;
        this.mFragmentMgr = mFragmentMgr;
        this.mFragments = mFragments;
        this.mFragmentLayoutId = mFragmentLayoutId;
    }

    public  void init() {
        rg_menu.setOnCheckedChangeListener(this);
        ((RadioButton) rg_menu.getChildAt(0)).setChecked(true);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for(int i = 0;i<group.getChildCount();i++){
            if(group.getChildAt(i).getId()==checkedId){
                changeFragment(i);
            }
        }
    }

    /**
     * 改变fragment
     * @param position
     */
    private void changeFragment(int position) {
        Fragment fragment = mFragments.get(position);
        if(!fragment.isAdded()){
            mFragmentMgr.beginTransaction().add(mFragmentLayoutId,fragment).commit();
        }

        for(int i=0;i<mFragments.size();i++){
            if(i==position){
                mFragmentMgr.beginTransaction().show(mFragments.get(i)).commit();
            }else{
                mFragmentMgr.beginTransaction().hide(mFragments.get(i)).commit();
            }
        }

    }
}
