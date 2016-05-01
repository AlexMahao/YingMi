package com.mahao.alex.yingmi.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.bean.Talk;
import com.mahao.alex.yingmi.ui.adapter.BaseRecycleAdapter;
import com.mahao.alex.yingmi.utils.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Alex_MaHao on 2016/5/1.
 */
public class TalkAdapter extends BaseRecycleAdapter<Talk> {

    public TalkAdapter(List<Talk> datas) {
        super(datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        ((TextView) holder.getView(R.id.social_talk_username_tv)).setText(datas.get(position).getUserName());
        ((TextView) holder.getView(R.id.social_talk_time_tv)).setText(datas.get(position).getTalkTime());
        ((TextView) holder.getView(R.id.social_talk_content_tv)).setText(datas.get(position).getTalkContent());
        ((TextView) holder.getView(R.id.social_talk_click_tv)).setText(datas.get(position).getClickCount()+"");
        ((TextView) holder.getView(R.id.social_talk_good_tv)).setText(datas.get(position).getGoodCount()+"");
        ((TextView) holder.getView(R.id.social_talk_comment_tv)).setText(datas.get(position).getCommentCount()+"");


        if (TextUtils.isEmpty(datas.get(position).getUserIcon()))
            Picasso.with(App.getContext()).load(R.mipmap.mine_logined).transform(new CircleTransformation(12)).into((ImageView) holder.getView(R.id.social_talk_icon_tv));
        else
            Picasso.with(App.getContext()).load(datas.get(position).getUserIcon()).transform(new CircleTransformation(12)).into((ImageView) holder.getView(R.id.social_talk_icon_tv));




    }

    @Override
    public int getLayoutId() {
        return R.layout.item_talk;
    }
}
