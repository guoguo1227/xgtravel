package com.stars.travel.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Description :提供session服务，如获取session标识,设置session值
 * Author : guo
 * Date : 2016/2/17 0:04
 */
public interface SessionProvider {
    public Serializable getAttribute(HttpServletRequest request, String name);

    public void setAttribute(HttpServletRequest request,
                             HttpServletResponse response, String name, Serializable value);

    public String getSessionId(HttpServletRequest request,
                               HttpServletResponse response);
    public void logout(HttpServletRequest request, HttpServletResponse response);
}
