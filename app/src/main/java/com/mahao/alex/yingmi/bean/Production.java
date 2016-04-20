package com.mahao.alex.yingmi.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 电影产品
 * Created by mdw on 2016/4/20.
 */
public class Production implements Parcelable {

    /**
     * createdAt : 2016-04-13 00:45:11
     * fileImagePath : http://file.bmob.cn/M03/28/75/oYYBAFcNJheAJVydAAIlBuXZ6Ig129.png
     * fileName : 火锅英雄
     * id : 262
     * language : 国语
     * objectId : a44890008d
     * produceYear : 2016
     * productionDesc : 在布满防空洞的重庆，三个从初中就“厮混”在一起的好兄弟合伙开着一家火锅店，名为“老同学洞子火锅”。由于经营不善，几人落得只能转让店铺还债。为了店铺能“卖个好价钱”，三人打起了“扩充门面”的主意，自行往洞里开挖。没想到，在扩充工程中却凿开了银行的金库。就这样，濒临倒闭的火锅店和银行金库仅有“一洞之隔”；看着眼前随手可得的成堆现金，在“拿钱还是报案”的思想拉锯战中，三兄弟偶遇上另一个女同学——初中时给老大写过情书、现在在银行上班的于小惠。四个老同学因为这个“洞”而打开重聚之门。由此，这个略显尴尬的洞，引发了一个令人意想不到的故事。
     * productionInfoId : 262
     * typeName : 剧情
     * updatedAt : 2016-04-13 00:45:11
     */

    private String fileImagePath;//图片地址
    private String fileName;//电影名
    private String language;//国语
    private String produceYear;//电影年份
    private String productionDesc;//电影描述
    private String productionInfoId;//电影id
    private String typeName;//类型

    public String getFileImagePath() {
        return fileImagePath;
    }

    public void setFileImagePath(String fileImagePath) {
        this.fileImagePath = fileImagePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProduceYear() {
        return produceYear;
    }

    public void setProduceYear(String produceYear) {
        this.produceYear = produceYear;
    }

    public String getProductionDesc() {
        return productionDesc;
    }

    public void setProductionDesc(String productionDesc) {
        this.productionDesc = productionDesc;
    }

    public String getProductionInfoId() {
        return productionInfoId;
    }

    public void setProductionInfoId(String productionInfoId) {
        this.productionInfoId = productionInfoId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileImagePath);
        dest.writeString(fileName);
        dest.writeString(language);
        dest.writeString(produceYear);
        dest.writeString(productionDesc);
        dest.writeString(productionInfoId);
        dest.writeString(typeName);


    }

    public static final Parcelable.Creator<Production> CREATOR = new Parcelable.Creator<Production>(){

        @Override
        public Production createFromParcel(Parcel source) {
            Production production = new Production();

            production.fileImagePath = source.readString();
            production.fileName = source.readString();
            production.language = source.readString();
            production.produceYear = source.readString();
            production.productionDesc = source.readString();
            production.productionInfoId = source.readString();
            production.typeName = source.readString();

            return production;
        }

        @Override
        public Production[] newArray(int size) {
            return new Production[size];
        }
    };

}
