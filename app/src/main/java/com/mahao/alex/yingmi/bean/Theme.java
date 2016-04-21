package com.mahao.alex.yingmi.bean;

import java.util.List;

/**
 * 主题
 * Created by mdw on 2016/4/21.
 */
public class Theme {

    private String themeImg;

    private String themeDesc;

    private String themeId;

    private List<Commodity> list;

    private String themeUsername;

    private String themeIcon;

    public String getThemeImg() {
        return themeImg;
    }

    public void setThemeImg(String themeImg) {
        this.themeImg = themeImg;
    }

    public String getThemeDesc() {
        return themeDesc;
    }

    public void setThemeDesc(String themeDesc) {
        this.themeDesc = themeDesc;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public List<Commodity> getList() {
        return list;
    }

    public void setList(List<Commodity> list) {
        this.list = list;
    }

    public String getThemeUsername() {
        return themeUsername;
    }

    public void setThemeUsername(String themeUsername) {
        this.themeUsername = themeUsername;
    }

    public String getThemeIcon() {
        return themeIcon;
    }

    public void setThemeIcon(String themeIcon) {
        this.themeIcon = themeIcon;
    }
}
