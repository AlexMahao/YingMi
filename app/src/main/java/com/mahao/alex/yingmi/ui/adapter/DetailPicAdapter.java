package com.mahao.alex.yingmi.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.Constant;
import com.mahao.alex.yingmi.ui.activity.ShowImageActivity;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mdw on 2016/4/27.
 */
public class DetailPicAdapter extends BaseRecycleAdapter<String> {
    public DetailPicAdapter(List<String> datas) {
        super(datas);
    }

    @Override
    protected void bindData(BaseViewHolder holder,final int position) {
        BitmapUtils.loadImage((ImageView) holder.getView(R.id.detail_pic_img),datas.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppManager.getAppManager().currentActivity(),ShowImageActivity.class);

                intent.putStringArrayListExtra(Constant.SHOW_IMG_URL, (ArrayList<String>) datas);
                intent.putExtra(Constant.SHOW_IMG_POSITION,position);
                AppManager.getAppManager().currentActivity().startActivity(intent);
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_detail_pic;
    }
}
