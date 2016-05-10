package com.mahao.alex.yingmi.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CategoryProductionTypeAdapter;
import com.mahao.alex.yingmi.utils.Tt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/26.
 */
public class CategoryProductionFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    private boolean isLoadMore = true;

    public static CategoryProductionFragment newInstance(String typeName) {
        CategoryProductionFragment fragment = new CategoryProductionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("typeName", typeName);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Bind(R.id.swipe_target)
    RecyclerView mRecycleView;

    @Bind(R.id.category_production_child_refresh)
    SwipeToLoadLayout refreshLayout;

    private String typeName;

    private List<Production> mProductions = new ArrayList<>();

    private CategoryProductionTypeAdapter mAdapter;

    private int page = 1;

    private int pageSize = 10;

    @Override
    protected void afterCreate() {
        typeName = getArguments().getString("typeName");

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new CategoryProductionTypeAdapter(mProductions);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        requestProduction();

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);




    }

    private void addData() {

        RetrofitManager.getInstance()
                .getProductionByType(typeName, page + "", pageSize + "")
                .subscribe(new ProgressSubscriber<List<Production>>() {
                    @Override
                    public void onNext(List<Production> productions) {

                        mAdapter.addData(productions);

                        if (productions.size()<pageSize){
                            isLoadMore = false;
                        }
                        page++;
                        refreshLayout.setLoadingMore(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refreshLayout.setLoadingMore(false);
                    }
                });
    }

    private void requestProduction() {

        page = 1;
        RetrofitManager.getInstance()
                .getProductionByType(typeName, page + "", pageSize + "")
                .subscribe(new ResultSubscriber<List<Production>>() {
                    @Override
                    public void onNext(List<Production> productions) {
                        mAdapter.refresh(productions);
                        page++;
                        refreshLayout.setRefreshing(false);
                        isLoadMore = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category_prodcution;
    }

    @Override
    public void onRefresh() {
        requestProduction();
    }

    @Override
    public void onLoadMore() {

        if(!isLoadMore){
            //无法加载更多，数据已经结束了。
            refreshLayout.setLoadingMore(false);
            Tt.showShort("到底啦");
            return;
        }

        addData();
    }
}
