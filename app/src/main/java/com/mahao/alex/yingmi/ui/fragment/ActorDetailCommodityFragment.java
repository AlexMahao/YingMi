package com.mahao.alex.yingmi.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Theme;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.DividerItemDecoration;
import com.mahao.alex.yingmi.ui.adapter.ThemeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/27.
 */
public class ActorDetailCommodityFragment extends BaseFragment {

    private ArrayList<Theme> mThemes;
    private ThemeAdapter mThemeAdapter;

    public static ActorDetailCommodityFragment getInstance(String actorId) {
        ActorDetailCommodityFragment fragment = new ActorDetailCommodityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("actorId", actorId);
        fragment.setArguments(bundle);

        return fragment;
    }

    private String actorId;

    @Bind(R.id.detail_comodity_recycle)
    RecyclerView mRecycleView;

    @Override
    protected void afterCreate() {
        actorId = getArguments().getString("actorId");

        initTheme();

        requestHotTheme();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_production_detail_commodity;
    }

    private void initTheme() {
        mThemes = new ArrayList<>();

        mThemeAdapter  = new ThemeAdapter(mThemes,getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        mRecycleView.setAdapter(mThemeAdapter);
    }

    private void requestHotTheme() {
        RetrofitManager.getInstance().getThemeByActorId(actorId)
                .subscribe(new ResultSubscriber<List<Theme>>(){
                    @Override
                    public void onNext(List<Theme> o) {

                        mThemeAdapter.refresh(o);
                    }
                });
    }

}
