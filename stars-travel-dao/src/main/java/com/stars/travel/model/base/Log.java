package com.stars.travel.model.base;

import com.lagou.common.base.bean.BaseBean;
import java.util.Date;

public class Log implements BaseBean {
    private static final long serialVersionUID = 1L;

    private Integer logId;

    private String logCategory;

    private String content;

    private String ip;

    private Date logTime;

    private String title;

    private String url;

    private Integer userId;

    private Boolean isEnable;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getLogCategory() {
        return logCategory;
    }

    public void setLogCategory(String logCategory) {
        this.logCategory = logCategory == null ? null : logCategory.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    /** 
     * 拷贝，将对象中的字段全部拷贝到子对象中
     * @param bean 接收对象的子类
     * @return 拷贝完成后的子类
     */ 
    public  <T extends Log> T copy(T bean) {
        bean.setLogId(getLogId());
        bean.setLogCategory(getLogCategory());
        bean.setContent(getContent());
        bean.setIp(getIp());
        bean.setLogTime(getLogTime());
        bean.setTitle(getTitle());
        bean.setUrl(getUrl());
        bean.setUserId(getUserId());
        bean.setIsEnable(getIsEnable());
        return bean;
    }

    /** 
     * 格式化显示
     */ 
    @Override
    public String toString() {
        return "{" + 
        	"logId:" + getLogId() + 
        	", logCategory:" + getLogCategory() + 
        	", content:" + getContent() + 
        	", ip:" + getIp() + 
        	", logTime:" + getLogTime() + 
        	", title:" + getTitle() + 
        	", url:" + getUrl() + 
        	", userId:" + getUserId() + 
        	", isEnable:" + getIsEnable() + 
        "}";
    }
}