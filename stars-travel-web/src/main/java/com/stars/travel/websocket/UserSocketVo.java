package com.stars.travel.websocket;

import org.springframework.web.socket.WebSocketSession;

import java.util.Date;

/**
 * @author : samuel
 * @Description : 用户socket连接实体
 * @Date : 16-3-7
 */
public class UserSocketVo {

    private String userEmail;  //用户邮箱
    private Date connectionTime;    //成功连接时间
    private Date preRequestTime;    //上次请求时间
    private Date newRequestTime;    //新请求时间
    private Date lastSendTime = new Date();  //下架消息最近一次发送时间
    private Date lastTaskSendTime = new Date();  //待处理任务最近一次发送时间
    private WebSocketSession webSocketSession;  //用户对应的wsSession 默认仅缓存一个

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getConnectionTime() {
        return connectionTime;
    }

    public void setConnectionTime(Date connectionTime) {
        this.connectionTime = connectionTime;
    }

    public Date getPreRequestTime() {
        return preRequestTime;
    }

    public void setPreRequestTime(Date preRequestTime) {
        this.preRequestTime = preRequestTime;
    }

    public Date getNewRequestTime() {
        return newRequestTime;
    }

    public void setNewRequestTime(Date newRequestTime) {
        this.newRequestTime = newRequestTime;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }

    public Date getLastTaskSendTime() {
        return lastTaskSendTime;
    }

    public void setLastTaskSendTime(Date lastTaskSendTime) {
        this.lastTaskSendTime = lastTaskSendTime;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}
