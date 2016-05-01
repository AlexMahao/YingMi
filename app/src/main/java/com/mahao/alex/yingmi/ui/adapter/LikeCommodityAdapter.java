package com.mahao.alex.yingmi.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.bean.CommodityBean;
import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Commodity;
import com.mahao.alex.yingmi.ui.activity.CommodityDetailActivity;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mdw on 2016/4/25.
 */
public class LikeCommodityAdapter extends RecyclerView.Adapter<LikeCommodityAdapter.ViewHolder> {


    private List<CommodityBean> mCommoditys;

    public LikeCommodityAdapter(List<CommodityBean> mCommoditys) {
        this.mCommoditys = mCommoditys;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_bottom_commodity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BitmapUtils.loadImage(holder.imageView,mCommoditys.get(position).getCommodityImagePath());
        holder.tv.setText(mCommoditys.get(position).getCommodityName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppManager.getAppManager().currentActivity(), CommodityDetailActivity.class);
                intent.putExtra(Constant.COMMODITY_ID,mCommoditys.get(position).getCommodityId());
                AppManager.getAppManager().currentActivity().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCommoditys.size();
    }

    public void refresh(List<CommodityBean> commodities) {
        mCommoditys.clear();
        mCommoditys.addAll(commodities);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.home_bottom_commodity_img)
        ImageView imageView;

        @Bind(R.id.home_bottom_commodity_title)
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
