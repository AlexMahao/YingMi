package com.mahao.alex.yingmi.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

/**
 * 分类 ---》电影
 * Created by mdw on 2016/4/26.
 */
public class CategoryProductionAdapter extends  BaseRecycleAdapter<Production> {

    public CategoryProductionAdapter(List<Production> data) {
        super(data);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        BitmapUtils.loadImage((ImageView) holder.getView(R.id.item_horizontal_list_image),datas.get(position).getFileImagePath());
        ((TextView) holder.getView(R.id.item_horizontal_list_text)).setText(datas.get(position).getFileName());

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_category_list;
    }

}
