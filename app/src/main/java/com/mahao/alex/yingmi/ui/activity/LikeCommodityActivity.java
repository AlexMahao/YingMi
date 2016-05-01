package com.mahao.alex.yingmi.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.alex.bean.CommodityBean;
import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.db.CommodityRepository;
import com.mahao.alex.yingmi.ui.adapter.LikeCommodityAdapter;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.widget.TitleBar;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Alex_MaHao on 2016/5/1.
 */
public class LikeCommodityActivity extends BaseActivity {


    @Bind(R.id.titlebar)
    TitleBar titleBar;

    @Bind(R.id.like_commodity_recyle)
    RecyclerView mRecycle;

    private List<CommodityBean>  commodityBeen;


    private LikeCommodityAdapter mAdapter;



    @Override
    public void afterCreate() {

        titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }
        });

        commodityBeen = CommodityRepository.findAll(getApplicationContext());

        mAdapter = new LikeCommodityAdapter(commodityBeen);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        mRecycle.setLayoutManager(layoutManager);
        mRecycle.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_like_commodity;
    }
}
