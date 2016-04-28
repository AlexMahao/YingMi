package com.mahao.alex.yingmi.ui.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Theme;
import com.mahao.alex.yingmi.ui.activity.CommodityDetailActivity;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

/**
 * Created by mdw on 2016/4/26.
 */
public class CategoryThemeAdapter extends BaseRecycleAdapter<Theme> {


    public CategoryThemeAdapter(List<Theme> datas) {
        super(datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
       BitmapUtils.loadImage((ImageView) holder.getView(R.id.item_theme_bg_image),datas.get(position).getThemeImg());

        ((TextView) holder.getView(R.id.item_theme_title_tv)).setText(datas.get(position).getThemeDesc());


        ((TextView) holder.getView(R.id.item_theme_username_text)).setText(datas.get(position).getThemeUsername());

        if(TextUtils.isEmpty(datas.get(position).getThemeIcon())){

        }else{
            BitmapUtils.loadImage((ImageView) holder.getView(R.id.item_theme_icon_img),datas.get(position).getThemeIcon());

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppManager.getAppManager().currentActivity(), CommodityDetailActivity.class);
                intent.putExtra(Constant.COMMODITY_ID,datas.get(position).getThemeId());
                AppManager.getAppManager().currentActivity().startActivity(intent);
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_theme;
    }
}
