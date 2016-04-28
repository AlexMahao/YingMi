package com.mahao.alex.yingmi.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Production;
import com.mahao.alex.yingmi.ui.activity.ProducitonDetailActivity;
import com.mahao.alex.yingmi.utils.AppManager;
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
    protected void bindData(BaseViewHolder holder, final int position) {
        BitmapUtils.loadImage((ImageView) holder.getView(R.id.home_bottom_commodity_img),datas.get(position).getFileImagePath());

        ((TextView) holder.getView(R.id.home_bottom_commodity_title)).setText(datas.get(position).getFileName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppManager.getAppManager().currentActivity(), ProducitonDetailActivity.class);

                intent.putExtra(Constant.PRODUCTION_ID,datas.get(position).getProductionInfoId());
                intent.putExtra(Constant.PRODUCTION_NAME,datas.get(position).getFileName());

                AppManager.getAppManager().currentActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_bottom_commodity;
    }
}
