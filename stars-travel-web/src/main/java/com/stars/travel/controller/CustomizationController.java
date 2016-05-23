package com.stars.travel.controller;

import com.stars.common.utils.Page;
import com.stars.travel.model.base.Customization;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.service.CustomizationService;
import com.stars.travel.service.UserService;
import com.stars.travel.web.HttpSessionProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Description : 定制rest层服务
 * Author : guo
 * Date : 2016/3/25 21:57
 */
@Controller
@RequestMapping("customization")
public class CustomizationController extends BaseController{
    private final static Logger logger = LoggerFactory.getLogger(CustomizationController.class);

    @Autowired
    private CustomizationService customizationService;

    @Autowired
    private UserService userService;
    /**
     * @Description :　查询定制列表
     * @param condition
     * @return
     */
    @RequestMapping("page")
    @ResponseBody
    public String queryCustomizationPage(AuctionSearchCondition condition){
        RequestResult result = new RequestResult();
        result.setSuccess(false);

        Page<Customization> page = customizationService.queryCustomizationPage(condition);
        if(null != page && page.getPageData().size()>0){
            result.setSuccess(true);
            result.setData(page);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 查询定制列表
     * @param condition
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public String queryCustomizationListApp(AuctionSearchCondition condition){
        RequestResult result = new RequestResult();
        result.setSuccess(false);

        List<Customization> list = customizationService.queryCustomizationListApp(condition);
        if(null != list && list.size()>0){
            result.setSuccess(true);
            result.setData(list);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 查询定制详情
     * @param condition
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public String queryCustomizationDetail(AuctionSearchCondition condition){
        RequestResult result = new RequestResult();
        result.setSuccess(true);

        if(null != condition && null != condition.getId()){
            Page<Customization> page = customizationService.queryCustomizationPage(condition);
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
     * @Description : 添加定制信息
     * @param customization
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public String addCustomization(Customization customization,String token){
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != customization){
            String userPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(token)){
                userPhone = userService.queryPhoneByToken(token);
            }
            if(StringUtils.isBlank(userPhone)){
                return gson.toJson(result);
            }
            customization.setPhone(userPhone);
            boolean success = customizationService.addCustomization(customization);
            result.setSuccess(success);
        }else{
            result.setMessage("添加失败，信息不可为空");
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 删除定制
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String deleteCustomization(Integer id){
        RequestResult result = new RequestResult();
        if(null != id){
            boolean success = customizationService.deleteCustomization(id);
            result.setSuccess(success);
        }
        return gson.toJson(result);
    }
}
