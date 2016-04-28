package com.mahao.alex.yingmi.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.bean.Theme;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CategoryThemeAdapter;
import com.mahao.alex.yingmi.ui.recycle.RecycleRefreshController;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Alex_MaHao on 2016/4/26.
 */
public class CategoryThemeActivity extends BaseActivity {

    @Bind(R.id.titlebar)
    TitleBar titleBar;

    @Bind(R.id.category_theme_child_refresh)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.category_theme_child_recycle)
    RecyclerView mRecycleView;


    private List<Theme> mThemes = new ArrayList<>();

    private CategoryThemeAdapter mAdapter;

    int page = 1;

    int pageSize =10;

    //是否正在请求数据
    private boolean isNet = true;


    @Override
    public void afterCreate() {

        titleBar.setCenterText("更多主题");

        mAdapter = new CategoryThemeAdapter(mThemes);

        RecycleUtils.initVerticalRecyle(mRecycleView);

        mRecycleView.setAdapter(mAdapter);

        requestHotTheme();


        mRecycleView.setOnScrollListener(new RecycleRefreshController() {
            @Override
            public void loadMoreData() {
                if (mThemes.size() < pageSize || isNet) {
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
                    requestHotTheme();
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
                .getHotTheme( page + "", pageSize + "")
                .subscribe(new ProgressSubscriber<List<Theme>>() {
                    @Override
                    public void onNext(List<Theme> themes) {

                        mAdapter.addData(themes);
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


    private void requestHotTheme() {

        isNet = true;

        page = 1;

        RetrofitManager.getInstance()
                .getHotTheme(page+"",pageSize+"")
                .subscribe(new ResultSubscriber<List<Theme>>(){
                    @Override
                    public void onNext(List<Theme> o) {
                        mAdapter.refresh(o);
                        page++;
                        isNet =false;
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
        return R.layout.activity_category_theme;
    }
}
