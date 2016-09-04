package com.stars.travel.controller;
import com.stars.common.utils.Page;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.base.MicroblogWithBLOBs;
import com.stars.travel.model.base.User;
import com.stars.travel.model.ext.JourneyVo;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.JourneyService;
import com.stars.travel.service.MicroblogService;
import com.stars.travel.service.UserService;
import com.stars.travel.web.HttpSessionProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description : 用户管理控制器
 * Author : guo
 * Date : 2015/12/22 23:08
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController{

    private Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;
    
    @Autowired
    private MicroblogService microblogService;
    
    @Resource
    private JourneyService  journeyService;

    /**
     * @Description : 用户分页列表
     * @param searchCondition
     * @return
     */
    @RequestMapping("user-page")
    @ResponseBody
    public Object queryUserList(SearchCondition searchCondition){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        String  userPhone = HttpSessionProvider.getSessionUserPhone();

        Page<UserInfo> page = userService.queryUserInfo(searchCondition,userPhone);
        if(null != page && !CollectionUtils.isEmpty(page.getPageData())){
            result.setSuccess(true);
            result.setData(page);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 用户,当地人列表,移动端
     * @param condition
     * @return
     */
    @RequestMapping("user-list")
    @ResponseBody
    public Object queryUserListAPP(SearchCondition condition){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        String  userPhone = HttpSessionProvider.getSessionUserPhone();

        //获取APP的token
        if(!StringUtils.isBlank(condition.getToken())){
            userPhone = userService.queryPhoneByToken(condition.getToken());
        }

        List<UserInfo> list = userService.queryUserListApp(condition,userPhone);
        if(null != list && list.size()>0){
            result.setSuccess(true);
            result.setData(list);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 用户,当地人列表,移动端
     * @param condition
     * @return
     */
    @RequestMapping("mycollection")
    @ResponseBody
    public Object queryMyCollection(SearchCondition condition){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        String  userPhone = HttpSessionProvider.getSessionUserPhone();

        //获取APP的token
        if(!StringUtils.isBlank(condition.getToken())){
            userPhone = userService.queryPhoneByToken(condition.getToken());
        }

        List<UserInfo> list = userService.queryMyCollection(condition,userPhone);
        if(null != list && list.size()>0){
            result.setSuccess(true);
            result.setData(list);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 查询用户详情信息
     * @param condition
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public Object queryUserDetailList(SearchCondition condition){
        RequestResult result = new RequestResult();
        if(null != condition && null != condition.getUserId()){
            result.setSuccess(true);
            String  userPhone = HttpSessionProvider.getSessionUserPhone();

            //获取APP的token
            if(!StringUtils.isBlank(condition.getToken())){
                userPhone = userService.queryPhoneByToken(condition.getToken());
            }
            Page<UserInfo> page = userService.queryUserInfo(condition,userPhone);
            if(null != page && null != page.getPageData() && page.getPageData().size()>0){
                result.setData(page.getPageData().get(0));
            }
        }else{
            result.setSuccess(false);
            result.setMessage("id 不可为空");
        }
        return gson.toJson(result);
    }

    /**
     * @Description: 查询用户个人主页
     * @param condition
     * @return
     */
    @RequestMapping("homePage")
    @ResponseBody
    public Object queryUserHomePage(SearchCondition condition){
        RequestResult result = new RequestResult();
        if(null != condition && null != condition.getUserId()){
            result.setSuccess(true);
            String  userPhone = HttpSessionProvider.getSessionUserPhone();

            //获取APP的token
            if(!StringUtils.isBlank(condition.getToken())){
                userPhone = userService.queryPhoneByToken(condition.getToken());
            }

            JSONObject object = userService.queryUserHomePage(condition,userPhone);
            result.setSuccess(true);
            result.setData(object);
        }else{
            result.setSuccess(false);
            result.setMessage("id 不可为空");
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 修改密码
     * @param token
     * @param password
     * @return
     */
    @RequestMapping(value = "updatePassword")
    @ResponseBody
    public Object updatePassword(String token,String password) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        String phone = "";
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            phone = userService.queryPhoneByToken(token);
        }
        if(!StringUtils.isBlank(phone)){
            if(!StringUtils.isBlank(password)){
                boolean flag  = userService.updatePassword(phone,password);
                result.setSuccess(flag);
            }else{
                result.setMessage("密码不可为空。");
            }
            logger.info("修改密码,用户phone为:"+phone);
        }else{
            result.setMessage("请先登录。");
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 更新个人信息
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "update" , method = RequestMethod.POST)
    @ResponseBody
    public Object update(UserInfo userInfo) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != userInfo){
            String phone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(userInfo.getToken())){
                phone = userService.queryPhoneByToken(userInfo.getToken());
            }
            if(StringUtils.isBlank(phone)){
                result.setMessage("请先登录。");
                return gson.toJson(result);
            }
            userInfo.setPhone(phone);
            boolean flag  = userService.modifyUserInfo(userInfo);
            result.setSuccess(flag);
            logger.info("更新用户信息,用户phone为:"+phone);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 修改个人信息,认证为当地人
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "updateUser" , method = RequestMethod.POST)
    @ResponseBody
    public Object updateUser(UserInfo userInfo) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != userInfo){
            String phone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(userInfo.getToken())){
                phone = userService.queryPhoneByToken(userInfo.getToken());
            }
            if(StringUtils.isBlank(phone)){
                result.setMessage("请先登录。");
                return gson.toJson(result);
            }
            boolean flag  = userService.modifyUserInfo(userInfo);
            result.setSuccess(flag);
            logger.info("更新用户信息,用户phone为:"+phone);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 删除用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public String deleteUser(String token,Integer userId,Boolean deleteFlag){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        String phone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            phone = userService.queryPhoneByToken(token);
        }
        if(StringUtils.isBlank(phone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }
        if(null != userId){
            boolean success = userService.deleteUserById(userId,deleteFlag);
            if(success){
                result.setSuccess(true);
            }else{
                result.setMessage("删除失败!");
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 恢复用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "restore")
    @ResponseBody
    public String restoreUser(String token ,Integer userId){
        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String phone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            phone = userService.queryPhoneByToken(token);
        }
        if(StringUtils.isBlank(phone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }
        if(null != userId){
            boolean success = userService.restoreUserById(userId);
            if(success){
                result.setSuccess(true);
            }else{
                result.setMessage("恢复失败!");
            }
        }
        return gson.toJson(result);
    }
    /**
     * @Description : 收藏该当地人
     * @param phone
     * @return
     */
    @RequestMapping("collect")
    @ResponseBody
    public String collectionUser(String phone,String token){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(!StringUtils.isBlank(phone)){
            String currentPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(token)){
                currentPhone = userService.queryPhoneByToken(token);
            }

            if(StringUtils.isBlank(currentPhone)){
                result.setMessage("请先登录");
                return gson.toJson(result);
            }
            boolean ifCollection = userService.ifCollectionUser(phone,currentPhone);
            if(ifCollection){
                result.setMessage("当地人已经收藏，不可重复收藏");
                return gson.toJson(result);
            }
            boolean success = userService.collectionUser(phone,currentPhone);
            result.setSuccess(success);
        }else{
            result.setMessage("当地人phone不可为空");
        }
        return gson.toJson(result);
    }
    /**
     * @Description : 取消收藏该当地人
     * @param phone
     * @return
     */
    @RequestMapping("uncollect")
    @ResponseBody
    public String uncollectionUser(String phone,String token){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(!StringUtils.isBlank(phone)){
            String  userPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(token)){
                userPhone = userService.queryPhoneByToken(token);
            }
            boolean success = userService.uncollectionUser(phone,userPhone);
            result.setSuccess(success);
        }else{
            result.setMessage("当地人phone不可为空");
        }
        return gson.toJson(result);
    }
    

    /**Description
     * @ : 他人用户,他人微游记，
     * @param condition
     * @return
     */
    @RequestMapping("homePageMicroblog")
    @ResponseBody
    public Object queryHomePageMicroblog(SearchCondition condition ,Integer userId){
        RequestResult result = new RequestResult();
        User  user =new User();
        //获取用户信息
        if(userId!=null && !StringUtils.isBlank(userId.toString())){
           user = userService.queryUserById(userId);
        }
        condition.setPhone(user.getPhone());
        //获取该用户的行程信息
        List<MicroblogWithBLOBs> list= microblogService.queryMicroblogByUser(user.getPhone());

       	Map data=new HashMap();
       	data.put("microblogs", list);
        if(user != null ){
            result.setSuccess(true);
            result.setData(data);
        }
        
        return gson.toJson(result);
    }
    /**
     * @Description : 他人用户,他人行程分享，
     * @param condition
     * @return
     */
    @RequestMapping("homePageJourney")
    @ResponseBody
    public Object homePageJourney(SearchCondition condition ,Integer userId){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        User  user =new User();
        //获取用户信息
        if(userId!=null && !StringUtils.isBlank(userId.toString())){
           user = userService.queryUserById(userId);
        }
        condition.setPhone(user.getPhone());
        //获取该用户的行程信息
        Page<JourneyVo> list= journeyService.queryJourneys(condition, user.getPhone());
       	Map data=new HashMap();
       	data.put("user", user);
       	data.put("journeys", list);
        if(user != null ){
            result.setSuccess(true);
            result.setData(data);
        }
        
        return gson.toJson(result);
    }
}
