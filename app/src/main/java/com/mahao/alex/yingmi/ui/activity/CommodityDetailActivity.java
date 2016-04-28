package com.mahao.alex.yingmi.ui.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.bean.Theme;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CommodityAdapter;
import com.mahao.alex.yingmi.utils.BitmapUtils;
import com.mahao.alex.yingmi.utils.RecycleUtils;
import com.mahao.alex.yingmi.widget.BuyDialog;
import com.mahao.alex.yingmi.widget.MyScrollView;
import com.mahao.alex.yingmi.widget.TitleBar;

import butterknife.Bind;
import butterknife.OnClick;

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



    private CommodityAdapter mCommodityAdapter;

    @Override
    public void afterCreate() {
        titleBar.setAlpha(0);
        titleBar.setCenterText("物品详情");

        commodityId = getIntent().getStringExtra(Constant.COMMODITY_ID);

        Log.i("info",commodityId);
        requestTheme();

    }

    private void requestTheme() {
        RetrofitManager.getInstance().getThemeByCommodity(commodityId)
                .subscribe(new ProgressSubscriber<Theme>() {
                    @Override
                    public void onNext(Theme theme) {
                        mTheme = theme;
                        for (int i = 0; i < mTheme.getList().size(); i++) {

                            if (mTheme.getList().get(i).getCommodityId().equals(commodityId)) {
                                    mCommodity =mTheme.getList().get(i);
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
                if(y<=1000&&y>=0){
                    titleBar.setAlpha(y/1000f);
                }
            }
        });

        BitmapUtils.loadImage(mTopImg,mCommodity.getCommodityImagePath());


        //同款
        mCommodityAdapter = new CommodityAdapter(mTheme.getList(),this);
        RecycleUtils.initHorizontalRecyle(mRecomRecycle);

        mRecomRecycle.setAdapter(mCommodityAdapter);

        //
        mCnameTv.setText(mCommodity.getCommodityName());

        String text = "";
        if(mCommodity.getCurrency().equals("人民币")){
            text=text+"￥";
        }else{
            text = text+"$";
        }
        mPriceTv.setText(text+" "+mCommodity.getPrice());

        //
        mNameTv.setText("品牌："+mCommodity.getName());

        //
        mDescTv.setText("物品描述："+mCommodity.getCommodityDesc());

        BitmapUtils.loadImage(mThemeImg,mCommodity.getBigImagePath());

        mMovieTv.setText(mCommodity.getProductionId());

        mActorTv.setText(mCommodity.getActorId());
    }


    @OnClick(R.id.commodity_detail_movie_rl)
    public void toMovie(){
        Intent intent = new Intent(this,ProducitonDetailActivity.class);
        intent.putExtra(Constant.PRODUCTION_ID,mCommodity.getProductionId());
        intent.putExtra(Constant.PRODUCTION_NAME,mCommodity.getProductionId());
        startActivity(intent);
    }

    @OnClick(R.id.commodity_detail_actor_rl)
    public void toActor(){
        Intent intent = new Intent(this,ProducitonDetailActivity.class);
        intent.putExtra(Constant.ACTOR_ID,mCommodity.getActorId());
        intent.putExtra(Constant.ACTOR_NAME,mCommodity.getActorId());
        startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_commodity_detail;
    }


    @OnClick(R.id.commodity_detail_comment)
    public void comment(){
        //评论
    }

    @OnClick(R.id.commodity_detail_like)
    public void myLike(){
        //喜欢
    }

    @OnClick(R.id.commodity_detail_buy)
    public void buy(){
        //现在购买
        BuyDialog dialog = new BuyDialog(this, new BuyDialog.OnBuyDialogListener() {
            @Override
            public void go() {
                Intent intent = new Intent(CommodityDetailActivity.this,WebActivity.class);
                intent.putExtra(Constant.WEB_LINK,mCommodity.getLinkPath());
                startActivity(intent);
            }
        });
        dialog.show();

    }


}
