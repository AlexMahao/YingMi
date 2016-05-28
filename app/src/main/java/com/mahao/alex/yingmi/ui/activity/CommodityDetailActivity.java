package com.mahao.alex.yingmi.ui.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.bean.CommodityBean;
import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Comment;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.bean.Theme;
import com.mahao.alex.yingmi.db.CommodityRepository;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CommentAdapter;
import com.mahao.alex.yingmi.ui.adapter.CommodityAdapter;
import com.mahao.alex.yingmi.utils.BitmapUtils;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.widget.BuyDialog;
import com.mahao.alex.yingmi.widget.MyScrollView;
import com.mahao.alex.yingmi.widget.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by mdw on 2016/4/28.
 */
public class CommodityDetailActivity extends BaseActivity {

    private String commodityId;

    private Theme mTheme;

    private Commodity mCommodity;

    @Bind(R.id.commodity_detail_top_img)
    ImageView mTopImg;

    @Bind(R.id.commodity_detail_recom_recycle)
    RecyclerView mRecomRecycle;

    @Bind(R.id.commodity_detail_cname_tv)
    TextView mCnameTv;

    @Bind(R.id.commodity_detail_price_tv)
    TextView mPriceTv;

    @Bind(R.id.commodity_detail_name_tv)
    TextView mNameTv;

    @Bind(R.id.commodity_detail_desc_tv)
    TextView mDescTv;

    @Bind(R.id.commodity_detail_them_img)
    ImageView mThemeImg;

    @Bind(R.id.commodity_detail_movie_tv)
    TextView mMovieTv;

    @Bind(R.id.commodity_detail_actor_tv)
    TextView mActorTv;

    @Bind(R.id.titlebar)
    TitleBar titleBar;

    @Bind(R.id.scroll)
    MyScrollView scrollView;


    @Bind(R.id.commodity_detail_like)
    ImageView mLikeImg;

    private CommodityBean commodityBean;


    private CommodityAdapter mCommodityAdapter;

    @Override
    public void afterCreate() {
        titleBar.setAlpha(0);
        titleBar.setCenterText("物品");

        commodityId = getIntent().getStringExtra(Constant.COMMODITY_ID);

        Log.i("info", commodityId);
        requestTheme();


        initLove();


        initComment();


        requestComment();
    }

    private void initLove() {
      commodityBean = CommodityRepository.getCommodityForId(this,commodityId);

        if (commodityBean == null) {
            mLikeImg.setImageResource(R.mipmap.detail_icon_like_normal);
        } else {
            mLikeImg.setImageResource(R.mipmap.detail_icon_like_highlight);
        }

    }

    private void requestTheme() {
        RetrofitManager.getInstance().getThemeByCommodity(commodityId)
                .subscribe(new ProgressSubscriber<Theme>() {
                    @Override
                    public void onNext(Theme theme) {
                        mTheme = theme;
                        for (int i = 0; i < mTheme.getList().size(); i++) {

                            if (mTheme.getList().get(i).getCommodityId().equals(commodityId)) {
                                mCommodity = mTheme.getList().get(i);
                                mTheme.getList().remove(i);
                                break;
                            }
                        }

                        initView();
                    }


                });
    }

    private void initView() {
        scrollView.setScrollChangeListener(new MyScrollView.ScrollChangeListener() {
            @Override
            public void scrollY(int y) {
                if (y <= 1000 && y >= 0) {
                    titleBar.setAlpha(y / 1000f);
                }
            }
        });

        BitmapUtils.loadImage(mTopImg, mCommodity.getCommodityImagePath());


        //同款
        mCommodityAdapter = new CommodityAdapter(mTheme.getList(), this);
        RecycleUtils.initHorizontalRecyle(mRecomRecycle);

        mRecomRecycle.setAdapter(mCommodityAdapter);

        //
        mCnameTv.setText(mCommodity.getCommodityName());

        String text = "";
        if (mCommodity.getCurrency().equals("人民币")) {
            text = text + "￥";
        } else {
            text = text + "$";
        }
        mPriceTv.setText(text + " " + mCommodity.getPrice());

        //
        mNameTv.setText("品牌：" + mCommodity.getName());

        //
        mDescTv.setText("物品描述：" + mCommodity.getCommodityDesc());

        BitmapUtils.loadImage(mThemeImg, mCommodity.getBigImagePath());

        /*mMovieTv.setText(mCommodity.getProductionId());

        mActorTv.setText(mCommodity.getActorId());*/
    }


