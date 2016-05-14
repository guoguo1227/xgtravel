package com.stars.travel.controller;
import com.lagou.platform.common.Page;
import com.stars.travel.model.base.User;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.UserService;
import com.stars.travel.web.HttpSessionProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import java.util.List;

import static com.stars.common.utils.TravelsUtils.USER_KEY;

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

    @RequestMapping("/*")
    public String index(Model model) {
        return "user/index";
    }

    @RequestMapping("query")
    @ResponseBody
    public Object querty(){
        User user = userService.queryUserById(1);
        return gson.toJson(user);
    }

    /**
     * @Description : 用户分页列表
     * @param searchCondition
     * @return
     */
    @RequestMapping("user-page")
    @ResponseBody
    public Object queryUserList(AuctionSearchCondition searchCondition){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        String  userPhone = HttpSessionProvider.getSessionUserPhone();

        Page<UserInfo> page = userService.queryUserInfo(searchCondition,userPhone);
        if(null != page && page.getPageData().size()>0){
            result.setSuccess(true);
            result.setData(page);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 用户,当地人列表,移动端
     * @param searchCondition
     * @param request
     * @return
     */
    @RequestMapping("user-list")
    @ResponseBody
    public Object queryUserListAPP(AuctionSearchCondition searchCondition, HttpServletRequest request){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        String  userPhone = HttpSessionProvider.getSessionUserPhone();

        List<UserInfo> list = userService.queryUserListApp(searchCondition,userPhone);
        if(null != list && list.size()>0){
            result.setSuccess(true);
            result.setData(list);
        }
        return gson.toJson(result);
    }


    /**
     * @Description : 查询用户详情信息
     * @param searchCondition
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public Object queryUserDetailList(AuctionSearchCondition searchCondition,HttpServletRequest request){
        RequestResult result = new RequestResult();
        if(null != searchCondition && null != searchCondition.getUserId()){
            result.setSuccess(true);
            String  userPhone = HttpSessionProvider.getSessionUserPhone();

            Page<UserInfo> page = userService.queryUserInfo(searchCondition,userPhone);
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
     * @Description : 修改密码
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "updatePassword" , method = RequestMethod.POST)
    @ResponseBody
    public Object updatePassword(String phone,String password) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(!StringUtils.isBlank(phone)){
            if(!StringUtils.isBlank(password)){
                boolean flag  = userService.updatePassword(phone,password);
                result.setSuccess(flag);
            }else{
                result.setMessage("密码不可为空。");
            }
            logger.info("修改密码,用户phone为:"+phone);
        }else{
            result.setMessage("手机号不可为空。");
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 更新个人信息
     * @param user
     * @return
     */
    @RequestMapping(value = "update" , method = RequestMethod.POST)
    @ResponseBody
    public Object update(User user) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != user){
            boolean flag  = userService.modifyUser(user);
            result.setSuccess(flag);
            logger.info("更新用户信息,用户phone为:"+user.getPhone());
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
    public String deleteUser(Integer userId){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != userId){
            boolean success = userService.deleteUserById(userId);
            if(success){
                result.setSuccess(true);
            }else{
                result.setMessage("删除失败!");
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 收藏该当地人
     * @param phone
     * @return
     */
    @RequestMapping("collection")
    @ResponseBody
    public String collectionUser(String phone){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(!StringUtils.isBlank(phone)){
            String  currentPhone = HttpSessionProvider.getSessionUserPhone();
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
            result.setMessage("当地人id不可为空");
        }
        return gson.toJson(result);
    }
    /**
     * @Description : 取消收藏该当地人
     * @param phone
     * @return
     */
    @RequestMapping("uncollection")
    @ResponseBody
    public String uncollectionUser(String phone){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(!StringUtils.isBlank(phone)){
            String  userPhone = HttpSessionProvider.getSessionUserPhone();

            boolean success = userService.uncollectionUser(phone,userPhone);
            result.setSuccess(success);
        }else{
            result.setMessage("当地人id不可为空");
        }
        return gson.toJson(result);
    }
}
