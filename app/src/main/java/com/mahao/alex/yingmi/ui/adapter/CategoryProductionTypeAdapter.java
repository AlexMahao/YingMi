package com.mahao.alex.yingmi.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

/**
 * 电影2级分类
 * Created by mdw on 2016/4/26.
 */
public class CategoryProductionTypeAdapter  extends  BaseRecycleAdapter<Production>{
    public CategoryProductionTypeAdapter(List<Production> datas) {
        super(datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        BitmapUtils.loadImage((ImageView) holder.getView(R.id.home_bottom_commodity_img),datas.get(position).getFileImagePath());

        ((TextView) holder.getView(R.id.home_bottom_commodity_title)).setText(datas.get(position).getFileName());

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_bottom_commodity;
    }
}
