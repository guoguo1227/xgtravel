package com.stars.travel.model.ext;

import com.stars.travel.model.base.JourneyWithBLOBs;

import java.util.ArrayList;
import java.util.List;


/**
 * Description : 行程分享rest层实体
 * Author : guo
 * Date : 2016/2/1 23:38
 */
public class JourneyVo extends JourneyWithBLOBs {

    private boolean ifComment = false ; //该用户是否对该行程评论

    private boolean ifCollection = false ; //该用户是否收藏

    private boolean ifTop  = false ; //该用户是否顶赞

    private String commentCount = "0" ; //评论数

    private UserInfo userInfo = new UserInfo(); //行程所属用户

    private String token;  //APP授权token

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

    List<JourneyDayVo> journeyDayVoList = new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<JourneyDayVo> getJourneyDayVoList() {
        return journeyDayVoList;
    }

    public void setJourneyDayVoList(List<JourneyDayVo> journeyDayVoList) {
        this.journeyDayVoList = journeyDayVoList;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

}
