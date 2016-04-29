package com.mahao.alex.yingmi.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.bean.User;
import com.mahao.alex.yingmi.utils.CircleTransformation;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.widget.TitleBar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by mdw on 2016/4/29.
 */
public class UserInfoActivity extends BaseActivity {

    public static String imgPath = Environment.getExternalStorageState()+"/ss/";

    @Bind(R.id.titlebar)
    TitleBar titleBar;

    @Bind(R.id.user_info_icon_img)
    ImageView mIcon;

    @Bind(R.id.user_info_name_tv)
    TextView mUserName;

    @Bind(R.id.user_info_sex_tv)
    TextView mSex;

    @Bind(R.id.user_info_birthday_tv)
    TextView mBirthDay;

    @Bind(R.id.user_info_phone_tv)
    TextView mPhone;

    @Bind(R.id.user_info_comment_tv)
    View mComment;

    private User mUser;

    @Override
    public void afterCreate() {

        initTitleBar();

        mUser = User.newInstance(App.user);

        setIcon(mUser.getUserIcon());

        setUserName(mUser.getUsername());

        setSex(mUser.getSex());

        setBirthday(mUser.getBirthday());

        setPhone(mUser.getMobilePhoneNumber());

    }

    private void setIcon(String path) {
        if (TextUtils.isEmpty(path)) {
            Picasso.with(getApplicationContext()).load(R.mipmap.mine_logined).transform(new CircleTransformation()).into(mIcon);
            mComment.setVisibility(View.VISIBLE);
        } else {
            mComment.setVisibility(View.GONE);
            Picasso.with(getApplicationContext()).load(path).transform(new CircleTransformation()).into(mIcon);
        }
    }

    private void initTitleBar() {
        titleBar.setTitleBarClickListener(new TitleBar.TitleBarClickListener() {
            @Override
            public void onLeftClick() {
                onBackPressed();
            }

            @Override
            public void onRightClick() {
                //保存
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    public void setSex(String sex) {
        if (TextUtils.isEmpty(sex)) {
            mSex.setText("未设置");
        } else {
            mSex.setText(sex);
        }

    }

    public void setUserName(String userName) {
        if (TextUtils.isEmpty(userName)) {
            mUserName.setText("未设置");
        } else {
            mUserName.setText(userName);
        }

    }

    public void setBirthday(String birthday) {
        if (TextUtils.isEmpty(birthday)) {
            mBirthDay.setText("未设置");
        } else {
            mBirthDay.setText(birthday);
        }
    }


    public void setPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            mPhone.setText("不会出现这种情况的");
        } else {
            mPhone.setText(phone);
        }
    }


    @OnClick(R.id.user_info_icon_ll)
    public void selectPic() {
        //选择图标
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri uri = data.getData();
                String[] filePathColum = {MediaStore.Images.ImageColumns.DATA};

                Cursor cursor = getContentResolver().query(uri,filePathColum,null,null,null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColum[0]);
                String picturePath = cursor.getString(columnIndex);

                cursor.close();

                cutPic(picturePath);
            } else if (requestCode == 200) {

                // 拿到剪切数据
                Bitmap bmap = data.getParcelableExtra("data");

                // 图像保存到文件中
                FileOutputStream foutput = null;

                File file = new File(imgPath);

                if(!file.exists()){
                    file.mkdirs();
                }
                try {
                    String imgUrl = imgPath+System.currentTimeMillis()+".png";
                    foutput = new FileOutputStream(imgUrl);
                    bmap.compress(Bitmap.CompressFormat.PNG, 100, foutput);

                    final BmobFile bmobFile = new BmobFile(new File(imgUrl));
                    bmobFile.upload(getApplicationContext(), new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            String fileUrl = bmobFile.getFileUrl(getApplicationContext());
                            mUser.setUserIcon(fileUrl);
                            setIcon(mUser.getUserIcon());
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            //图片选择失败
                            Tt.showShort("图片选择失败");
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }finally{
                    if(null != foutput){
                        try {
                            foutput.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    private void cutPic(String path) {

        Intent intent = new Intent();

        //intent.setAction(Intent.);
        intent.setDataAndType(Uri.parse(path), "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);// 输出图片大小
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 200);
    }

    @OnClick(R.id.user_info_name_ll)
    public void selectName() {
        //设置姓名

        final EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("设置昵称")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickName = editText.getText().toString().trim();
                        mUser.setUsername(nickName);
                        setUserName(mUser.getUsername());

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();

    }


    @OnClick(R.id.user_info_sex_ll)
    public void selectSex() {
        //设置性别

        final EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("设置性别")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sex = editText.getText().toString().trim();
                        mUser.setSex(sex);
                        setSex(mUser.getSex());

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        dialog.show();
    }

    @OnClick(R.id.user_info_birthday_ll)
    public void selectBirthday() {
        //设置性别
    }

    @OnClick(R.id.user_info_exit_login)
    public void exitLogin() {
        //退出登录


    }

}
