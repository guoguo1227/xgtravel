package com.stars.travel.controller;

import com.stars.travel.enums.LogType;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.service.LogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Description : 星轨日志接口
 * Author : guo
 * Date : 2016/3/13 11:42
 */
@Controller
@RequestMapping("log")
public class LogController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;


    /**
     * @Description : 根据类型查询日志数量
     * @param typeId
     * @return
     */
    @RequestMapping("count")
    @ResponseBody
    public Object queryLogCount(Integer typeId){

        RequestResult result = new RequestResult();
        result.setSuccess(true);
        if(null != typeId){
            String type = LogType.getDescription(typeId);
            if(!StringUtils.isBlank(type)){
                int count = logService.countQueryLog(type);
                result.setData(count);
            }
        }
        return gson.toJson(result);
    }

}
