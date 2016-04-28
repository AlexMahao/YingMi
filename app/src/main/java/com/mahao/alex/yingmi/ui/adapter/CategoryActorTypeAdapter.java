package com.mahao.alex.yingmi.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.bean.Actor;
import com.mahao.alex.yingmi.ui.activity.ActorDetailActivity;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.List;

/**
 * 电影2级分类
 * Created by mdw on 2016/4/26.
 */
public class CategoryActorTypeAdapter extends  BaseRecycleAdapter<Actor>{
    public CategoryActorTypeAdapter(List<Actor> datas) {
        super(datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        BitmapUtils.loadImage((ImageView) holder.getView(R.id.home_bottom_commodity_img),datas.get(position).getActorImagePath());

        ((TextView) holder.getView(R.id.home_bottom_commodity_title)).setText(datas.get(position).getActorName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppManager.getAppManager().currentActivity(), ActorDetailActivity.class);
                intent.putExtra(Constant.ACTOR_ID,datas.get(position).getActorId());
                intent.putExtra(Constant.ACTOR_NAME,datas.get(position).getActorName());

                AppManager.getAppManager().currentActivity().startActivity(intent);

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_bottom_commodity;
    }
}
