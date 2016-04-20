package com.mahao.alex.yingmi.ui.adapter;

import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.utils.BitmapUtils;
import com.mahao.alex.yingmi.utils.L;

import java.util.List;

/**
 * 同款物品展示列表
 * Created by mdw on 2016/4/20.
 */
public class CommodityAdapter extends HorizontalListRecycleAdapter<Commodity> {

    public CommodityAdapter(List<Commodity> data) {
        super(data);
    }

    @Override
    public void binddata(HorizontalListRecycleAdapter.ViewHolder holder, Commodity commodity) {
        L.object(holder.imgView);
        BitmapUtils.loadImage(holder.imgView,commodity.getCommodityImagePath());
        String text = "";
        if(commodity.getCurrency().equals("人民币")){
            text=text+"￥";
        }else{
            text = text+"$";
        }
        holder.textView.setText(text+commodity.getPrice());
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
