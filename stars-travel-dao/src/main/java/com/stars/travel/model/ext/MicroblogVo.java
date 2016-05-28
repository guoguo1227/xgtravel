package com.stars.travel.model.ext;

import com.stars.travel.model.base.MicroblogWithBLOBs;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/27 21:48
 */
public class MicroblogVo extends MicroblogWithBLOBs {

    private UserInfo userInfo = new UserInfo();

    private boolean ifComment = false ; //该用户是否对该行程评论

    private boolean ifCollection = false ; //该用户是否收藏

    private boolean ifTop = false ; //该用户是否顶赞

    private String commentCount = "0" ; //评论数

    private String token = ""; //app登录授权码

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isIfComment() {
        return ifComment;
    }

    public void setIfComment(boolean ifComment) {
        this.ifComment = ifComment;
    }

    public boolean isIfCollection() {
        return ifCollection;
    }

    public void setIfCollection(boolean ifCollection) {
        this.ifCollection = ifCollection;
    }

    public boolean isIfTop() {
        return ifTop;
    }

    public void setIfTop(boolean ifTop) {
        this.ifTop = ifTop;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
