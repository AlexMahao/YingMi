package com.mahao.alex.yingmi.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CategoryProductionTypeAdapter;
import com.mahao.alex.yingmi.ui.recycle.RecycleRefreshController;
import com.mahao.alex.yingmi.utils.Tt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/26.
 */
public class CategoryProductionFragment extends BaseFragment {

    public static CategoryProductionFragment newInstance(String typeName) {
        CategoryProductionFragment fragment = new CategoryProductionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("typeName", typeName);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Bind(R.id.category_production_child_recycle)
    RecyclerView mRecycleView;

    @Bind(R.id.category_production_child_refresh)
    SwipeRefreshLayout refreshLayout;

    private String typeName;

    private List<Production> mProductions = new ArrayList<>();

    private CategoryProductionTypeAdapter mAdapter;

    private int page = 1;

    private int pageSize = 10;

    //是否正在请求数据
    private boolean isNet = true;


    @Override
    protected void afterCreate() {
        typeName = getArguments().getString("typeName");

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new CategoryProductionTypeAdapter(mProductions);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        requestProduction();


        mRecycleView.setOnScrollListener(new RecycleRefreshController() {
            @Override
            public void loadMoreData() {
                if (mProductions.size() < pageSize || isNet) {
                    //没有更多数据了
                } else {
                    addData();
                }
            }
        });


        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isNet) {
                    requestProduction();
                } else {
                    Tt.showShort("不要心急呦~~~");
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void addData() {
        Log.i("info", "add-------data");
        isNet = true;
        RetrofitManager.getInstance()
                .getProductionByType(typeName, page + "", pageSize + "")
                .subscribe(new ProgressSubscriber<List<Production>>() {
                    @Override
                    public void onNext(List<Production> productions) {
                        Log.i("info",productions.toString());
                        mAdapter.addData(productions);
                        isNet = false;
                        page++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        isNet = false;
                    }
                });
    }

    private void requestProduction() {
        isNet = true;
        page = 1;
        RetrofitManager.getInstance()
                .getProductionByType(typeName, page + "", pageSize + "")
                .subscribe(new ResultSubscriber<List<Production>>() {
                    @Override
                    public void onNext(List<Production> productions) {
                        mAdapter.refresh(productions);
                        isNet = false;
                        page++;
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        isNet = false;
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category_prodcution;
    }
}
