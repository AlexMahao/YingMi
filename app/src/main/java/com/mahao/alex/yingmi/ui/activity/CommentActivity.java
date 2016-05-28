package com.mahao.alex.yingmi.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Comment;
import com.mahao.alex.yingmi.bean.Talk;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CommentAdapter;
import com.mahao.alex.yingmi.utils.CircleTransformation;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.widget.TitleBar;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mdw on 2016/5/3.
 */
public class CommentActivity extends BaseActivity {

    private Talk talk;

    @Bind(R.id.social_talk_username_tv)
    TextView mTalkUserName;

    @Bind(R.id.social_talk_time_tv)
    TextView mTalkTime;

    @Bind(R.id.social_talk_content_tv)
    TextView mTalkContent;

    @Bind(R.id.social_talk_click_tv)
    TextView mTalkclick;

    @Bind(R.id.social_talk_good_tv)
    TextView mTalkGood;

    @Bind(R.id.social_talk_comment_tv)
    TextView mTalkComment;

    @Bind(R.id.social_talk_icon_tv)
    ImageView mTalkIcon;

    @Bind(R.id.titlebar)
    TitleBar titleBar;

    @Bind(R.id.comment_add_et)
    EditText mCommentAddEt;

    @Bind(R.id.comment_recycle)
    RecyclerView mCommentRecycle;

    private List<Comment> mComments = new ArrayList<>();

    private CommentAdapter mCommentAdapter;

    @Override
    public void afterCreate() {
        talk = getIntent().getParcelableExtra(Constant.TALK);

        titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }
        });

        requestAddTalkClick();

        initTalk();

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
                .getComment(Constant.COMMENT_TYPE_TALK, talk.getTalkId() + "")
                .subscribe(new ResultSubscriber<List<Comment>>() {
                    @Override
                    public void onNext(List<Comment> comments) {
                        mCommentAdapter.refresh(comments);
                    }
                });
    }

    private void initTalk() {
        mTalkclick.setText(talk.getClickCount() + "");
        mTalkComment.setText(talk.getCommentCount() + "");
        mTalkContent.setText(talk.getTalkContent() + "");
        mTalkGood.setText(talk.getGoodCount() + "");
        mTalkTime.setText(talk.getTalkTime());
        mTalkUserName.setText(talk.getUserName());
        if (TextUtils.isEmpty(talk.getUserIcon()))
            Picasso.with(App.getContext()).load(R.mipmap.mine_logined).transform(new CircleTransformation(12)).into(mTalkIcon);
        else
            Picasso.with(App.getContext()).load(talk.getUserIcon()).transform(new CircleTransformation(12)).into(mTalkIcon);

    }


    private void requestAddTalkClick() {
        RetrofitManager.getInstance()
                .addTalkClickCount(talk.getTalkId() + "")
                .subscribe(new ResultSubscriber<String>() {
                    @Override
                    public void onNext(String o) {
                        // Tt.showShort(o);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
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
                .addComment(App.user.getUserId() + "", time, comment, Constant.COMMENT_TYPE_TALK, talk.getTalkId() + "")
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return RetrofitManager.getInstance().addTalkCommentCount(talk.getTalkId()+"");
                    }
                })
                .flatMap(new Func1<String, Observable<List<Comment>>>() {
                    @Override
                    public Observable<List<Comment>> call(String s) {
                        return RetrofitManager.getInstance().getComment(Constant.COMMENT_TYPE_TALK, talk.getTalkId() + "");
                    }
                })
                .subscribe(new ProgressSubscriber<List<Comment>>() {
                    @Override
                    public void onNext(List<Comment> comments) {
                        mCommentAdapter.refresh(comments);
                        mCommentAddEt.setText("");
                    }
                });

    }
}
