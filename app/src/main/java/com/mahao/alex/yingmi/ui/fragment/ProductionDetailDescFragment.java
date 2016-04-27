package com.mahao.alex.yingmi.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CategoryActorAdapter;
import com.mahao.alex.yingmi.ui.adapter.DetailPicAdapter;
import com.mahao.alex.yingmi.utils.RecycleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/27.
 */
public class ProductionDetailDescFragment extends BaseFragment {

    public static ProductionDetailDescFragment getInstance(Production production) {
        ProductionDetailDescFragment fragment = new ProductionDetailDescFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("production",production);
        fragment.setArguments(bundle);

        return fragment;
    }

    private Production production;

    @Bind(R.id.detail_desc_desc)
    TextView mDeitalDescTextView;

    @Bind(R.id.detail_desc_pic)
    RecyclerView mPicRecycleView;

    @Bind(R.id.detail_desc_actor)
    RecyclerView mActorRecycleView;

    private List<Actor> mActor = new ArrayList<>();

    private CategoryActorAdapter mActorAdapter;

    private List<String> urls = new ArrayList<>();

    private DetailPicAdapter mPicAdapter;

    @Override
    protected void afterCreate() {
        production = getArguments().getParcelable("production");
        mDeitalDescTextView.setText(production.getProductionDesc());

        initPic();

        initActor();
    }

    private void initActor() {
        RecycleUtils.initHorizontalRecyle(mActorRecycleView);
        mActorAdapter = new CategoryActorAdapter(mActor);
        mActorRecycleView.setAdapter(mActorAdapter);

        requestActor();

    }

    private void requestActor() {
        RetrofitManager.getInstance()
                .getActorByProducitonId(production.getProductionInfoId())
                .subscribe(new ResultSubscriber<List<Actor>>(){
                    @Override
                    public void onNext(List<Actor> o) {
                        mActorAdapter.refresh(o);
                    }
                });
    }

    private void initPic() {
        RecycleUtils.initHorizontalRecyle(mPicRecycleView);
        mPicAdapter = new DetailPicAdapter(urls);
        mPicRecycleView.setAdapter(mPicAdapter);

        requestPic();
    }

    private void requestPic() {
        RetrofitManager.getInstance()
                .getPicByProduciton(production.getProductionInfoId())
                .subscribe(new ResultSubscriber<List<String>>(){
                    @Override
                    public void onNext(List<String> o) {
                        mPicAdapter.refresh(o);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_production_detail_desc;
    }
}
