package com.mahao.alex.yingmi.ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.bean.Comment;
import com.mahao.alex.yingmi.utils.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Alex_MaHao on 2016/5/1.
 */
public class CommentAdapter extends BaseRecycleAdapter<Comment> {

    public CommentAdapter(List<Comment> datas) {
        super(datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder, final int position) {
        ((TextView) holder.getView(R.id.social_talk_username_tv)).setText(datas.get(position).getUserName());
        ((TextView) holder.getView(R.id.social_talk_time_tv)).setText(datas.get(position).getTime());
        ((TextView) holder.getView(R.id.social_talk_content_tv)).setText(datas.get(position).getCommentContent());

        if (TextUtils.isEmpty(datas.get(position).getUserIcon()))
            Picasso.with(App.getContext()).load(R.mipmap.mine_logined).transform(new CircleTransformation(12)).into((ImageView) holder.getView(R.id.social_talk_icon_tv));
        else
            Picasso.with(App.getContext()).load(datas.get(position).getUserIcon()).transform(new CircleTransformation(12)).into((ImageView) holder.getView(R.id.social_talk_icon_tv));


    }

    @Override
    public int getLayoutId() {
        return R.layout.item_comment;
    }
}
