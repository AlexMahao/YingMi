package com.mahao.alex.yingmi.ui.fragment;

import android.support.v7.widget.RecyclerView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.bean.Theme;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.activity.CategoryActorActivity;
import com.mahao.alex.yingmi.ui.activity.CategoryProductionActivity;
import com.mahao.alex.yingmi.ui.activity.CategoryThemeActivity;
import com.mahao.alex.yingmi.ui.adapter.BaseRecycleAdapter;
import com.mahao.alex.yingmi.ui.adapter.CategoryActorAdapter;
import com.mahao.alex.yingmi.ui.adapter.CategoryProductionAdapter;
import com.mahao.alex.yingmi.ui.adapter.CategoryThemeAdapter;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mdw on 2016/4/19.
 */
public class CategoryFragment extends BaseFragment {

    @Bind(R.id.category_actor_recycle)
    RecyclerView mActorRecycle;

    @Bind(R.id.category_production_recycle)
    RecyclerView mProductionRecycle;

    @Bind(R.id.category_theme_recycle)
    RecyclerView mThemeRecycle;

    @Bind(R.id.titlebar)
    TitleBar mTitleBar;

    private List<Production> mProductions;

    private List<Actor> mActors;

    private List<Theme> mThemes;

    private CategoryActorAdapter mActorAdapter;

    private CategoryProductionAdapter mProductionAdapter;

    private CategoryThemeAdapter mThemeAdapter;

    @Override
    protected void afterCreate() {
        mTitleBar.setCenterText("热门分类");

        initProduction();

        initActor();

        initTheme();

    }


    private void initTheme() {
        mThemes = new ArrayList<>();

        mThemeAdapter = new CategoryThemeAdapter(mThemes);

        RecycleUtils.initVerticalRecyle(mThemeRecycle);

        mThemeRecycle.setAdapter(mThemeAdapter);

        requestHotTheme();
    }

    private void requestHotTheme() {
        RetrofitManager.getInstance()
                .getHotTheme("1","4")
                .subscribe(new ResultSubscriber<List<Theme>>(){
                    @Override
                    public void onNext(List<Theme> o) {
                        mThemeAdapter.refresh(o);
                    }
                });
    }


    private void initActor() {
        mActors = new ArrayList<>();
        mActorAdapter = new CategoryActorAdapter(mActors);
        RecycleUtils.initHorizontalRecyle(mActorRecycle);
        mActorRecycle.setAdapter(mActorAdapter);

        requestHotActor();
    }

    private void requestHotActor() {
        RetrofitManager.getInstance()
                .getHotActor()
                .subscribe(new ResultSubscriber<List<Actor>>(){
                    @Override
                    public void onNext(List<Actor> o) {

                        mActorAdapter.refresh(o);
                    }
                });
    }

    private void initProduction() {
        mProductions = new ArrayList<>();
        mProductionAdapter = new CategoryProductionAdapter(mProductions);
        RecycleUtils.initHorizontalRecyle(mProductionRecycle);
        mProductionRecycle.setAdapter(mProductionAdapter);

        mProductionAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {


            }
        });
        requestHotProduction();
    }

    private void requestHotProduction() {
        RetrofitManager.getInstance()
                .getHotProduction()
                .subscribe(new ResultSubscriber<List<Production>>(){
                    @Override
                    public void onNext(List<Production> o) {
                        mProductionAdapter.refresh(o);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_category;
    }


    @OnClick(R.id.category_actor_load_more_tv)
    public void actorLoadMore(){
    intent2Activity(CategoryActorActivity.class);
    }

    @OnClick(R.id.category_production_load_more_tv)
    public void productionLoadMore(){
       intent2Activity(CategoryProductionActivity.class);
    }
    @OnClick(R.id.category_theme_load_more_tv)
    public void themeLoadMore(){
        intent2Activity(CategoryThemeActivity.class);
    }

}
