package com.mahao.alex.yingmi.ui.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

/**
 * Created by mdw on 2016/4/26.
 */
public class CategoryActorAdapter extends BaseRecycleAdapter<Actor> {

    public CategoryActorAdapter(List<Actor> data) {
        super(data);
    }


    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        BitmapUtils.loadImage((ImageView) holder.getView(R.id.item_horizontal_list_image),datas.get(position).getActorImagePath());
        ((TextView) holder.getView(R.id.item_horizontal_list_text)).setText(datas.get(position).getActorName());

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_category_list;
    }
}
