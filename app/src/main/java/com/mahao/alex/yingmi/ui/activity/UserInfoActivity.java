package com.mahao.alex.yingmi.ui.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahao.alex.yingmi.R;
import com.mahao.alex.yingmi.base.App;
import com.mahao.alex.yingmi.base.BaseActivity;
import com.mahao.alex.yingmi.bean.User;
import com.mahao.alex.yingmi.utils.AppManager;
import com.mahao.alex.yingmi.utils.CircleTransformation;
import com.mahao.alex.yingmi.utils.DimenUtils;
import com.mahao.alex.yingmi.utils.Tt;
import com.mahao.alex.yingmi.widget.TitleBar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by mdw on 2016/4/29.
 */
public class UserInfoActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    public static String imgPath = Environment.getExternalStorageDirectory() + "/ss/";

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

    private int mYear = 1995;

    private int mMonth = 1;

    private int mDay = 1;


    private boolean isSetInfo = false;

    /**
     * 时间选择器
     */
    private DatePickerDialog mDatePcikerDialog;

    @Override
    public void afterCreate() {

        initTitleBar();


        mUser = new User();

        setIcon(App.user.getUserIcon());

        setUserName(App.user.getUsername());

        setSex(App.user.getSex());

        setBirthday(App.user.getBirthday());

        setPhone(App.user.getMobilePhoneNumber());

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


                if(!isSetInfo){
                    Tt.showShort("您未作任何修改哦！！！");
                    return;
                }

                Log.i("info", mUser.toString());
                mUser.setUserId(App.user.getUserId());
                mUser.update(getApplicationContext(), App.user.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        App.user = BmobUser.getCurrentUser(getApplicationContext(), User.class);
                        finish();
                        Tt.showLong("保存成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("info",s);
                        Tt.showLong("更新失败:" + s);
                    }
                });
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
            mDatePcikerDialog = new DatePickerDialog(this, this, mYear, mMonth, mDay);

        } else {
            mBirthDay.setText(birthday);
            String[] split = birthday.split("-");
            mYear = Integer.parseInt(split[0]);
            mMonth = Integer.parseInt(split[1]);
            mDay = Integer.parseInt(split[2]);

            mDatePcikerDialog = new DatePickerDialog(this, this, mYear, mMonth, mDay);

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

                Cursor cursor = getContentResolver().query(uri, filePathColum, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColum[0]);
                String picturePath = cursor.getString(columnIndex);

                cursor.close();
                Log.i("info", picturePath);
                cutPic(picturePath);
            } else if (requestCode == 200) {

                // 拿到剪切数据
                Bitmap bmap = data.getParcelableExtra("data");

                // 图像保存到文件中
                FileOutputStream foutput = null;

                File file = new File(imgPath);

                if (!file.exists()) {
                    file.mkdirs();
                }
                try {
                    String imgUrl = imgPath + System.currentTimeMillis() + ".png";

                    foutput = new FileOutputStream(new File(imgUrl));
                    bmap.compress(Bitmap.CompressFormat.PNG, 100, foutput);

                    final BmobFile bmobFile = new BmobFile(new File(imgUrl));
                    bmobFile.upload(getApplicationContext(), new UploadFileListener() {
                        @Override
                        public void onSuccess() {
                            String fileUrl = bmobFile.getFileUrl(getApplicationContext());
                            mUser.setUserIcon(fileUrl);
                            setIcon(mUser.getUserIcon());
                            isSetInfo = true;
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            //图片选择失败
                            Tt.showShort("图片选择失败");
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (null != foutput) {
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

        intent.setAction("com.android.camera.action.CROP");
        intent.setDataAndType(getPicUri(path), "image/*");// mUri是已经选择的图片Uri
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
        FrameLayout fl = new FrameLayout(this);
        fl.addView(editText);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) editText.getLayoutParams();
        layoutParams.setMargins(DimenUtils.dp2px(10), 0, DimenUtils.dp2px(10), 0);
        editText.setLayoutParams(layoutParams);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("设置昵称")
                .setView(fl)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nickName = editText.getText().toString().trim();
                        mUser.setUsername(nickName);
                        setUserName(mUser.getUsername());
                        isSetInfo = true;

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();

    }


    @OnClick(R.id.user_info_sex_ll)
    public void selectSex() {
        //设置性别


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("设置性别")
                .setSingleChoiceItems(new String[]{"男", "女"}, mUser.getSex() == null || mUser.getSex().equals("男") ? 0 : 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String sex = which == 0 ? "男" : "女";
                        mUser.setSex(sex);
                        setSex(mUser.getSex());
                        isSetInfo = true;
                    }
                })
                .create();
        dialog.show();
    }

    @OnClick(R.id.user_info_birthday_ll)
    public void selectBirthday() {
       /* //设置生日
        if (mUser.getBirthday() == null) {
            //此时还未设置生日
        } else {

        }*/
        mDatePcikerDialog.show();
    }

    @OnClick(R.id.user_info_exit_login)
    public void exitLogin() {
        //退出登录
        BmobUser.logOut(getApplicationContext());   //清除缓存用户对象

        App.user = null;
        AppManager.getAppManager().finishAllActivity();
        intent2Activity(HomeActivity.class);
    }


    public Uri getPicUri(String picPath) {
        Uri mUri = Uri.parse("content://media/external/images/media");
        Uri mImageUri = null;
        Cursor cursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor
                    .getColumnIndex(MediaStore.MediaColumns.DATA));
            if (picPath.equals(data)) {
                int ringtoneID = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID);
                break;
            }
            cursor.moveToNext();
        }

        return mImageUri;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear+1;
        mDay = dayOfMonth;

        mUser.setBirthday(mYear + "-" + mMonth + "-" + mDay);
        setBirthday(mUser.getBirthday());
        isSetInfo = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatePcikerDialog != null) {
            mDatePcikerDialog.dismiss();
        }
    }
}
