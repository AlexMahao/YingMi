package com.mahao.alex.yingmi.bean;

import cn.bmob.v3.BmobUser;

/**
 * 用户实体类，用于登录，注册等一系列操作
 * Created by mdw on 2016/1/27.
 */
public class User extends BmobUser {


    private String userIcon;

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }
}
