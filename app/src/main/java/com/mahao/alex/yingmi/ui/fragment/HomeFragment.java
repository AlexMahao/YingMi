package com.mahao.alex.yingmi.ui.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseFragment;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.network.ResultSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.ui.adapter.CommodityAdapter;
import com.mahao.alex.yingmi.ui.adapter.HomeShufAdapter;
import com.mahao.alex.yingmi.utils.BitmapUtils;
import com.mahao.alex.yingmi.utils.DimenUtils;
import com.mahao.alex.yingmi.widget.TitleBar;

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

    @Bind(R.id.scroll_home)
    ScrollView scrollView;

    @Bind(R.id.iv_home_shuf_bg)
    ImageView iv_home_shuf_bg;

    @Bind(R.id.titlebar)
    TitleBar titleBar;

    @Bind(R.id.home_commodity_recycleView)
    RecyclerView mHomeCommodityRecycle;

    private HomeShufAdapter mShufAdapter;

    private List<ImageView> mShufImages;

    private CommodityAdapter mCommodityAdapter;

    private List<Commodity> mCommoditys;

    @Override
    protected void afterCreate() {
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
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("info", v.getScrollY() + "");

                int scrollY = v.getScrollY();
                if (scrollY <= 1000 && scrollY > 0) {
                    titleBar.setAlpha(scrollY / 1000.0f);
                }
                return false;
            }
        });

        /**
         * 请求热门电影并加载图片
         */
        requestHotProduction();


        initCommodityList();
    }

    /**
     * 初始化同款物品的recycle
     */
    private void initCommodityList() {
        mCommoditys = new ArrayList<>();

        mCommodityAdapter = new CommodityAdapter(mCommoditys);

        LinearLayoutManager horizontal = new LinearLayoutManager(getContext());

        horizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHomeCommodityRecycle.setLayoutManager(horizontal);

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
                        for (Production production : mProductions) {
                            ImageView imageView = new ImageView(getContext());
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                            int padding = DimenUtils.dp2px(40);
                            imageView.setPadding(padding, padding, padding, padding);
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
