package com.stars.travel.model.ext;

import java.io.Serializable;
import java.util.Date;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/2 22:24
 */
public class UserInfo implements Serializable {
    private Integer id;
    private String nikename = ""; //昵称
    private String name = ""; //用户名
    private Date createTime;
    private int type = 0;
    private int isActive = 0;
    private String email = "";
    private String password = "";
    private String phone = "";
    private String portrait = ""; //头像
    private String summary = ""; //个人简介

    private String token ; //app登录授权码

    private String introduceImage = ""; //标题图

    private String introduction = ""; //介绍

    private String address = ""; //地址

    private String profession = ""; //职业

    private String journeyNumber = "0" ; //行程分享数量

    private String microblogNumber = "0" ; //微游记数量

    private String signature = "";      //个性签名

    private String strategyInfo = "";   //攻略介绍

    private String score = "0" ; //评分

    private String price = "0"; //价格

    private String customCount = "0" ; //定制人数

    private String attentionCount = "0" ; //关注人数

    private boolean ifCollection; //是否收藏

    public UserInfo() {
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isIfCollection() {
        return ifCollection;
    }

    public void setIfCollection(boolean ifCollection) {
        this.ifCollection = ifCollection;
    }

    public String getNikename() {
        return nikename;
    }

    public void setNikename(String nikename) {
        this.nikename = nikename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public String getIntroduceImage() {
        return introduceImage;
    }

    public void setIntroduceImage(String introduceImage) {
        this.introduceImage = introduceImage;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsActive() {
        return this.isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getJourneyNumber() {
        return journeyNumber;
    }

    public void setJourneyNumber(String journeyNumber) {
        this.journeyNumber = journeyNumber;
    }

    public String getMicroblogNumber() {
        return microblogNumber;
    }

    public void setMicroblogNumber(String microblogNumber) {
        this.microblogNumber = microblogNumber;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getStrategyInfo() {
        return strategyInfo;
    }

    public void setStrategyInfo(String strategyInfo) {
        this.strategyInfo = strategyInfo;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCustomCount() {
        return customCount;
    }

    public void setCustomCount(String customCount) {
        this.customCount = customCount;
    }

    public String getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(String attentionCount) {
        this.attentionCount = attentionCount;
    }
}
