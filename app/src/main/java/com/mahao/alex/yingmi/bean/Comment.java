package com.mahao.alex.yingmi.bean;

/**
 * 评论
 * Created by Alex_MaHao on 2016/5/1.
 */
public class Comment {

    private int commentId;

    private String commentContent;

    private String type;


    private int userId;

    private int goldId;

    private String userName;

    private String userIcon;

    private String time;


    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getGoldId() {
        return goldId;
    }

    public void setGoldId(int goldId) {
        this.goldId = goldId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
