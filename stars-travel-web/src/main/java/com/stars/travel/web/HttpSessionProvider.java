package com.stars.travel.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stars.travel.model.ext.UserInfo;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Description :获取session服务,如获取session标识等
 * Author : guo
 * Date : 2016/2/17 0:04
 */
public class HttpSessionProvider implements SessionProvider{
    private final static Logger logger = Logger.getLogger(HttpSessionProvider.class);
    private final static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public Serializable getAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (Serializable) session.getAttribute(name);
        } else {
            return null;
        }
    }

    @Override
    public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value) {
        HttpSession session = request.getSession();
        session.setAttribute(name, value);
    }

    @Override
    public String getSessionId(HttpServletRequest request, HttpServletResponse response) {
        return request.getSession().getId();
    }

    public static UserInfo getSessionUser(HttpServletRequest request, String name) {
        if(request == null){
            return  null;
        }else{
            HttpSession session = request.getSession();
            if (session != null) {
                return (UserInfo) session.getAttribute(name);
            } else {
                logger.info("session为空");
                return null;
            }
        }
    }

    public static String getSessionUserPhone(){
        String phone = "";
        Subject currentUser = SecurityUtils.getSubject();
        if(null != currentUser){
            phone = (String) currentUser.getPrincipal();
        }
        return phone;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
