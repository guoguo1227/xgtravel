package com.stars.travel.web;

import com.stars.common.service.Assertion;
import com.stars.common.service.HttpRequestService;
import com.stars.common.service.impl.HttpRequestServiceImpl;
import com.stars.travel.model.ext.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/2 22:05
 */
public class UserManager {
    private static HttpRequestService httpRequestService = HttpRequestServiceImpl.getIns();

    public UserManager() {
    }

    public static UserInfo getSessionUser(HttpServletRequest request) {
        if(request == null) {
            return null;
        } else {
            HttpSession session = request.getSession();
            if(session == null) {
                return null;
            } else {
                Assertion assertion = (Assertion)session.getAttribute("_const_cas_assertion_");
                if(assertion != null && assertion.getUserInfo() != null) {
                    UserInfo user = new UserInfo();
                    user.setEmail(assertion.getUserInfo().getEmail());
                    user.setId(assertion.getUserInfo().getId());
                    return user;
                } else {
                    return null;
                }
            }
        }
    }
}
