package com.mahao.alex.yingmi.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CategoryActorTypeAdapter;
import com.mahao.alex.yingmi.ui.adapter.CategoryProductionTypeAdapter;
import com.mahao.alex.yingmi.ui.recycle.RecycleRefreshController;
import com.mahao.alex.yingmi.utils.Tt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/26.
 */
public class CategoryActorFragment extends BaseFragment {

    public static CategoryActorFragment newInstance(String homeTown) {
        CategoryActorFragment fragment = new CategoryActorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("homeTown", homeTown);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean isLoadMore = true;


    @Bind(R.id.category_production_child_recycle)
    RecyclerView mRecycleView;

    @Bind(R.id.category_production_child_refresh)
    SwipeRefreshLayout refreshLayout;

    private String homeTown;

    private List<Actor> mActors = new ArrayList<>();

    private CategoryActorTypeAdapter mAdapter;

    private int page = 1;

    private int pageSize = 10;

    //是否正在请求数据
    private boolean isNet = true;


    @Override
    protected void afterCreate() {
        homeTown = getArguments().getString("homeTown");

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new CategoryActorTypeAdapter(mActors);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        requestProduction();


        mRecycleView.setOnScrollListener(new RecycleRefreshController() {
            @Override
            public void loadMoreData() {
                if (mActors.size() < pageSize || isNet) {
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

        if(!isLoadMore){
            //无法加载更多，数据已经结束了。
            return;
        }

        isNet = true;


        RetrofitManager.getInstance()
                .getActorByType(homeTown, page + "", pageSize + "")
                .subscribe(new ProgressSubscriber<List<Actor>>(getActivity()) {
                    @Override
                    public void onNext(List<Actor> actors) {

                        mAdapter.addData(actors);
                        isNet = false;
                        page++;

                        if (actors.size()<pageSize){
                            isLoadMore = false;
                        }
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
                .getActorByType(homeTown, page + "", pageSize + "")
                .subscribe(new ResultSubscriber<List<Actor>>() {
                    @Override
                    public void onNext(List<Actor> actors) {
                        mAdapter.refresh(actors);
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
