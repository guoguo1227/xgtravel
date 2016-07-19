package com.stars.travel.controller;

import com.stars.common.utils.Page;
import com.stars.travel.model.base.Attention;
import com.stars.travel.model.base.Customization;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.CommentVo;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.AttentionService;
import com.stars.travel.service.UserService;
import com.stars.travel.web.HttpSessionProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description : 当地人关注rest层接口
 * Author : guo
 * Date : 2016/7/19 22:36
 */
@Controller
@RequestMapping("attention")
public class AttentionController extends BaseController{

    private final static Logger logger = LoggerFactory.getLogger(AttentionController.class);

    @Autowired
    private AttentionService attentionService; //关注服务

    @Autowired
    private UserService userService; //用户服务

    /**
     * @Description: 我的关注列表
     * @param condition
     * @return
     */
    @RequestMapping("my-attention")
    @ResponseBody
    public String queryMyAttentionList(SearchCondition condition){
        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String currentPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(null != condition && !StringUtils.isBlank(condition.getToken())){
            currentPhone = userService.queryPhoneByToken(condition.getToken());
        }
        if(StringUtils.isBlank(currentPhone)){
            result.setMessage("请先登录");
            return gson.toJson(result);
        }
        //设置我的
        condition.setMy(true);
        List<UserInfo> list = attentionService.queryAttetionApp(condition,currentPhone);
        if(!CollectionUtils.isEmpty(list)){
            result.setSuccess(true);
            result.setData(list);
        }
        return gson.toJson(result);
    }

    /**
     * @Description: 关注我的列表
     * @param condition
     * @return
     */
    @RequestMapping("attention-me")
    @ResponseBody
    public String queryAttentionMeList(SearchCondition condition){
        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String currentPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(null != condition && !StringUtils.isBlank(condition.getToken())){
            currentPhone = userService.queryPhoneByToken(condition.getToken());
        }
        if(StringUtils.isBlank(currentPhone)){
            result.setMessage("请先登录");
            return gson.toJson(result);
        }
        //设置我的
        condition.setAttentionMe(true);
        List<UserInfo> list = attentionService.queryAttetionApp(condition,currentPhone);
        if(!CollectionUtils.isEmpty(list)){
            result.setSuccess(true);
            result.setData(list);
        }
        return gson.toJson(result);
    }
    /**
     * @Description : 关注当地人
     * @param phone
     * @return
     */
    @RequestMapping("attention")
    @ResponseBody
    public String collectionUser(String phone,String token){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(!StringUtils.isBlank(phone)){
            String  currentPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(token)){
                currentPhone = userService.queryPhoneByToken(token);
            }
            if(StringUtils.isBlank(currentPhone)){
                result.setMessage("请先登录");
                return gson.toJson(result);
            }
            boolean ifAttetion = attentionService.ifAttention(phone,currentPhone);
            if(ifAttetion){
                result.setMessage("当地人已经关注，不可重复关注");
                return gson.toJson(result);
            }
            boolean success = attentionService.addAttention(phone,currentPhone);
            result.setSuccess(success);
        }else{
            result.setMessage("当地人id不可为空");
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 取消关注该当地人
     * @param phone
     * @return
     */
    @RequestMapping("unattention")
    @ResponseBody
    public String unAttentionUser(String phone,String token){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(!StringUtils.isBlank(phone)){
            String currentPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(token)){
                currentPhone = userService.queryPhoneByToken(token);
            }
            boolean success = attentionService.unAttention(phone,currentPhone);
            result.setSuccess(success);
        }else{
            result.setMessage("关注phone不可为空");
        }
        return gson.toJson(result);
    }
}
