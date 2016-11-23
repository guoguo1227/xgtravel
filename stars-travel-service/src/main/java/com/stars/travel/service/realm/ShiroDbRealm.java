package com.stars.travel.service.realm;

import com.stars.common.utils.BeanUtilExt;
import com.stars.common.utils.EncryptUtil;
import com.stars.travel.common.util.DateUtil;
import com.stars.travel.model.base.Permission;
import com.stars.travel.model.base.Role;
import com.stars.travel.model.base.User;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.stars.common.utils.TravelsUtils.USER_KEY;

/**
 * @类名: ShiroDbRealm
 * @描述: 自定义的指定Shiro验证用户登录的类
 * @版本: 
 * @创建日期: 2015-1-9下午02:27:27
 * @作者: 01
 * @JDK: 1.6
 */
/*
* 类的横向关系：
*/
public class ShiroDbRealm extends AuthorizingRealm {

	private Logger logger = org.apache.log4j.Logger.getLogger(ShiroDbRealm.class);

	@Autowired
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

    /**
     * 为当前登录的Subject授予角色和权限 
     * @see :本例中该方法的调用时机为需授权资源被访问时
     * @see :并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例未启用AuthorizationCache
     * @see : web层可以有shiro的缓存，dao层可以配有hibernate的缓存（后面介绍）
     */ 
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		//获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()  
		String userName = (String) super.getAvailablePrincipal(principals);
		
		List<String> roles = new ArrayList<>();
		List<String> permissions = new ArrayList<>();
		
		//从数据库中获取当前登录用户的详细信息  
		User user = userService.queryUserByPhoneNumber(userName);
		
		if(user != null){
			//实体类User中包含有用户角色的实体类信息
			List<Role> roleList = userService.queryRoleListByUser(user.getId());
			if (roleList != null && roleList.size() > 0) {
				//获取当前登录用户的角色 
				for (Role role : roleList) {
					roles.add(role.getRoleName());
					 //实体类Role中包含有角色权限的实体类信息
					List<Permission> permissionList  = userService.queryPermissionListByRole(role.getRoleId());
					if (permissionList != null && permissionList.size() > 0) {
						 //获取权限  
						for (Permission pmss : permissionList) {
							if(!StringUtils.isEmpty(pmss.getPermission())){
								permissions.add(pmss.getPermission());
							}
						}
					}
				}
			}
		}else{
			throw new AuthorizationException();
		}
		
		//为当前用户设置角色和权限
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roles);
        info.addStringPermissions(permissions); 
        
		return info;
		
	}

	/**
	 * removeUserAuthorizationInfoCache
	 * @描述: 删除某个用户关联的权限认证
	 * @作者: gzs
	 * @创建时间: 2015-1-12下午04:03:35
	 * 
	 * @修改描述: 无
	 * @修改人: gzs
	 * @修改时间: 2015-1-12下午04:03:35
	 * @param username
	 */
	 
	public void clearUserAuthorizationInfoCache(String username){
		  SimplePrincipalCollection pc = new SimplePrincipalCollection();
		  pc.add(username, super.getName()); 
		  super.clearCachedAuthorizationInfo(pc);
	}
	
    /** 
     * 验证当前登录的Subject 
     * @see :本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */  
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		//获取基于用户名和密码的令牌  
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的  
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = null;
		String phone = token.getUsername();
		String passwd = new String(token.getPassword());

		logger.info("phone:"+phone+",passwd:"+passwd);
		if(!StringUtils.isBlank(phone)){
			user = userService.queryUserByPhoneNumber(phone);
			if(null != user){
				logger.info("用户的密码为:"+user.getPassword());
				if(EncryptUtil.md5(passwd).equals(user.getPassword())){
					//生成token
					String time = DateUtil.date2stringFormate(new Date());
					String tokenId = EncryptUtil.md5(user.getId()+user.getPassword()+time);
					user.setOauthid(tokenId);
					user.setLastlogintime(new Date());
					userService.modifyUser(user);
					//封装用户属性
					UserInfo userInfo = packageUserInfo(user);
					this.setSession(USER_KEY, userInfo);
					return  new SimpleAuthenticationInfo(token.getUsername(),token.getPassword(), getName());
				}
			}else{
				logger.info("没有账号:"+phone);
				throw new UnknownAccountException();//没找到帐号
			}
		}
		return null;
	}
	/**
	 * ShiroSession设置
	 *
	 * @see //使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			if (null != session) {
				session.setAttribute(key, value);
				logger.info("ShiroSession设置"+key+value.toString());
			}
		}
	}

	/**
	 * @Description : 封装用户信息
	 * @param user
	 * @return
	 */
	private UserInfo packageUserInfo(User user){
		if(null == user){
			return  null;
		}
		UserInfo userInfo = new UserInfo();
		try {
			BeanUtilExt.copyProperties(userInfo,user);
			userInfo.setToken(user.getOauthid());
		} catch (InvocationTargetException e) {
			logger.info("拷贝用户属性失败"+e.toString());
		} catch (IllegalAccessException e) {
			logger.info("拷贝用户属性是吧"+e.toString());
		}
		return  userInfo;
	}
}

