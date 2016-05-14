package com.stars.travel.controller;

import com.stars.travel.enums.LogType;
import com.stars.travel.model.base.Log;
import com.stars.travel.model.base.User;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.service.LogService;
import com.stars.travel.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Description : 会员注册控制器
 * Author : guo
 * Date : 2016/1/18 23:23
 */
@Controller
@RequestMapping("register")
public class RegisterController extends BaseController{
    private Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private LogService logService;
    /**
     * register
     * @描述: 用户注册
     * @作者: gzs
     */

    @RequestMapping(value = "register" , method = RequestMethod.POST)
    @ResponseBody
    public Object register(User user) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != user){
            boolean flag = userService.phoneExits(user.getPhone());
            if(!flag){
                User u = userService.registerMember(user);
                if(null != u){
                    result.setSuccess(true);
                    u.setPassword(null);
                    result.setData(u);
                }
            }
            logger.info("用户注册,phone为:"+user.getPhone());
            addLog(user.getPhone());
        }

        return gson.toJson(result);
    }

    /**
     * active
     * @描述: 是否激活
     * @作者: gzs
     * @创建时间: 2015-2-3上午10:56:41
     * @return
     */

    @RequestMapping(value ="active"  , method = RequestMethod.GET)
    @ResponseBody
    public Object active(User user) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != user){
            String message = validateActive(user);
            if(!StringUtils.isBlank(message)){
                result.setMessage(message);
            }
        }
        return gson.toJson(result);
    }

    /**
     * usernameUnique
     * @描述: 验证用户名是否唯一
     * @作者: gzs
     */

    @RequestMapping(value = "phone_unique")
    @ResponseBody
    public Object phoneUnique(String phone) {
        RequestResult result = new RequestResult();
        // 用户名为空，返回false。
        if (StringUtils.isBlank(phone)) {
            result.setSuccess(false);
            result.setMessage("手机号码为空。");
            return result;
        }
        // 用户名存在，返回false。
        if (userService.phoneExits(phone)) {
            result.setSuccess(false);
            result.setMessage("手机号码已经注册。");
            return result;
        }
        result.setSuccess(true);
        return gson.toJson(result);
    }

    private String validateActive(User user) {

        if (StringUtils.isBlank(user.getPhone())) {
            return "手机号不可为空";
        }
        User u = userService.queryUserByPhoneNumber(user.getPhone());
        if (u == null) {
            return "该用户不存在。";
        }
        if (u.getActivated()==0) {
            return "该用户未激活";
        }else if(u.getActivated() == 1){
            return "该用户已激活";
        }
        return null;
    }

    /**
     * @Description : 添加日志
     */
    private void addLog(String phone){
        Log log = new Log();
        log.setLogCategory(LogType.USER_REGISTER.getDescription());
        log.setLogTime(new Date());
        log.setTitle("用户"+phone+"注册");
        logService.addLog(log);
    }
}
