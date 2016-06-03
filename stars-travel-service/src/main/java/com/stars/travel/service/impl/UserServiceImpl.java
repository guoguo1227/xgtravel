package com.stars.travel.service.impl;

import com.stars.common.utils.Page;
import com.stars.common.utils.BeanUtilExt;
import com.stars.common.utils.EncryptUtil;
import com.stars.travel.dao.base.mapper.*;
import com.stars.travel.dao.ext.mapper.UserVoMapper;
import com.stars.travel.enums.LogType;
import com.stars.travel.model.base.*;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description : 用户管理service层实现
 * Author : guo
 * Date : 2015/12/22 23:06
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final Short  USER_ENABLE = 1; //用户状态 不受限

    @Autowired
    private UserMapper userMapper; //用户服务
    @Autowired
    private RoleMapper roleMapper; //角色服务
    @Autowired
    private PermissionMapper permissionMapper; //权限服务
    @Autowired
    private UserRoleMapper userRoleMapper; //用户角色服务
    @Autowired
    private RolePermissionMapper rolePermissionMapper; //角色权限服务
    @Autowired
    private UserVoMapper userVoMapper; //用户扩展服务
    @Autowired
    private JourneyService journeyService; //行程服务
    @Autowired
    private JourneyMapper journeymapper; //行程dao层服务
    @Autowired
    private MicroblogVoService microblogVoService; //微游记扩展服务
    @Autowired
    private CustomizationService customizationService; //定制服务
    @Autowired
    private UserCollectionMapper userCollectionMapper ; //当地人收藏服务
    @Autowired
    private MicroblogMapper microblogMapper; //微游记
    @Autowired
    private CustomizationMapper customizationMapper; //定制dao层服务

    @Autowired
    private LogService logService;

    @Override
    public User queryUserByPhoneNumber(String phone) {
        User user = null;
        if(!StringUtils.isBlank(phone)){
            UserCriteria criteria = new UserCriteria();
            criteria.createCriteria().andPhoneEqualTo(phone).andIsEnableEqualTo(true);
            List<User> userList = userMapper.selectByExample(criteria);
            if(null != userList && userList.size()>0){
                user = userList.get(0);
            }
        }
        return user;
    }

    @Override
    public boolean deleteUserById(Integer id) {
        if(null != id){
            User user = userMapper.selectByPrimaryKey(id);
            Date nowDate = new Date();
            if(null != user){
                //删除该用户发布的微游记
                MicroblogCriteria micriteria = new MicroblogCriteria();
                micriteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(user.getPhone());
                List<MicroblogWithBLOBs> microblogs = microblogMapper.selectByExampleWithBLOBs(micriteria);
                if(null != microblogs && microblogs.size()>0){
                    for(MicroblogWithBLOBs m : microblogs){
                        m.setIsEnable(false);
                        m.setUpdatetime(nowDate);
                        int i = microblogMapper.updateByPrimaryKeySelective(m);
                        if(i>0){
                            logger.info("成功删除用户:"+id+"的微游记:"+m.getId());
                        }else{
                            logger.info("删除用户:"+id+"的微游记:"+m.getId()+"失败。");
                        }
                    }
                }
                //删除该用户发布的行程
                JourneyCriteria journeyCriteria = new JourneyCriteria();
                journeyCriteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(user.getPhone());
                List<JourneyWithBLOBs> journeys = journeymapper.selectByExampleWithBLOBs(journeyCriteria);
                if(null != journeys && journeys.size()>0){
                    for(JourneyWithBLOBs j : journeys){
                        j.setIsEnable(false);
                        j.setUpdatetime(nowDate);
                        int i = journeymapper.updateByPrimaryKeySelective(j);
                        if(i>0){
                            logger.info("成功删除用户:"+id+"的行程:"+j.getId());
                        }else{
                            logger.info("删除用户:"+id+"的行程:"+j.getId()+"失败。");
                        }
                    }
                }

                //删除该用户的定制
                CustomizationCriteria criteria = new CustomizationCriteria();
                criteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(user.getPhone());

                List<Customization> customizations = customizationMapper.selectByExample(criteria);
                for(Customization c : customizations){
                    c.setIsEnable(true); //标记删除
                    c.setUpdatetime(nowDate);
                    int i = customizationMapper.updateByPrimaryKeySelective(c);
                    if(i>0){
                        logger.info("成功删除用户:"+id+"的定制:"+c.getId());
                    }else{
                        logger.info("删除用户:"+id+"的定制:"+c.getId()+"失败。");
                    }
                }

                user.setIsEnable(false); //标记删除
                int i = userMapper.updateByPrimaryKeySelective(user);
                if(i>0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean restoreUserById(Integer id) {
        if(null != id){
            User user = userMapper.selectByPrimaryKey(id);
            if(null != user){
                user.setIsEnable(true); //恢复
                int i = userMapper.updateByPrimaryKeySelective(user);
                if(i>0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean modifyUserInfo(UserInfo userInfo) {
        if(null != userInfo && !StringUtils.isBlank(userInfo.getPhone())){

            User user = queryUserByPhoneNumber(userInfo.getPhone());
            if(null != user){
                if(!StringUtils.isBlank(userInfo.getName())){
                    user.setName(userInfo.getName());
                }
                if(!StringUtils.isBlank(userInfo.getEmail())){
                    user.setEmail(userInfo.getEmail());
                }
                if(!StringUtils.isBlank(userInfo.getPortrait())){
                    user.setPortrait(userInfo.getPortrait());
                }
                if(!StringUtils.isBlank(userInfo.getSummary())){
                    user.setSummary(userInfo.getSummary());
                }
                if(!StringUtils.isBlank(userInfo.getIntroduceImage())){
                    user.setIntroduceImage(userInfo.getIntroduceImage());
                }
                if(!StringUtils.isBlank(userInfo.getIntroduction())){
                    user.setIntroduction(userInfo.getIntroduction());
                }
                if(!StringUtils.isBlank(userInfo.getName())){
                    user.setName(userInfo.getName());
                }
                if(!StringUtils.isBlank(userInfo.getAddress())){
                    user.setAddress(userInfo.getAddress());
                }
                if(!StringUtils.isBlank(userInfo.getProfession())){
                    user.setProfession(userInfo.getProfession());
                }
                if(!StringUtils.isBlank(userInfo.getSignature())){
                    user.setSignature(userInfo.getSignature());
                }
                if(!StringUtils.isBlank(userInfo.getStrategyInfo())){
                    user.setStrategyInfo(userInfo.getStrategyInfo());
                }
                user.setPassword(null); //不可修改密码
                int i = userMapper.updateByPrimaryKeySelective(user);
                if(i>0){
                    return true;
                }
            }else{
                logger.info("用户phone:"+userInfo.getPhone()+"不存在");
            }

        }
        return false;
    }

    @Override
    public boolean modifyUser(User user) {
        if(null != user && !StringUtils.isBlank(user.getPhone())){
            user.setPassword(null); //不可修改密码
            int i = userMapper.updateByPrimaryKeySelective(user);
            if(i>0){
                return true;
            }
        }
        return false;
    }
    @Override
    public User queryUserById(Integer userId) {
        User user = null;
        if(null != userId){
            user = userMapper.selectByPrimaryKey(userId);
        }
        return user;
    }

    @Override
    public List<User> listAllUser() {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.createCriteria().andIsEnableEqualTo(true); //可用
        List<User> userList = userMapper.selectByExample(userCriteria);
        return userList;
    }

    @Override
    public List<Role> queryRoleListByUser(Integer userId) {
        List<Role> roles = null;
        if(null != userId){
            UserRoleCriteria userRoleCriteria = new UserRoleCriteria();
            userRoleCriteria.createCriteria().andUserIdEqualTo(userId).andIsEnableEqualTo(true);//可用
            List<UserRole> userRoles = userRoleMapper.selectByExample(userRoleCriteria);
            if(null != userRoles && userRoles.size()>0){
                for(UserRole userRole : userRoles){
                    if(null != userRole){
                        Role role = roleMapper.selectByPrimaryKey(userRole.getRoleId());
                        if(null != role){
                            roles.add(role);
                        }
                    }
                }
            }
        }
        return roles;
    }

    @Override
    public Page<UserInfo> queryUserInfo(AuctionSearchCondition searchCondition, String currentPhone) {
        Page<UserInfo> userInfoPage = new Page<>();
        List<UserInfo> userInfoList = new ArrayList<>();

        userInfoPage.setPageSize(searchCondition.getLimit());
        Short type = 0;
        if(null != searchCondition){
            UserCriteria userCriteria = new UserCriteria();
            UserCriteria.Criteria criteria = userCriteria.createCriteria();
            //用户id, 查询详情
            if(null != searchCondition.getUserId()){
                criteria.andIdEqualTo(searchCondition.getUserId());
            }

            //用户类型
            if(!StringUtils.isBlank(searchCondition.getType())){
                if(searchCondition.getType().equals("1")){
                    type = 1;
                }
                criteria.andTypeEqualTo(type);
            }
            //用户昵称
            if(!StringUtils.isBlank(searchCondition.getName())){
                criteria.andNameEqualTo(searchCondition.getName());
            }
            //手机
            if(!StringUtils.isBlank(searchCondition.getPhone())){
                criteria.andPhoneEqualTo(searchCondition.getPhone());
            }

            //是否可用
            if(null != searchCondition.getIfEnable()){
                criteria.andIsEnableEqualTo(searchCondition.getIfEnable());
            }
            //是否激活
            if(null != searchCondition.getActivated()){
                criteria.andActivatedEqualTo(searchCondition.getActivated());
            }
            //邮箱
            if(!StringUtils.isBlank(searchCondition.getEmail())){
                criteria.andEmailEqualTo(searchCondition.getEmail());
            }

            //criteria.andIsEnableEqualTo(true); //可用
            userCriteria.setOrderByClause(" createtime desc ");
            Integer count = userMapper.countByExample(userCriteria);
            if(count>0){
                userCriteria.setLimitStart(searchCondition.getOffset());
                userCriteria.setLimitEnd(searchCondition.getLimit());
                List<User> userList = userMapper.selectByExample(userCriteria);
                if(null != userList && userList.size()>0){
                    for(User u : userList){
                        //封装用户属性
                        UserInfo userInfo = addUserExtAtrr(u,currentPhone);
                        userInfoList.add(userInfo);
                    }
                    userInfoPage.setPageData(userInfoList);
                    userInfoPage.setTotalCount(count);
                }
            }
        }
        return userInfoPage;
    }

    @Override
    public List<UserInfo> queryUserListApp(AuctionSearchCondition searchCondition,String currentPhone) {
        List<UserInfo> userInfoList = new ArrayList<>();

        Short type = 0;
        if(null != searchCondition){
            UserCriteria userCriteria = new UserCriteria();
            UserCriteria.Criteria criteria = userCriteria.createCriteria();

            //是否查询最新，历史
            if(null != searchCondition.getIfNew()){
                if(searchCondition.getIfNew()){
                    if(null != searchCondition.getfId()){
                        criteria.andIdGreaterThan(searchCondition.getfId());//大于该id
                    }
                }else{
                    if(null != searchCondition.getfId()){
                        criteria.andIdLessThan(searchCondition.getfId());//小于该id
                    }
                }
            }

            //用户类型
            if(!StringUtils.isBlank(searchCondition.getType())){
                if(searchCondition.getType().equals("1")){
                    type = 1;//当地人
                }
                criteria.andTypeEqualTo(type);
            }
            //用户昵称
            if(!StringUtils.isBlank(searchCondition.getName())){
                criteria.andNameEqualTo(searchCondition.getName());
            }
            //手机
            if(!StringUtils.isBlank(searchCondition.getPhone())){
                criteria.andPhoneEqualTo(searchCondition.getPhone());
            }

            //邮箱
            if(!StringUtils.isBlank(searchCondition.getEmail())){
                criteria.andEmailEqualTo(searchCondition.getEmail());
            }

            criteria.andIsEnableEqualTo(true); //可用
            userCriteria.setOrderByClause(" id desc "); //按id降序
            Integer count = userMapper.countByExample(userCriteria);
            if(count>0){
                userCriteria.setLimitStart(searchCondition.getOffset());
                userCriteria.setLimitEnd(searchCondition.getLimit());
                List<User> userList = userMapper.selectByExample(userCriteria);
                if(null != userList && userList.size()>0){
                    for(User u : userList){
                        //封装用户属性
                        UserInfo userInfo = addUserExtAtrr(u,currentPhone);
                        userInfoList.add(userInfo);
                    }
                }
            }
        }
        return userInfoList;
    }

    @Override
    public List<UserInfo> queryMyCollection(AuctionSearchCondition searchCondition, String currentPhone) {
        List<UserInfo> userInfoList = new ArrayList<>();

        Short state = 1;
        Short type = 0;
        List<String> phones = new ArrayList<>();
        if(null != searchCondition){
            UserCriteria userCriteria = new UserCriteria();
            UserCriteria.Criteria criteria = userCriteria.createCriteria();
            criteria.andStateEqualTo(state);
            criteria.andIsEnableEqualTo(true); //可用
            userCriteria.setOrderByClause(" id desc "); //按id降序

            UserCollectionCriteria collectionCriteria = new UserCollectionCriteria();
            collectionCriteria.createCriteria().andIsEnableEqualTo(true).andUserPhoneEqualTo(currentPhone);
            List<UserCollection> collections = userCollectionMapper.selectByExample(collectionCriteria);

            if(!collections.isEmpty()){
                for(UserCollection c : collections){
                    phones.add(c.getCollectionPhone());
                }
            }
            //是否查询最新，历史
            if(null != searchCondition.getIfNew()){
                if(searchCondition.getIfNew()){
                    if(null != searchCondition.getfId()){
                        criteria.andIdGreaterThan(searchCondition.getfId());//大于该id
                    }
                }else{
                    if(null != searchCondition.getfId()){
                        criteria.andIdLessThan(searchCondition.getfId());//小于该id
                    }
                }
            }

            if(phones.size()>0){
                Integer count = userMapper.countByExample(userCriteria);
                if(count>0){
                    userCriteria.setLimitStart(searchCondition.getOffset());
                    userCriteria.setLimitEnd(searchCondition.getLimit());
                    criteria.andPhoneIn(phones);
                    List<User> userList = userMapper.selectByExample(userCriteria);
                    if(!userList.isEmpty()){
                        for(User u : userList){
                            //封装用户属性
                            UserInfo userInfo = addUserExtAtrr(u,currentPhone);
                            userInfoList.add(userInfo);
                        }
                    }
                }
            }
        }
        return userInfoList;
    }

    @Override
    public List<Permission> queryPermissionListByRole(Integer roleId) {
        List<Permission> permissionList = null;
        if(null !=  roleId){
            RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
            rolePermissionCriteria.createCriteria().andRoleIdEqualTo(roleId);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectByExample(rolePermissionCriteria);
            if(null != rolePermissions){
                for(RolePermission rolePermission : rolePermissions){
                    if(null != rolePermission){
                        Permission permission = permissionMapper.selectByPrimaryKey(rolePermission.getPermissionId());
                        if(null != permission){
                            permissionList.add(permission);
                        }
                    }
                }

            }
        }
        return permissionList;
    }

    @Override
    public User registerMember(User user) {
        if(null != user && !StringUtils.isBlank(user.getPassword())){
            user.setPassword(EncryptUtil.md5(user.getPassword()));
            user.setCreatetime(new Date());
            user.setIsEnable(true);
            user.setState(USER_ENABLE);
            int i = userMapper.insert(user);
            if(i>0){
                //给用户添加角色
                return user;
            }
        }
        return null;
    }

    @Override
    public User active(String phone) {
        return null;
    }

    @Override
    public boolean phoneExits(String phone) {
        if(!StringUtils.isBlank(phone)){
            User u = userVoMapper.queryUserByPhone(phone);
            if(null != u){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePassword(String phone, String password) {
        if(!StringUtils.isBlank(phone) && !StringUtils.isBlank(password)) {
            password = EncryptUtil.md5(password);
            int i = userVoMapper.updatePassword(phone,password);
            if(i>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean collectionUser(String phone,String currentPhone) {
        boolean success = false;
        Date nowDate = new Date();
        int j = 0;
        if(!StringUtils.isBlank(phone) && !StringUtils.isBlank(currentPhone)){

            UserCollectionCriteria criteria = new UserCollectionCriteria();
            criteria.createCriteria().andCollectionPhoneEqualTo(phone).andUserPhoneEqualTo(currentPhone);
            List<UserCollection> list = userCollectionMapper.selectByExample(criteria);
            //记录存在
            if(!list.isEmpty()){
                UserCollection userCollection = list.get(0);
                userCollection.setUpdatetime(nowDate);
                userCollection.setIsEnable(true);
                j = userCollectionMapper.updateByPrimaryKeySelective(userCollection);
            }else{
                //记录不存在
                String phoneCollectionKey = phone+"-"+currentPhone;
                UserCollection userCollection = new UserCollection();
                userCollection.setCreatetime(nowDate);
                userCollection.setCollectionPhone(phone);
                userCollection.setUserPhone(currentPhone);
                userCollection.setPhoneCollectionKey(phoneCollectionKey);
                userCollection.setIsEnable(true);
                j = userCollectionMapper.insertSelective(userCollection);
            }
            if(j>0){
                success = true;
            }
        }
        return success;
    }

    @Override
    public boolean uncollectionUser(String phone,String currentPhone) {
        boolean success = false;
        Date nowDate = new Date();
        if(!StringUtils.isBlank(phone) && !StringUtils.isBlank(currentPhone)){
            UserCollectionCriteria cri = new UserCollectionCriteria();
            cri.createCriteria().andCollectionPhoneEqualTo(phone).andUserPhoneEqualTo(currentPhone).andIsEnableEqualTo(true);

            List<UserCollection> list = userCollectionMapper.selectByExample(cri);
            if(!list.isEmpty()){
                UserCollection collection = list.get(0);
                collection.setUpdatetime(nowDate);
                collection.setIsEnable(false);
                int i = userCollectionMapper.updateByPrimaryKeySelective(collection);
                if(i>0){
                    success = true;
                }
            }
        }
        return success;
    }

    @Override
    public boolean ifCollectionUser(String phone,String currentPhone) {
        boolean success = false;
        if(!StringUtils.isBlank(phone) && !StringUtils.isBlank(currentPhone)){
            UserCollectionCriteria cri = new UserCollectionCriteria();
            cri.createCriteria().andUserPhoneEqualTo(currentPhone).andCollectionPhoneEqualTo(phone).andIsEnableEqualTo(true);
            int collectionCount = userCollectionMapper.countByExample(cri);
            if(collectionCount > 0){
                success = true;
            }
        }
        return success;
    }

    @Override
    public String queryPhoneByToken(String token) {
        String phone = "";
        if(!StringUtils.isBlank(token)){
            UserCriteria userCriteria = new UserCriteria();
            userCriteria.createCriteria().andOauthidEqualTo(token);
            List<User> users = userMapper.selectByExample(userCriteria);
            if(null != users && users.size()>0){
                User user = users.get(0);
                phone = user.getPhone();
            }
        }
        return phone;
    }

    private UserInfo packageUserInfo(User u){
        UserInfo userInfo = new UserInfo();
        if(null != u){
            try {
                BeanUtilExt.copyProperties(userInfo,u);
            } catch (InvocationTargetException e) {
                logger.info("拷贝用户属性失败"+e.toString());
            } catch (IllegalAccessException e) {
                logger.info("拷贝用户属性失败"+e.toString());
            }

        }
        return userInfo;
    }

    /**
     * @Description : 添加用户扩展属性
     * @param u
     * @param currentPhone
     * @return
     */
    private UserInfo addUserExtAtrr(User u,String currentPhone){
        UserInfo userInfo = new UserInfo();
        try {
            BeanUtilExt.copyProperties(userInfo,u);
            userInfo.setCreateTime(u.getCreatetime());
            userInfo.setEnable(u.getIsEnable());
        } catch (InvocationTargetException e) {
            logger.info("属性拷贝异常,"+e.toString());
        } catch (IllegalAccessException e) {
            logger.info("属性拷贝异常,"+e.toString());
        }

        //行程分享数量
        AuctionSearchCondition journeyContion = new AuctionSearchCondition();
        journeyContion.setUserId(u.getId());
        int journeyCount = journeyService.queryJourneyCount(journeyContion);
        userInfo.setJourneyNumber(journeyCount+"");
        //微游记数量
        int microbloCount = microblogVoService.countSharedMicroblogVoList(journeyContion);
        userInfo.setMicroblogNumber(microbloCount+"");
        //定制数量
        int customCount = customizationService.countCustomization(journeyContion);
        userInfo.setCustomCount(customCount+"");
        //当前用户是否收藏
        if(!StringUtils.isBlank(currentPhone)){
            userInfo.setIfCollection(ifCollectionUser(u.getPhone(),currentPhone));
        }else{
            userInfo.setIfCollection(false);
        }
        return userInfo;
    }

    /**
     * @Description : 记录日志
     */
    private void addLog(String type,String title){
        Log log = new Log();
        log.setLogCategory(type);
        log.setLogTime(new Date());
        log.setTitle(title);
        logService.addLog(log);
    }
}
