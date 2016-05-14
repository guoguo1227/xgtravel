package com.stars.travel.service;

import com.lagou.platform.common.Page;
import com.stars.travel.model.base.Permission;
import com.stars.travel.model.base.Role;
import com.stars.travel.model.base.User;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.UserInfo;

import java.util.List;

/**
 * Description : 用户管理service层接口
 * Author : guo
 * Date : 2015/12/22 22:47
 */
public interface UserService {
    public User queryUserByPhoneNumber(String phone);

    public boolean deleteUserById(Integer id);

    public boolean modifyUser(User user) ;

    public User queryUserById(Integer userId) ;

    public List<User> listAllUser() ;

    public List<Role> queryRoleListByUser(Integer useId);

    public Page<UserInfo> queryUserInfo(AuctionSearchCondition searchCondition, String currentPhone);

    public List<UserInfo> queryUserListApp(AuctionSearchCondition searchCondition, String currentPhone);

    public List<Permission> queryPermissionListByRole(Integer roleId);

    /**
     * @Description : 会员注册
     * @param user
     * @return
     */
    public User registerMember(User user);

    /**
     * @Description : 是否激活
     * @param phone
     * @return
     */
    public User active(String phone);

    /**
     * @Description : 验证手机号码是否存在
     * @param phone
     * @return
     */
    public boolean phoneExits(String phone);

    /**
     * @Description : 修改密码
     * @param phone
     * @param password
     * @return
     */
    public boolean updatePassword(String phone,String password);

    /**
     * @Description : 收藏当地人
     * @param userPhone
     * @param currentPhone
     * @return
     */
    public boolean collectionUser(String userPhone,String currentPhone);

    /**
     * @Description : 取消收藏当地人
     * @param userPhone
     * @param currentPhone
     * @return
     */
    public boolean uncollectionUser(String userPhone,String currentPhone);

    /**
     * @Description :　是否收藏该当地人
     * @param userPhone
     * @param currentPhone
     * @return
     */
    public boolean ifCollectionUser(String userPhone,String currentPhone);

    /**
     * @Description : 根据token查询用户手机
     * @param token
     * @return
     */
    public String queryPhoneByToken(String token);
}
