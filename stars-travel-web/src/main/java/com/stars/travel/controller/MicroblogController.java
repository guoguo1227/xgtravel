package com.stars.travel.controller;
import com.lagou.platform.common.Page;
import com.stars.common.enums.CollectionTopType;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.MicroblogVo;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.service.MicroblogService;
import com.stars.travel.service.MicroblogVoService;
import com.stars.travel.service.UserService;
import com.stars.travel.web.HttpSessionProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * Description : 微游记控制层
 * Author : guo
 * Date : 2016/2/22 23:37
 */
@Controller
@RequestMapping("microblog")
public class MicroblogController extends BaseController{

    private Logger logger = Logger.getLogger(MicroblogController.class);

    @Autowired
    private MicroblogService microblogService;

    @Autowired
    private MicroblogVoService microblogVoService;

    @Autowired
    private UserService userService;

    /**
     * @Descirption : 查询最新，最热微游记
     * @param condition
     * @return
     */
    @RequestMapping("page")
    @ResponseBody
    public Object queryShareMicroblogList(AuctionSearchCondition condition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != condition){
            String userPhone = HttpSessionProvider.getSessionUserPhone();

            Page<MicroblogVo> page = microblogVoService.querySharedMicroblogVoPage(condition,userPhone);
            if(null != page && page.getPageData().size()>0){
                result.setSuccess(true);
                result.setData(page);
            }
        }
        return gson.toJson(result);
    }


    /**
     * @Description : 查询微游记列表 移动端
     * @param condition
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Object queryShareMicroblogListApp(AuctionSearchCondition condition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != condition){
            String userPhone = HttpSessionProvider.getSessionUserPhone();

            List<MicroblogVo> list = microblogVoService.querySharedMicroblogVoApp(condition,userPhone);
            if(null != list && list.size()>0){
                result.setSuccess(true);
                result.setData(list);
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 查询微游记详情
     * @param condition
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public Object queryMicroblogDetailList(AuctionSearchCondition condition,HttpServletRequest request){

        RequestResult result = new RequestResult();
        result.setSuccess(true);
        if(null != condition && null != condition.getId()){
            String userPhone = HttpSessionProvider.getSessionUserPhone();

            Page<MicroblogVo> page = microblogVoService.querySharedMicroblogVoPage(condition,userPhone);
            if(null != page && null != page.getPageData() && page.getPageData().size()>0){
                result.setData(page.getPageData().get(0));
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 查询微游记数量
     * @return
     */
    @RequestMapping("count")
    @ResponseBody
    public Object queryMicroblogCount(){

        RequestResult result = new RequestResult();
        result.setSuccess(true);
        AuctionSearchCondition searchCondition = new AuctionSearchCondition();
        int count = microblogVoService.countSharedMicroblogVoList(searchCondition);
        result.setData(count);
        return gson.toJson(result);
    }

    /**
     * @Description : 添加微游记
     * @param microblogVo
     * @return
     */
    @RequestMapping("add")
    @ResponseBody
    public String addMicroblog(MicroblogVo microblogVo){
        RequestResult result = new RequestResult();
        if(null != microblogVo){

            logger.info("token:"+microblogVo.getToken());
            logger.info("微游记:"+gson.toJson(microblogVo));
            String phone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(microblogVo.getToken())){
                phone = userService.queryPhoneByToken(microblogVo.getToken());
            }
            if(StringUtils.isBlank(phone)){
                result.setMessage("请先登录。");
                return gson.toJson(result);
            }

            if(!StringUtils.isBlank(phone)){
                microblogVo.setPhone(phone);
                boolean success = microblogVoService.addMicroblog(microblogVo);
                if(success){
                    result.setSuccess(true);
                }else{
                    result.setMessage("添加微游记失败!");
                }
            }else{
                result.setMessage("请先登录!");
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 删除微游记
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public String deleteMicroblog(Integer id){
        RequestResult result = new RequestResult();
        if(null != id){
            boolean success = microblogVoService.deleteMicroblog(id);
            if(success){
                result.setSuccess(true);
            }else{
                result.setMessage("删除微游记失败!");
            }
        }else{
            result.setMessage("id 不可为空");
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 收藏微游记
     * @param id
     * @return
     */
    @RequestMapping("collect")
    @ResponseBody
    public String collectMicroblog(Integer id,String token){
        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String phone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            phone = userService.queryPhoneByToken(token);
        }

        if(!StringUtils.isBlank(phone)){
            result.setMessage("收藏失败,请先登录。");
            return gson.toJson(result);
        }

        if(null != id){
            boolean isExist = microblogVoService.ifCollectionTop(id,phone, CollectionTopType.COLLECTION.getCode());
            if(isExist){
                result.setMessage("已经收藏，不可重复收藏");
                return gson.toJson(result);
            }

            boolean success = microblogVoService.collectMicroblog(id,phone);
            if(success){
                result.setSuccess(true);
            }else{
                result.setMessage("收藏失败");
            }
        }else{
            result.setMessage("id 不可为空");
        }

        return gson.toJson(result);
    }

    /**
     * @Description : 取消收藏微游记
     * @param id
     * @return
     */
    @RequestMapping("uncollect")
    @ResponseBody
    public String uncollectMicroblog(Integer id,String token){
        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String phone = HttpSessionProvider.getSessionUserPhone();

        //获取APP的token
        if(!StringUtils.isBlank(token)){
            phone = userService.queryPhoneByToken(token);
        }
        if(!StringUtils.isBlank(phone)){
            result.setMessage("取消收藏,请先登录。");
            return gson.toJson(result);
        }

        if(null != id){
            boolean success = microblogVoService.uncollectMicroblog(id,phone);
            if(success){
                result.setSuccess(true);
            }else{
                result.setMessage("取消收藏失败");
            }
        }else{
            result.setMessage("id 不可为空");
        }

        return gson.toJson(result);
    }

    /**
     * @Description :　顶赞
     * @param id
     * @return
     */
    @RequestMapping("top")
    @ResponseBody
    public String topJourney(Integer id,String token){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            userPhone = userService.queryPhoneByToken(token);
        }
        if(!StringUtils.isBlank(userPhone)){
            result.setMessage("顶赞失败,请先登录。");
            return gson.toJson(result);
        }
        if(null != id){
            boolean isExist = microblogVoService.ifCollectionTop(id,userPhone, CollectionTopType.TOP.getCode());
            if(isExist){
                result.setMessage("已经顶赞，不可重复顶赞");
                return gson.toJson(result);
            }
            RequestResult r = microblogVoService.topMicroblog(id,userPhone);
            result.setSuccess(r.getSuccess());
            result.setData(r.getData());
        }else{
            result.setMessage("id 不可为空");
        }

        return gson.toJson(result);
    }

    /**
     * @Description :　取消顶赞
     * @param id
     * @return
     */
    @RequestMapping("untop")
    @ResponseBody
    public String untopJourney(Integer id,String token){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            userPhone = userService.queryPhoneByToken(token);
        }
        if(!StringUtils.isBlank(userPhone)){
            result.setMessage("取消顶赞,请先登录。");
            return gson.toJson(result);
        }
        if(null != id){
            RequestResult r = microblogVoService.untopMicroblog(id,userPhone);
            result.setSuccess(r.getSuccess());
            result.setData(r.getData());
        }else{
            result.setMessage("id 不可为空");
        }

        return gson.toJson(result);
    }
}
