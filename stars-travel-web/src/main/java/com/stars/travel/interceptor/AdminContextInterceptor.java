package com.stars.travel.interceptor;

import com.stars.common.utils.BeanUtilExt;
import com.stars.common.utils.TravelsUtils;
import com.stars.travel.model.base.User;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.UserService;
import com.stars.travel.service.realm.ShiroDbRealm;
import com.stars.travel.web.ThreadVariable;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * @类名: FrontContextInterceptor
 * @描述: 实现对前台uri请求的拦截处理,包括登录、权限及站点信息
 * @版本:
 * @创建日期: 2015-1-13上午10:00:20
 * @作者: gzs
 *
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {
	/**
	 * Logger log 日志实体
	 */
	private static final Logger log = Logger.getLogger(AdminContextInterceptor.class);
	
	/**
	 * String PERMISSION_MODEL 权限key
	 */
	public static final String PERMISSION_MODEL = "_permission_key";
	
	/**
	 * String[] excludeUrls 拦截登录请求之后需要执行的url
	 */
	private String[] excludeUrls = null;
	
	/**
	 * setExcludeUrls
	 * @描述: 设置无权限验证的url
	 * @作者: chengr
	 * @创建时间: 2015-1-9下午02:07:22
	 * @param excludeUrls 无需权限验证的引入URL
	 *  返回值为空
	 */
	 
	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
	
	/**
	 * boolean auth 权限boolea值
	 */
	private boolean auth = true;
	
	/**
	 * setAuth
	 * @描述: 设置权限认证
	 * @作者: chengr
	 * @创建时间: 2015-1-9下午01:50:34
	 * @param auth 默认传入值为true
	 *  返回值为空
	 */
	 
	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	/**
	 * IUserService userService 用户服务层实体
	 */
	@Resource(name = "userService")
	private UserService userService = null;
	
	/**
	 * ShiroDbRealm shiroDbRealm 权限验证服务
	 */
	@Resource(name = "shiroDbRealm")
	private ShiroDbRealm shiroDbRealm = null;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		
		//获取用户并存入本地线程
		User user = null;
		UserInfo userInfo = null;
		Subject curUser = SecurityUtils.getSubject();
		if(curUser.isAuthenticated()) {
			String username = (String)curUser.getPrincipal();
			user = userService.queryUserByPhoneNumber(username);
		}
		TravelsUtils.setUser(request, user);
		//封装用户属性
		userInfo = packageUserInfo(user);
		ThreadVariable.setUser(userInfo);
		
		//判断请求的uri是否需要进行权限验证
		String uri = getURI(request);
		if(exclude(uri)) {
			return true;
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView mav) throws Exception {
		User user = TravelsUtils.getUser(request);

		// 不控制权限时perm为null，PermistionDirective标签将以此作为依据不处理权限问题。
		/*if (auth && null != user && !user.isSuper() && null != mav
				&& null != mav.getModelMap() && null != mav.getViewName()
				&& !mav.getViewName().startsWith("redirect:")) {
			mav.getModelMap().addAttribute(PERMISSION_MODEL, getUserPermission(site, user));
		}*/
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// Sevlet容器有可能使用线程池，所以必须手动清空线程变量。
		ThreadVariable.removeUser();
	}

	
	/**
	 * getURI
	 * @描述: 获得第三个路径分隔符的位置
	 * @param request HTTP请求实体
	 * @return
	 * @throws IllegalStateException
	 *  获取请求路径,不包括域名
	 */
	 
	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		int start = 0;
		int i = 0;
		int count = 1;
		if (!StringUtils.isBlank(ctxPath)) {
			count++;
		}
		
		while (i < count && start != -1) {
			start = uri.indexOf('/', start + 1);
			i++;
		}
		
		if (start <= 0) {
			throw new IllegalStateException("admin access path not like '/phycms/admin/...' pattern: " + uri);
		}
		
		return uri.substring(start);
	}
	
	/**
	 * exclude
	 * @描述: 判断请求的uri是否不在权限验证范围内
	 * @param uri 请求uri
	 * @return
	 *  不在权限验证范围内则返回真
	 */
	 
	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
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
		} catch (InvocationTargetException e) {
			log.info("拷贝用户属性失败"+e.toString());
		} catch (IllegalAccessException e) {
			log.info("拷贝用户属性是吧"+e.toString());
		}
		return  userInfo;
	}
}