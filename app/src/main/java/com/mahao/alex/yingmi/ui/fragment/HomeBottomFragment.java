package com.mahao.alex.yingmi.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.HomeBottomCommodityAdaptetr;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/25.
 */
public class HomeBottomFragment extends BaseFragment {

    @Bind(R.id.home_bottom_commodity_recycle)
    RecyclerView mRecycle;


    private List<Commodity> mCommoditylist;


    int page = 1;
    int pageSize = 10;
    private HomeBottomCommodityAdaptetr mCommodityAdapter;

    @Override
    protected void afterCreate() {

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        mRecycle.setLayoutManager(layoutManager);

        //mRecycle.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));

        mCommoditylist = new ArrayList<>();

        mCommodityAdapter = new HomeBottomCommodityAdaptetr(mCommoditylist);
        mRecycle.setAdapter(mCommodityAdapter);

        getHotCommodityList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_bottom;
    }



    public void getHotCommodityList() {
         RetrofitManager.getInstance()
                .getHotCommodity(page+"",pageSize+"")
                .subscribe(new ResultSubscriber<List<Commodity>>(){
                    @Override
                    public void onNext(List<Commodity> o) {
                       mCommodityAdapter.refresh(o);
                    }
                });
    }
}
