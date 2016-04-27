package com.mahao.alex.yingmi.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mdw on 2016/4/26.
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseRecycleAdapter.BaseViewHolder> {


    protected List<T> datas;

    protected OnItemClickListener onItemClickListener;

    public BaseRecycleAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(),parent,false);

        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseRecycleAdapter.BaseViewHolder holder, final int position) {

       /* *//**
         * 设置控件的点击事件
         *//*
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onClick(position);
                }
            }
        });*/
        bindData(holder,position);
    }


    /**
     * 刷新数据
     * @param datas
     */
    public void refresh(List<T> datas){
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }


    /**
     * 添加数据
     * @param datas
     */
    public void addData(List<T> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    protected abstract void bindData(BaseViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public class BaseViewHolder extends  RecyclerView.ViewHolder{
        private Map<Integer, View> viewMap;

        public BaseViewHolder(View itemView) {
            super(itemView);
            viewMap = new HashMap<>();
        }

        /**
         * 获取设置的view
         * @param id
         * @return
         */
        public View getView(int id) {
            View view = viewMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                viewMap.put(id, view);
            }
            return view;
        }
    }

    /**
     * 获取子item
     * @return
     */
    public abstract int getLayoutId();



    public interface OnItemClickListener{
        void onClick(int position);
    }

    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }
}
