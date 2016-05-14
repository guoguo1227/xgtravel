package com.stars.common.utils;

import com.stars.travel.model.base.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Description :提供一些统中使用到的共用方法,比如获得会员信息,获得后台站点信息
 * Author : guo
 * Date : 2016/1/17 23:16
 */
public class TravelsUtils {
    /**
     * String USER_KEY 用户KEY
     */
    public static final String USER_KEY = "_user_key";

    /**
     * 获得用户
     *
     * @param request HTTP请求对象
     * @return
     * 用户实体
     */
    public static User getUser(HttpServletRequest request) {
        return (User) request.getAttribute(USER_KEY);
    }

    /**
     * 获得用户ID
     *
     * @param request HTTP请求对象
     * @return
     * 用户标识
     */
    public static Integer getUserId(HttpServletRequest request) {
        User user = getUser(request);
        if (user != null) {
            return user.getId();
        } else {
            return null;
        }
    }

    public static void removeUser(HttpServletRequest request){
        request.removeAttribute(USER_KEY);
    }

    /**
     * 设置用户
     *
     * @param request HTTP请求对象
     * @param user 设置用户实体
     */
    public static void setUser(HttpServletRequest request, User user) {
        request.setAttribute(USER_KEY, user);
    }
}
