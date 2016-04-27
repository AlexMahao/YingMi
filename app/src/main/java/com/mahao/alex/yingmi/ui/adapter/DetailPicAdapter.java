package com.mahao.alex.yingmi.ui.adapter;

import android.widget.ImageView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

/**
 * Created by mdw on 2016/4/27.
 */
public class DetailPicAdapter extends BaseRecycleAdapter<String> {
    public DetailPicAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        BitmapUtils.loadImage((ImageView) holder.getView(R.id.detail_pic_img),datas.get(position));
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_detail_pic;
    }
}
