package com.stars.travel.service;

import com.stars.common.utils.Page;
import com.stars.travel.model.base.Permission;
import com.stars.travel.model.base.Role;
import com.stars.travel.model.base.User;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.UserInfo;
import org.json.simple.JSONObject;

import java.util.List;

/**
 * Description : 用户管理service层接口
 * Author : guo
 * Date : 2015/12/22 22:47
 */
public interface UserService {

    public User queryUserByPhoneNumber(String phone);

    public boolean deleteUserById(Integer id,Boolean deleteFlag);

    public boolean restoreUserById(Integer id);

    public boolean modifyUserInfo(UserInfo userInfo) ;

    public boolean modifyUser(User user);

    public User queryUserById(Integer userId) ;

    public List<Role> queryRoleListByUser(Integer useId);

    public Page<UserInfo> queryUserInfo(SearchCondition searchCondition, String currentPhone);

    public List<UserInfo> queryUserListApp(SearchCondition searchCondition, String currentPhone);

    public List<UserInfo> queryMyCollection(SearchCondition searchCondition, String currentPhone);

    /**
     * @Description:根据phone查询用户信息
     * @param phone 查询用户手机
     * @param currentPhone 当前用户手机
     * @return
     */
    public UserInfo queryUserInfoByPhone(String phone,String currentPhone);

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
     * @param phone
     * @param currentPhone
     * @return
     */
    public boolean collectionUser(String phone,String currentPhone);

    /**
     * @Description : 取消收藏当地人
     * @param phone
     * @param currentPhone
     * @return
     */
    public boolean uncollectionUser(String phone,String currentPhone);

    /**
     * @Description :　是否收藏该当地人
     * @param phone
     * @param currentPhone
     * @return
     */
    public boolean ifCollectionUser(String phone,String currentPhone);

    /**
     * @Description : 根据token查询用户手机
     * @param token
     * @return
     */
    public String queryPhoneByToken(String token);

    /**
     * @Description : 查询用户主页
     * @param condition
     * @return
     */
    public JSONObject queryUserHomePage(SearchCondition condition, String currentPhone);
}
