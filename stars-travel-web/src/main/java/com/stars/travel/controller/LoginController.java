package com.stars.travel.controller;

import com.stars.common.utils.BeanUtilExt;
import com.stars.common.utils.TravelsUtils;
import com.stars.travel.enums.LogType;
import com.stars.travel.model.base.Log;
import com.stars.travel.model.base.User;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.LogService;
import com.stars.travel.service.UserService;
import com.stars.travel.web.ThreadVariable;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * Description : 用户注册登录接口
 * Author : guo
 * Date : 2015/12/29 0:17
 */
@Controller
@RequestMapping("login")
public class LoginController extends BaseController{
private Logger logger = Logger.getLogger(UserController.class);

 @Resource
 private UserService userService;

 @Resource
 private LogService logService;

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request,
                         HttpServletResponse response, ModelMap model) {
        //获取当前的Subject
        Subject curUser = SecurityUtils.getSubject();
        boolean flag = curUser.isAuthenticated();
        //如果用户已经登陆
        if(flag){
            //用户登出
            curUser.logout();	//从shiro中退出
            TravelsUtils.removeUser(request); //从request中删除user标识
            ThreadVariable.removeUser();	//从用户记录本地线程中删除user

            logger.info("用户登出。");
        }

        return "/login/loginfalse";
    }

    /**
     * @Description: 用户登录
     * @param user
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Object submit(User user){
        UserInfo userInfo= null;
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        //获取当前的Subject
        Subject curUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = null;
        //密码不为空
        if(null != user){
            String password = user.getPassword();
            if(!StringUtils.isBlank(password)){
                //用户手机、密码
                token = new UsernamePasswordToken(user.getPhone(),password);
            }else{
                return gson.toJson(result);
            }

            logger.info("phone为:"+user.getPhone()+"用户登录。");
            addLog(user);
        }

        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到ShiroDbRealm.doGetAuthenticationInfo()方法中
            curUser.login(token);

            assert user != null;
            userInfo  = userService.queryUserInfoByPhone(user.getPhone(),user.getPhone());

            //获取token
            User tmp = userService.queryUserById(userInfo.getId());
            if(null != tmp){
                userInfo.setToken(tmp.getOauthid());
            }
            result.setData(userInfo);
            result.setSuccess(true);
            logger.info("用户登陆成功。");
        }catch (AuthenticationException e) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            token.clear();
            logger.info("用户登陆失败。");
            return gson.toJson(result);
        }finally{
            //登录成功后，将当前用户设置为此用户
            ThreadVariable.setUser(userInfo);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 记录日志
     * @param user
     */
    private void addLog(User user){
        Log log = new Log();
        log.setLogCategory(LogType.USER_LOGIN.getDescription());
        log.setLogTime(new Date());
        log.setTitle("用户"+user.getPhone()+"登录");
        logService.addLog(log);
    }
}
