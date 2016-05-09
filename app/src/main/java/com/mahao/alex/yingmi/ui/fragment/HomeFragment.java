package com.mahao.alex.yingmi.ui.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.bean.Theme;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.activity.ProducitonDetailActivity;
import com.mahao.alex.yingmi.ui.adapter.CommodityAdapter;
import com.mahao.alex.yingmi.ui.adapter.DividerItemDecoration;
import com.mahao.alex.yingmi.ui.adapter.HomeShufAdapter;
import com.mahao.alex.yingmi.ui.adapter.ThemeAdapter;
import com.mahao.alex.yingmi.utils.BitmapUtils;
import com.mahao.alex.yingmi.widget.CustomDialog;
import com.mahao.alex.yingmi.widget.TitleBar;
import com.mahao.alex.yingmi.widget.VerticalScrollerLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/19.
 */
public class HomeFragment extends BaseFragment implements HomeShufAdapter.OnPageSelectListener {

    private List<Production> mProductions;

    @Bind(R.id.vp_home_shuf)
    ViewPager vp_home_shuf;

    @Bind(R.id.rl_home_shuf)
    RelativeLayout rl_home_shuf;

    @Bind(R.id.home_vertical_scroll)
    VerticalScrollerLayout scrollView;

    @Bind(R.id.iv_home_shuf_bg)
    ImageView iv_home_shuf_bg;

    @Bind(R.id.titlebar)
    TitleBar titleBar;

    @Bind(R.id.home_commodity_recycleView)
    RecyclerView mHomeCommodityRecycle;

    @Bind(R.id.home_theme_recycle)
    RecyclerView mHomeThemeRecycle;

    @Bind(R.id.home_botton_titlebar)
    TitleBar mBottomTitleBar;

    private HomeShufAdapter mShufAdapter;

    private List<ImageView> mShufImages;

    private CommodityAdapter mCommodityAdapter;

    private List<Commodity> mCommoditys;

    private List<Theme> mThemes;

    private ThemeAdapter mThemeAdapter;


    private CustomDialog dialog ;



    @Override
    protected void afterCreate() {

        mBottomTitleBar.setVisibility(View.GONE);

        mBottomTitleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onCenterClick() {
                scrollView.scrollToTop();
            }
        });


        //初始化电影同款物品
        initShufProducitons();

        //初始化同款物品列表
        initCommodityList();

        initTheme();
        /**
         * 请求热门电影并加载图片
         */
        requestHotProduction();


        requestHotTheme();
    }

    private void initTheme() {
        mThemes = new ArrayList<>();

        mThemeAdapter  = new ThemeAdapter(mThemes,getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHomeThemeRecycle.setLayoutManager(layoutManager);
        mHomeThemeRecycle.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        mHomeThemeRecycle.setAdapter(mThemeAdapter);
    }

    private void requestHotTheme() {
        RetrofitManager.getInstance().getHotTheme("1","2")
                .subscribe(new ResultSubscriber<List<Theme>>(){
                    @Override
                    public void onNext(List<Theme> o) {

                        mThemeAdapter.refresh(o);
                    }
                });
    }


    private void initShufProducitons() {
        mProductions = new ArrayList<>();
        mShufImages = new ArrayList<>();
        mShufAdapter = new HomeShufAdapter(mShufImages);
        mShufAdapter.setOnPageSelectListener(this);

        vp_home_shuf.setAdapter(mShufAdapter);
        vp_home_shuf.addOnPageChangeListener(mShufAdapter);
        vp_home_shuf.setOffscreenPageLimit(5);

        rl_home_shuf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return vp_home_shuf.dispatchTouchEvent(event);
            }
        });


        /**
         * 设置滚动监听
         *
         */
        scrollView.setScrollChangeListener(new VerticalScrollerLayout.ScrollChangeListener() {
            @Override
            public void scrollY(int y) {
                if(y<=1000&&y>=0){
                    titleBar.setAlpha(y/1000f);
                }
            }

            @Override
            public void onScollStateChange(int type) {
                if(type==VerticalScrollerLayout.TOP){
                    titleBar.setVisibility(View.VISIBLE);
                    mBottomTitleBar.setVisibility(View.GONE);
                }else{
                    titleBar.setVisibility(View.GONE);
                    mBottomTitleBar.setVisibility(View.VISIBLE);
                }
            }
        });


        /*//边界回弹，发光效果
        scrollView.setOverScrollMode(View.OVER_SCROLL_ALWAYS);*/
    }

    /**
     * 初始化同款物品的recycle
     */
    private void initCommodityList() {
        mCommoditys = new ArrayList<>();

        mCommodityAdapter = new CommodityAdapter(mCommoditys,getActivity());

        LinearLayoutManager horizontal = new LinearLayoutManager(getContext());

        horizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHomeCommodityRecycle.setLayoutManager(horizontal);

        //mHomeCommodityRecycle.setItemAnimator(new DefaultItemAnimator());
        mHomeCommodityRecycle.setAdapter(mCommodityAdapter);
    }


    /**
     * 请求热门电影并加载图片
     */
    public void requestHotProduction() {
        RetrofitManager.getInstance()
                .getHotProduction()
                .subscribe(new ResultSubscriber<List<Production>>() {
                    @Override
                    public void onNext(List<Production> productions) {
                        //将图片地址转化为ImageView对象
                        mProductions.addAll(productions);

                        //初始化ViewPager
                        for (final Production production : mProductions) {
                            ImageView imageView = new ImageView(getContext());
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), ProducitonDetailActivity.class);

                                    intent.putExtra(Constant.PRODUCTION_ID,production.getProductionInfoId());
                                    intent.putExtra(Constant.PRODUCTION_NAME,production.getFileName());

                                    startActivity(intent);
                                }
                            });

                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


                            imageView.setPadding(HomeShufAdapter.sWidthPadding, HomeShufAdapter.sHeightPadding, HomeShufAdapter.sWidthPadding, HomeShufAdapter.sHeightPadding);
                            BitmapUtils.loadImage(imageView, production.getFileImagePath());
                            mShufImages.add(imageView);

                        }

                        //加载背景图
                        BitmapUtils.loadImage(iv_home_shuf_bg, mProductions.get(0).getFileImagePath(), BitmapUtils.GAOSI);

                        mShufAdapter.notifyDataSetChanged();

                        requestCommodity(mProductions.get(0).getProductionInfoId());
                    }
                });

    }


    /**
     * 请求同款单品
     */
    public void requestCommodity(String producitonId) {
        RetrofitManager.getInstance().getCommodityListByProduction(producitonId)
                .subscribe(new ResultSubscriber<List<Commodity>>() {
                    @Override
                    public void onNext(List<Commodity> commodities) {

                        mCommodityAdapter.refresh(commodities);
                    }
                });

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void select(int position) {
        //ViewPage点击的回调
        BitmapUtils.loadImage(iv_home_shuf_bg, mProductions.get(position).getFileImagePath(), BitmapUtils.GAOSI);


        requestCommodity(mProductions.get(position).getProductionInfoId());
    }
}
