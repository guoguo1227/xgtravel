package com.stars.travel.web;


import com.stars.travel.model.ext.UserInfo;

/**
 * Description :实现登录用户的线程记录
 * Author : guo
 * Date : 2016/1/17 23:20
 */
public class ThreadVariable {
    /**
     * ThreadLocal<User> userVariable 用户线程
     */
    private static ThreadLocal<UserInfo> userVariable = new ThreadLocal<UserInfo>();

    /**
     * 获得当前用户
     *
     * @return
     */
    public static UserInfo getUser() {
        return userVariable.get();
    }

    /**
     * 设置当前用户
     *
     * @param user
     */
    public static void setUser(UserInfo user) {
        userVariable.set(user);
    }

    /**
     * 移除当前用户
     */
    public static void removeUser() {
        userVariable.remove();
    }

}
