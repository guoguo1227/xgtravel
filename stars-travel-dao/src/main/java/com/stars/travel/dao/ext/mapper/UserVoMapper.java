package com.stars.travel.dao.ext.mapper;

import com.stars.travel.model.base.User;
import org.apache.ibatis.annotations.Param;

/**
 * Description : 用户扩展服务
 * Author : guo
 * Date : 2016/1/19 22:12
 */
public interface UserVoMapper {

    public User queryUserByPhone(@Param("phone") String phone);

    /**
     * @Description : 更新用户密码
     * @param phone
     * @param password
     * @return
     */
    public int updatePassword(@Param("phone") String phone,@Param("password") String password);
}

