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
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CategoryActorTypeAdapter;
import com.mahao.alex.yingmi.utils.Tt;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/26.
 */
public class CategoryActorFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    public static CategoryActorFragment newInstance(String homeTown) {
        CategoryActorFragment fragment = new CategoryActorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("homeTown", homeTown);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean isLoadMore = true;


    @Bind(R.id.swipe_target)
    RecyclerView mRecycleView;

    @Bind(R.id.category_production_child_refresh)
    SwipeToLoadLayout refreshLayout;

    private String homeTown;

    private List<Actor> mActors = new ArrayList<>();

    private CategoryActorTypeAdapter mAdapter;

    private int page = 1;

    private int pageSize = 10;



    @Override
    protected void afterCreate() {
        homeTown = getArguments().getString("homeTown");

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecycleView.setLayoutManager(layoutManager);
        mAdapter = new CategoryActorTypeAdapter(mActors);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        requestProduction();


        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);



    }

    private void addData() {

        RetrofitManager.getInstance()
                .getActorByType(homeTown, page + "", pageSize + "")
                .subscribe(new ProgressSubscriber<List<Actor>>() {
                    @Override
                    public void onNext(List<Actor> actors) {

                        mAdapter.addData(actors);
                        page++;

                        if (actors.size()<pageSize){
                            isLoadMore = false;
                        }

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
                .getActorByType(homeTown, page + "", pageSize + "")
                .subscribe(new ResultSubscriber<List<Actor>>() {
                    @Override
                    public void onNext(List<Actor> actors) {
                        mAdapter.refresh(actors);
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
