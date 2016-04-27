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
import com.mahao.alex.yingmi.ui.adapter.CategoryProductionAdapter;
import com.mahao.alex.yingmi.ui.adapter.DetailPicAdapter;
import com.mahao.alex.yingmi.utils.RecycleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/27.
 */
public class ActorDetailDescFragment extends BaseFragment {

    public static ActorDetailDescFragment getInstance(Actor actor) {
        ActorDetailDescFragment fragment = new ActorDetailDescFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("actor",actor);
        fragment.setArguments(bundle);

        return fragment;
    }

    private Actor actor;

    @Bind(R.id.detail_desc_desc)
    TextView mDeitalDescTextView;

    @Bind(R.id.detail_desc_pic)
    RecyclerView mPicRecycleView;

    @Bind(R.id.detail_desc_actor)
    RecyclerView mActorRecycleView;

    private List<Production> mProduction = new ArrayList<>();

    private CategoryProductionAdapter mProductionAdapter;

    private List<String> urls = new ArrayList<>();

    private DetailPicAdapter mPicAdapter;

    @Override
    protected void afterCreate() {
        actor = getArguments().getParcelable("actor");
        mDeitalDescTextView.setText(actor.getActorDesc());

       initPic();

        initProduciton();
    }


    private void initProduciton() {
        RecycleUtils.initHorizontalRecyle(mActorRecycleView);
        mProductionAdapter = new CategoryProductionAdapter(mProduction);
        mActorRecycleView.setAdapter(mProductionAdapter);

        requestProduction();

    }

    private void requestProduction() {
        RetrofitManager.getInstance()
                .getProductionByActor(actor.getActorId())
                .subscribe(new ResultSubscriber<List<Production>>(){
                    @Override
                    public void onNext(List<Production> o) {
                        mProductionAdapter.refresh(o);
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
                .getPicByActor(actor.getActorId())
                .subscribe(new ResultSubscriber<List<String>>(){
                    @Override
                    public void onNext(List<String> o) {
                        mPicAdapter.refresh(o);
                    }
                });
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_actor_detail_desc;
    }
}
