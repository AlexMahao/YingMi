package com.mahao.alex.yingmi.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdw on 2016/4/26.
 */
public class Actor implements Parcelable {


    /**
     * actorDesc : 1995年至1999年， 就读于上海戏剧学院电视艺术系，获得学士学位。2000年至2005年， 就读于上海戏剧学院表演系，获得硕士学位。 2006年因在《武林外传》中饰演吕轻侯而为观众所熟知。2009年7月获得中央戏剧学院表演、导演艺术研究博士学位。2012年凭借电影《做次有钱人》获得第4届澳门国际电影节最佳男主角奖。2013年主演电影《李可乐寻人记》。2014年主演电影《擦枪走火》黄斗。2015年3月8日，与女主播史林子在京举行婚礼。同年8月26日，喻恩泰晒娃宣布当爹。
     * actorId : 835
     * actorImagePath : http://file.bmob.cn/M03/28/75/oYYBAFcNJlyALv4tAAHD1gnLHM0658.png
     * actorName : 喻恩泰
     * birthDay : 1977-11-03T00:00:00
     * collectionCount : 0
     * constellation_CN : 天蝎座
     * createdAt : 2016-04-13 00:46:21
     * homeTown : null
     * id : 835
     * objectId : bdb0a25265
     * updatedAt : 2016-04-26 08:51:37
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actorDesc);
        dest.writeString(actorId);
        dest.writeString(actorImagePath);
        dest.writeString(actorName);
        dest.writeString(birthDay);
        dest.writeString(collectionCount);
        dest.writeString(constellation_CN);
        dest.writeString(homeTown);

    }

    public  static final Parcelable.Creator<Actor> CREATOR = new Parcelable.Creator<Actor>(){

        @Override
        public Actor createFromParcel(Parcel source) {
            Actor actor = new Actor();
            actor.setActorDesc(source.readString());
            actor.setActorId(source.readString());
            actor.setActorImagePath(source.readString());
            actor.setActorName(source.readString());
            actor.setBirthDay(source.readString());
            actor.setCollectionCount(source.readString());
            actor.setConstellation_CN(source.readString());
            actor.setHomeTown(source.readString());
            return actor;
        }

        @Override
        public Actor[] newArray(int size) {
            return new Actor[size];
        }
    };

    private String actorDesc;
    private String actorId;
    private String actorImagePath;
    private String actorName;
    private String birthDay;
    private String collectionCount;
    private String constellation_CN;
    private String createdAt;
    private String homeTown;
    private int id;
    private String objectId;
    private String updatedAt;

    public String getActorDesc() {
        return actorDesc;
    }

    public void setActorDesc(String actorDesc) {
        this.actorDesc = actorDesc;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public String getActorImagePath() {
        return actorImagePath;
    }

    public void setActorImagePath(String actorImagePath) {
        this.actorImagePath = actorImagePath;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(String collectionCount) {
        this.collectionCount = collectionCount;
    }

    public String getConstellation_CN() {
        return constellation_CN;
    }

    public void setConstellation_CN(String constellation_CN) {
        this.constellation_CN = constellation_CN;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
