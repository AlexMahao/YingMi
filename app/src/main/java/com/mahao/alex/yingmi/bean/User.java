package com.mahao.alex.yingmi.bean;

import cn.bmob.v3.BmobUser;

/**
 * 用户实体类，用于登录，注册等一系列操作
 * Created by mdw on 2016/1/27.
 */
public class User extends BmobUser {


    private String userIcon;

    private String sex;

    private String birthday;

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public static User newInstance(User user) {
        User u = new User();
        u.setMobilePhoneNumber(user.getMobilePhoneNumber());
        u.setUsername(user.getUsername());
        u.userIcon = user.getUserIcon();
        u.sex =user.getSex();
        u.birthday = user.getBirthday();
        u.userId = user.getUserId();
        return u;
    }

    @Override
    public String toString() {
        return "User{" +
                "userIcon='" + userIcon + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", userId=" + userId +
                '}';
    }
}
