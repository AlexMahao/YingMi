package com.mahao.alex.yingmi.ui.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.utils.BitmapUtils;
import com.mahao.alex.yingmi.widget.photoview.Info;
import com.mahao.alex.yingmi.widget.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by mdw on 2016/4/29.
 */
public class ShowImageActivity extends BaseActivity {

    private int position;
    private ArrayList<String> paths;
    private ImageView[] mImageViews;

    @Bind(R.id.show_img_viewpager)
    ViewPager mViewPager;

    @Override
    public void afterCreate() {
        position = getIntent().getIntExtra(Constant.SHOW_IMG_POSITION, 0);
        paths = getIntent().getStringArrayListExtra(Constant.SHOW_IMG_URL);
        mImageViews = new ImageView[paths.size()];
        mViewPager.setAdapter(new PagerAdapter() {
                                  @Override
                                  public Object instantiateItem(ViewGroup container, int position) {
                                      PhotoView imageView = new PhotoView(ShowImageActivity.this);
                                      // 启用图片缩放功能
                                      imageView.enable();
                                      // 禁用图片缩放功能 (默认为禁用，会跟普通的ImageView一样，缩放功能需手动调用enable()启用)

				/*ZoomImageView imageView = new ZoomImageView(
                        getApplicationContext());*/
                                      Info info = imageView.getInfo();
                                      // 从一张图片信息变化到现在的图片，用于图片点击后放大浏览，具体使用可以参照demo的使用
                                      imageView.animaFrom(info);
                                      // 从现在的图片变化到所给定的图片信息，用于图片放大后点击缩小到原来的位置，具体使用可以参照demo的使用
                                      imageView.animaTo(info, new Runnable() {
                                          @Override
                                          public void run() {
                                              //动画完成监听
                                          }
                                      });
                                      BitmapUtils.loadImage(imageView,paths.get(position));
                                      container.addView(imageView);
                                      mImageViews[position] = imageView;
                                      return imageView;
                                  }

                                  @Override
                                  public void destroyItem(ViewGroup container, int position,
                                                          Object object) {
                                      container.removeView(mImageViews[position]);
                                  }

                                  @Override
                                  public boolean isViewFromObject(View arg0, Object arg1) {
                                      return arg0 == arg1;
                                  }

                                  @Override
                                  public int getCount() {
                                      return paths.size();
                                  }
                              }


        );
        mViewPager.setCurrentItem(position);
    }

    @Override
    public int getLayoutId() {

        return R.layout.activity_show_img;
    }
}
