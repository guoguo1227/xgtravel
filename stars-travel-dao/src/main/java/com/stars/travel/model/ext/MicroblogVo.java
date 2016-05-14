package com.stars.travel.model.ext;

import com.stars.travel.model.base.MicroblogWithBLOBs;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/27 21:48
 */
public class MicroblogVo extends MicroblogWithBLOBs {

    private UserInfo userInfo = new UserInfo();

    private boolean isComment = false ; //该用户是否对该行程评论

    private boolean isCollection = false ; //该用户是否收藏

    private boolean hasTop = false ; //该用户是否顶赞

    private String commentCount = "0" ; //评论数

    private String topCount = "0"; //点赞数

    private String token = ""; //app登录授权码

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isHasTop() {
        return hasTop;
    }

    public void setHasTop(boolean hasTop) {
        this.hasTop = hasTop;
    }

    public boolean isComment() {
        return isComment;
    }

    public void setComment(boolean comment) {
        isComment = comment;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
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

    public String getTopCount() {
        return topCount;
    }

    public void setTopCount(String topCount) {
        this.topCount = topCount;
    }
}
