package com.stars.travel.model.ext;

import com.stars.travel.model.base.Comment;

/**
 * Description :
 * Author : guo
 * Date : 2016/3/20 13:50
 */
public class CommentVo extends Comment {

    public String title; //关联标题

    public UserInfo userInfo = new UserInfo();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
