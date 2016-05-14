package com.stars.travel.service;

import com.stars.travel.model.base.User;

/**
 * Description : 用户登录服务接口
 * Author : guo
 * Date : 2015/12/29 0:43
 */
public interface LoginService {
    /**
     * @Description : 该手机号是否注册过
     * @param phone
     * @return
     */
    public boolean phoneNumberNotExist(String phone);

    /**
     * @Description : 用户注册
     * @param user
     * @return
     */
    public User registerUser(User user);

}
