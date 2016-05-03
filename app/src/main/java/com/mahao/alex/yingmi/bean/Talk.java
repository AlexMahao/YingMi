package com.mahao.alex.yingmi.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 说说
 * Created by Alex_MaHao on 2016/5/1.
 */
public class Talk implements Parcelable {

    private int talkId;

    private int userId;

    private String userName;

    private String userIcon;

    private String talkTime;


    private String talkContent;

    private int clickCount;

    private int goodCount;

    private int commentCount;

    private boolean isGood;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(talkId);
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(userIcon);
        dest.writeString(talkTime);
        dest.writeString(talkContent);
        dest.writeInt(clickCount);
        dest.writeInt(goodCount);
        dest.writeInt(commentCount);
        dest.writeByte((byte)(isGood?1: 0));
    }
    protected Talk(Parcel in) {
        talkId = in.readInt();
        userId = in.readInt();
        userName = in.readString();
        userIcon = in.readString();
        talkTime = in.readString();
        talkContent = in.readString();
        clickCount = in.readInt();
        goodCount = in.readInt();
        commentCount = in.readInt();
        isGood = in.readByte() != 0;
    }

    public static final Creator<Talk> CREATOR = new Creator<Talk>() {
        @Override
        public Talk createFromParcel(Parcel in) {
            return new Talk(in);
        }

        @Override
        public Talk[] newArray(int size) {
            return new Talk[size];
        }
    };

    public int getTalkId() {
        return talkId;
    }

    public void setTalkId(int talkId) {
        this.talkId = talkId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(String talkTime) {
        this.talkTime = talkTime;
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(int goodCount) {
        this.goodCount = goodCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isGood() {
        return isGood;
    }

    public void setGood(boolean good) {
        isGood = good;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
