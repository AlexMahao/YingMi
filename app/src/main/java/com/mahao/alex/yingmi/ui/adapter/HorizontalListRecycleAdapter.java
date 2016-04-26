package com.mahao.alex.yingmi.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 水平展示数据
 * Created by mdw on 2016/4/20.
 */
public abstract  class HorizontalListRecycleAdapter<T> extends RecyclerView.Adapter<HorizontalListRecycleAdapter.ViewHolder> {

    protected List<T> datas;

    protected Activity mActivity;

    public HorizontalListRecycleAdapter(List<T> data,Activity activity){
        datas = data;
        mActivity = activity;
    }

    @Override
    public HorizontalListRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal_list, parent, false);

        return new HorizontalListRecycleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalListRecycleAdapter.ViewHolder holder, int position) {
        binddata(holder,datas.get(position));
    }

    public void refresh(List<T> datas){
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 数据绑定
     * @param holder
     * @param t
     */
    public abstract void binddata(HorizontalListRecycleAdapter.ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.item_horizontal_list_image)
        ImageView imgView;

        @Bind(R.id.item_horizontal_list_text)
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