    @OnClick(R.id.commodity_detail_movie_rl)
    public void toMovie() {
        Intent intent = new Intent(this, ProducitonDetailActivity.class);
        intent.putExtra(Constant.PRODUCTION_ID, mCommodity.getProductionId());
        intent.putExtra(Constant.PRODUCTION_NAME, mCommodity.getProductionId());
        startActivity(intent);
    }

    @OnClick(R.id.commodity_detail_actor_rl)
    public void toActor() {
        Intent intent = new Intent(this, ActorDetailActivity.class);
        intent.putExtra(Constant.ACTOR_ID, mCommodity.getActorId());
        intent.putExtra(Constant.ACTOR_NAME, mCommodity.getActorId());
        startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_commodity_detail;
    }

    @OnClick(R.id.commodity_detail_like)
    public void myLike() {
        //喜欢
        if (commodityBean == null){
            commodityBean = new CommodityBean();
            commodityBean.setCommodityId(mCommodity.getCommodityId());
            commodityBean.setCommodityImagePath(mCommodity.getCommodityImagePath());
            commodityBean.setCommodityName(mCommodity.getCommodityName());
            CommodityRepository.insertOrUpdate(getApplicationContext(),commodityBean);
            Tt.showLong("已收藏");
            mLikeImg.setImageResource(R.mipmap.detail_icon_like_highlight);
        }else{
            CommodityRepository.deleteCommodityWithId(getApplicationContext(),mCommodity.getCommodityId());
            Tt.showLong("取消收藏");
            mLikeImg.setImageResource(R.mipmap.detail_icon_like_normal);
            commodityBean = null;
        }

    }

    @OnClick(R.id.commodity_detail_buy)
    public void buy() {
        //现在购买

        if (App.user == null) {
            Tt.showShort("登录之后才能购买");
            return;
        }


        BuyDialog dialog = new BuyDialog(this, new BuyDialog.OnBuyDialogListener() {
            @Override
            public void go() {
                Intent intent = new Intent(CommodityDetailActivity.this, WebActivity.class);
                intent.putExtra(Constant.WEB_LINK, mCommodity.getLinkPath());
                intent.putExtra(Constant.WEB_TITLE, mCommodity.getCommodityName());
                startActivity(intent);
            }
        });
        dialog.show();

    }

    @OnClick(R.id.commodity_detail_top_img)
    public void showImg() {
        Intent intent = new Intent(this, ShowImageActivity.class);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(mCommodity.getCommodityImagePath());
        intent.putStringArrayListExtra(Constant.SHOW_IMG_URL, arrayList);
        startActivity(intent);
    }



    //  -----  评论列表


    @Bind(R.id.comment_recycle)
    RecyclerView mCommentRecycle;

    @Bind(R.id.comment_add_et)
    EditText mCommentAddEt;

    @Bind(R.id.comment_no)
    TextView mCommentNo;

    private List<Comment> mComments = new ArrayList<>();

    private CommentAdapter mCommentAdapter;


    private void initComment() {
        mCommentAdapter = new CommentAdapter(mComments);

        RecycleUtils.initVerticalRecyle(mCommentRecycle);

        mCommentRecycle.setAdapter(mCommentAdapter);
    }


    private void requestComment() {
        RetrofitManager.getInstance()
                .getComment(Constant.COMMENT_TYPE_COMMODITY,commodityId+ "")
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
                .addComment(App.user.getUserId() + "", time, comment, Constant.COMMENT_TYPE_COMMODITY,commodityId + "")

                .flatMap(new Func1<String, Observable<List<Comment>>>() {
                    @Override
                    public Observable<List<Comment>> call(String s) {
                        return RetrofitManager.getInstance().getComment(Constant.COMMENT_TYPE_COMMODITY, commodityId+"");
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
