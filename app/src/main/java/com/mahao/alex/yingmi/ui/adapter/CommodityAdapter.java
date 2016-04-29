package com.mahao.alex.yingmi.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.ui.activity.CommodityDetailActivity;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

/**
 * 同款物品展示列表
 * Created by mdw on 2016/4/20.
 */
public class CommodityAdapter extends HorizontalListRecycleAdapter<Commodity> {

    public CommodityAdapter(List<Commodity> data, Activity activity) {
        super(data,activity);
    }

    @Override
    public void binddata(HorizontalListRecycleAdapter.ViewHolder holder, final Commodity commodity) {
        BitmapUtils.loadImage(holder.imgView,commodity.getCommodityImagePath());
        String text = "";
        if(commodity.getCurrency().equals("人民币")){
            text=text+"￥";
        }else{
            text = text+"$";
        }
        holder.textView.setText(text+commodity.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppManager.getAppManager().currentActivity(), CommodityDetailActivity.class);
                intent.putExtra(Constant.COMMODITY_ID,commodity.getCommodityId());

                AppManager.getAppManager().currentActivity().startActivity(intent);

            }
        });
    }

    /**
     * 刷新添加数据
     * @param commodities
     */
    public void refresh(List<Commodity> commodities){
        datas.clear();
        datas.addAll(commodities);
        notifyDataSetChanged();
    }


}
