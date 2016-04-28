package com.mahao.alex.yingmi.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.utils.AppManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自定义购买的Dialog
 * Created by mdw on 2016/4/28.
 */
public class BuyDialog extends Dialog  {


    private View view;

    private OnBuyDialogListener l;

    public BuyDialog(Context context,OnBuyDialogListener listener) {
        this(context,R.style.BuyDialog);
        l = listener;



    }

    public BuyDialog(Context context, int themeResId) {
        super(context, themeResId);

        init();


        initWidth();
    }

    private void initWidth() {
        WindowManager m = AppManager.getAppManager().currentActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
       // p.height = view.getMeasuredHeight();  // 高度设置为屏幕的0.6
         p.width = (int) (d.getWidth()*0.8); // 宽度设置为屏幕的0.65

        getWindow().setAttributes(p);

    }


    private void init() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_buy, null);
        setContentView(view);

        ButterKnife.bind(this,view);

        getWindow().setWindowAnimations(R.style.BuyDialogAnim);
    }

    @OnClick(R.id.dialog_buy_cancel)
    public void cancel(){
        this.dismiss();
        if(l!=null){
            l.cancel();
        }
    }


    @OnClick(R.id.dialog_buy_go)
    public void go(){
        if(l!=null){
            l.go();
            dismiss();
        }
    }

    public abstract static  class OnBuyDialogListener{
        public abstract  void go();

        public void cancel(){

        }
    }


}
