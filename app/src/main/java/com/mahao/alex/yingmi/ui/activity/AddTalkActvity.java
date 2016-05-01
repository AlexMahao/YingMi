package com.mahao.alex.yingmi.ui.activity;

import android.text.TextUtils;
import android.widget.EditText;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.network.ProgressSubscriber;
import com.mahao.alex.yingmi.network.RetrofitManager;
import com.mahao.alex.yingmi.utils.Tt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Alex_MaHao on 2016/5/1.
 */
public class AddTalkActvity extends BaseActivity {

    @Bind(R.id.talk_add_content_et)
    EditText editText;

    String comment;
    @Override
    public void afterCreate() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_talk;
    }


    @OnClick(R.id.talk_add_submit_btn)
    public void submit(){
        comment = editText.getText().toString();
        if(!TextUtils.isEmpty(comment)){
            Tt.showShort("输入不能为空");
        }

        addCotent();
    }

    private void addCotent() {
      SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        String time = sdf.format(new Date(System.currentTimeMillis()));

        RetrofitManager.getInstance()
                .saveTalk(App.user.getUserId()+"",time,comment)
                .subscribe(new ProgressSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        Tt.showShort(s);
                        finish();
                    }
                });
    }
}
