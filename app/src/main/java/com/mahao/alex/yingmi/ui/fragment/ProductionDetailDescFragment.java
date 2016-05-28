package com.mahao.alex.yingmi.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.bean.Comment;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CategoryActorAdapter;
import com.mahao.alex.yingmi.ui.adapter.CommentAdapter;
import com.mahao.alex.yingmi.ui.adapter.DetailPicAdapter;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.utils.Tt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Func1;

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

    @Bind(R.id.comment_no)
    TextView mCommentNo;

    @Bind(R.id.comment_recycle)
    RecyclerView mCommentRecycle;

    @Bind(R.id.comment_add_et)
    EditText mCommentAddEt;

    private List<Actor> mActor = new ArrayList<>();

    private CategoryActorAdapter mActorAdapter;

    private List<String> urls = new ArrayList<>();

    private DetailPicAdapter mPicAdapter;

    private List<Comment> mComments = new ArrayList<>();

    private CommentAdapter mCommentAdapter;

    @Override
    protected void afterCreate() {
        production = getArguments().getParcelable("production");
        mDeitalDescTextView.setText(production.getProductionDesc());

        initPic();

        initActor();


        initComment();


        requestComment();
    }

    private void initComment() {
        mCommentAdapter = new CommentAdapter(mComments);

        RecycleUtils.initVerticalRecyle(mCommentRecycle);

        mCommentRecycle.setAdapter(mCommentAdapter);
    }

    private void requestComment() {
        RetrofitManager.getInstance()
                .getComment(Constant.COMMENT_TYPE_PRODUCITON,production.getProductionInfoId() + "")
                .subscribe(new ResultSubscriber<List<Comment>>() {
                    @Override
                    public void onNext(List<Comment> comments) {
                        if (comments==null||comments.size()==0)
                            mCommentNo.setVisibility(View.VISIBLE);
                        else
                            mCommentNo.setVisibility(View.GONE);

                            mCommentAdapter.refresh(comments);
                    }
                });
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


    @OnClick(R.id.comment_add_btn)
    public void addComment() {
        String comment = mCommentAddEt.getText().toString().trim();

        if (TextUtils.isEmpty(comment)) {
            Tt.showShort("请输入评论内容");
            return;
        }

        if (App.user == null) {
            Tt.showShort("登录之后才能发表评论");
            return;
        }

        if(App.user.getMobilePhoneNumber().equals(App.user.getUsername())){
            Tt.showShort("设置昵称之后才能发布说说哦");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));

        RetrofitManager.getInstance()
                .addComment(App.user.getUserId() + "", time, comment, Constant.COMMENT_TYPE_PRODUCITON, production.getProductionInfoId() + "")
                .flatMap(new Func1<String, Observable<List<Comment>>>() {
                    @Override
                    public Observable<List<Comment>> call(String s) {
                        return RetrofitManager.getInstance().getComment(Constant.COMMENT_TYPE_PRODUCITON, production.getProductionInfoId() + "");
                    }
                })
                .subscribe(new ProgressSubscriber<List<Comment>>() {
                    @Override
                    public void onNext(List<Comment> comments) {
                        if (comments==null||comments.size()==0)
                            mCommentNo.setVisibility(View.VISIBLE);
                        else
                            mCommentNo.setVisibility(View.GONE);

                        mCommentAdapter.refresh(comments);
                    }
                });

    }
}
