package com.mahao.alex.yingmi.ui.adapter;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Theme;
import com.mahao.alex.yingmi.ui.activity.CommodityDetailActivity;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mdw on 2016/4/21.
 */
public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ViewHolder> {

    private List<Theme> mThemes;

    private Activity mActivity;
    public ThemeAdapter(List<Theme> mThemes,Activity activity) {
        this.mThemes = mThemes;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Theme theme = mThemes.get(position);

        holder.usernameTv.setText(theme.getThemeUsername());
        if(theme.getThemeUsername().equals("alex")){

        }else{
            BitmapUtils.loadImage(holder.icon,theme.getThemeIcon());
        }

        holder.themeBgImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppManager.getAppManager().currentActivity(), CommodityDetailActivity.class);
                intent.putExtra(Constant.COMMODITY_ID,theme.getThemeId());
                AppManager.getAppManager().currentActivity().startActivity(intent);
            }
        });

        holder.titleTv.setText(theme.getThemeDesc());

        BitmapUtils.loadImage(holder.themeBgImageView,theme.getThemeImg());

        RecyclerView recycle = holder.commodityRecycle;
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycle.setLayoutManager(layoutManager);

        CommodityAdapter adapter = new CommodityAdapter(theme.getList(),mActivity);

        recycle.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return  mThemes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_theme_bg_image)
        ImageView themeBgImageView;

        @Bind(R.id.item_theme_title_tv)
        TextView titleTv;

        @Bind(R.id.item_theme_icon_img)
        ImageView icon;


        @Bind(R.id.item_theme_username_text)
        TextView usernameTv;

        @Bind(R.id.item_theme_commodity_recycle)
        RecyclerView commodityRecycle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }



    /**
     * 刷新添加数据
     *
     */
    public void refresh(List<Theme> themes){
        mThemes.clear();
        mThemes.addAll(themes);
        notifyDataSetChanged();

        Log.i("info",getItemCount()+"");
    }

}
