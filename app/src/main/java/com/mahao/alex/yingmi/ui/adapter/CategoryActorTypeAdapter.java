package com.mahao.alex.yingmi.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

/**
 * 电影2级分类
 * Created by mdw on 2016/4/26.
 */
public class CategoryActorTypeAdapter extends  BaseRecycleAdapter<Actor>{
    public CategoryActorTypeAdapter(List<Actor> datas) {
        super(datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        BitmapUtils.loadImage((ImageView) holder.getView(R.id.home_bottom_commodity_img),datas.get(position).getActorImagePath());

        ((TextView) holder.getView(R.id.home_bottom_commodity_title)).setText(datas.get(position).getActorName());

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_bottom_commodity;
    }
}
