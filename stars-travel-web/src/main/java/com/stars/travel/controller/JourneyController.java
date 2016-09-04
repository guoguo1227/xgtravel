package com.stars.travel.controller;

import com.stars.common.utils.Page;
import com.stars.common.enums.CollectionTopType;
import com.stars.travel.model.base.JourneyWithBLOBs;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.JourneyDayVo;
import com.stars.travel.model.ext.JourneyItemVo;
import com.stars.travel.model.ext.JourneyVo;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.service.JourneyService;
import com.stars.travel.service.UserService;
import com.stars.travel.web.HttpSessionProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description : 行程管理控制器
 * Author : guo
 * Date : 2016/2/1 23:11
 */
@Controller
@RequestMapping("journey")
public class JourneyController extends BaseController{

    @Resource
    private JourneyService journeyService;

    @Autowired
    private UserService userService;

    /**
     * @Description : 查询用户行程列表
     * @param searchCondition
     * @return
     */
    @RequestMapping("page")
    @ResponseBody
    public Object queryJourneyListByUser(SearchCondition searchCondition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        if(null != searchCondition){
            String userPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(searchCondition.getToken())){
                userPhone = userService.queryPhoneByToken(searchCondition.getToken());
            }
            Page<JourneyVo> page = journeyService.queryJourneys(searchCondition,userPhone);
            if(null != page && !CollectionUtils.isEmpty(page.getPageData())){
                result.setSuccess(true);
                result.setData(page);
        }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 查询用户行程列表
     * @param searchCondition
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Object queryJourneyListApp(SearchCondition searchCondition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        if(null != searchCondition){
            String userPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(searchCondition.getToken())){
                userPhone = userService.queryPhoneByToken(searchCondition.getToken());
            }
            List<JourneyVo> list = journeyService.queryJourneyVoListApp(searchCondition,userPhone);
            if(!CollectionUtils.isEmpty(list)){
                result.setSuccess(true);
                result.setData(list);
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 用户行程列表搜索接口
     * @param searchCondition
     * @return
     */
    @RequestMapping("search")
    @ResponseBody
    public Object searchJourneyListApp(SearchCondition searchCondition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        if(null != searchCondition){
            String userPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(searchCondition.getToken())){
                userPhone = userService.queryPhoneByToken(searchCondition.getToken());
            }
            List<JourneyVo> list = journeyService.searchJourneyListApp(searchCondition,userPhone);
            if(!CollectionUtils.isEmpty(list)){
                result.setSuccess(true);
                result.setData(list);
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 查询我的收藏行程
     * @param searchCondition
     * @return
     */
    @RequestMapping("mycollection")
    @ResponseBody
    public Object queryMyCollectionListApp(SearchCondition searchCondition){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        if(null != searchCondition){
            String userPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(searchCondition.getToken())){
                userPhone = userService.queryPhoneByToken(searchCondition.getToken());
            }
            List<JourneyVo> list = journeyService.queryMyCollectList(searchCondition,userPhone);
            if(!CollectionUtils.isEmpty(list)){
                result.setSuccess(true);
                result.setData(list);
            }
        }
        return gson.toJson(result);
    }
    /**
     * @Description : 查询详情
     * @param id
     * @return
     */
    @RequestMapping("detail")
    @ResponseBody
    public Object queryJourneyDetailById(String token,Integer id){

        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != id){
            String userPhone = HttpSessionProvider.getSessionUserPhone();
            //获取APP的token
            if(!StringUtils.isBlank(token)){
                userPhone = userService.queryPhoneByToken(token);
            }
            SearchCondition searchCondition = new SearchCondition();
            searchCondition.setId(id);
            Page<JourneyVo> page = journeyService.queryJourneyVos(searchCondition,userPhone);
            if(null != page && !CollectionUtils.isEmpty(page.getPageData())){
                result.setSuccess(true);
                result.setData(page.getPageData().get(0));
            }
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 更新行程信息
     * @param journeyWithBLOBs
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public Object updateJourney(JourneyWithBLOBs journeyWithBLOBs){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }

        journeyWithBLOBs.setPhone(userPhone);
        journeyWithBLOBs = journeyService.updateJourney(journeyWithBLOBs);
        if(null != journeyWithBLOBs){
            result.setSuccess(true);
        }
        return gson.toJson(result);
    }

    /**
     * @Description : 删除用户行程
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @ResponseBody
    public Object deleteJourney(Integer id){

        RequestResult result = new RequestResult();

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }
        boolean flag  = false;
        if(null != id){
            flag = journeyService.deleteJourney(id);
        }
        result.setSuccess(flag);
        return gson.toJson(result);
    }

    /**
     * @Description : 查询行程总数
     * @return
     */
    @RequestMapping("count")
    @ResponseBody
    public String queryJourneyCount(){

        RequestResult result = new RequestResult();
        result.setSuccess(true);
        SearchCondition condition = new SearchCondition();
        int count = journeyService.queryJourneyCount(condition);

        result.setData(count);
        return gson.toJson(result);
    }

    /**
     * @Description : 添加行程
     * @param journeyVo
     * @return
     */
    @RequestMapping("add-journeyVo")
    @ResponseBody
    public Object addJourney(JourneyVo journeyVo){

        RequestResult result = new RequestResult();
        boolean flag  = false;

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(journeyVo.getToken())){
            userPhone = userService.queryPhoneByToken(journeyVo.getToken());
        }
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }
        if(null != journeyVo){
            journeyVo.setPhone(userPhone);
            journeyVo = journeyService.addJourney(journeyVo);
            if(null != journeyVo){
                flag = true;
                result.setData(journeyVo);
            }
        }
        result.setSuccess(flag);
        return gson.toJson(result);
    }

    /**
     * @Description : 添加行程每天的具体安排
     * @param journeyItemList
     * @return
     */
    @RequestMapping("add-journeyDay")
    @ResponseBody
    public Object addJourneyDay(@RequestBody List<JourneyItemVo> journeyItemList){

        RequestResult result = new RequestResult();
        boolean flag  = false;

        String userPhone = HttpSessionProvider.getSessionUserPhone();

        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }
        if(null != journeyItemList && journeyItemList.size()>0){
            JourneyDayVo journeyDayVo = new JourneyDayVo();
            journeyDayVo.setJourneyId(journeyItemList.get(0).getJourneyId());
            journeyDayVo.setCurrentDay(journeyItemList.get(0).getCurrentDay());
            journeyDayVo.setJourneyItemVoList(journeyItemList);
            journeyDayVo = journeyService.addJourneyDay(journeyDayVo);
            if(null != journeyDayVo){
                flag = true;
                result.setData(journeyDayVo);
            }
        }
        result.setSuccess(flag);
        return gson.toJson(result);
    }

    /**
     * @Description:添加行程
     * @return
     */
    @RequestMapping("add-journeyDatail")
    @ResponseBody
    public Object addJourneyDetail(String journeyVo,String token){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            userPhone = userService.queryPhoneByToken(token);
        }
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }
        if(StringUtils.isBlank(journeyVo)){
            result.setMessage("行程不可我空");
            return gson.toJson(result);
        }
        try{
            JourneyVo vo = gson.fromJson(journeyVo,JourneyVo.class);
            if(null != vo){
                vo.setPhone(userPhone);
                RequestResult requestResult = journeyService.addJourneyDetail(vo);
                result.setSuccess(requestResult.getSuccess());
                result.setMessage(requestResult.getMessage());
            }else{
                result.setMessage("行程不可为空");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description : 收藏行程分享
     * @return
     */
    @RequestMapping("collect")
    @ResponseBody
    public String collectionJourney(Integer id,String token){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            userPhone = userService.queryPhoneByToken(token);
        }
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }

        if(null != id){
            boolean isExist = journeyService.isExistCollectionByUserAndId(id,userPhone, CollectionTopType.COLLECTION.getCode());
            if(isExist){
                result.setMessage("已经收藏，不可重复收藏");
                return gson.toJson(result);
            }
            boolean success = journeyService.collectionJourney(id,userPhone);
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
     * @Description : 取消收藏行程分享
     * @return
     */
    @RequestMapping("uncollect")
    @ResponseBody
    public String uncollectionJourney(String token,Integer id){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            userPhone = userService.queryPhoneByToken(token);
        }
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }

        if(null != id){
            boolean success = journeyService.uncollectionJourney(id,userPhone);
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
     * @Description :顶赞
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
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }

        if(null != id){
            boolean isExist = journeyService.isExistCollectionByUserAndId(id,userPhone, CollectionTopType.TOP.getCode());
            if(isExist){
                result.setMessage("已经点赞，不可重复点赞");
                return gson.toJson(result);
            }
            RequestResult r = journeyService.topJourney(id,userPhone);
            result.setSuccess(r.getSuccess());
            result.setData(r.getData());
        }else{
            result.setMessage("id 不可为空");
        }

        return gson.toJson(result);
    }
    /**
     * @Description :取消顶赞
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
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }

        if(null != id){
            RequestResult r = journeyService.untopJourney(id,userPhone);
            result.setData(r.getData());
            result.setSuccess(r.getSuccess());
        }else{
            result.setMessage("id 不可为空");
        }

        return gson.toJson(result);
    }
    /**
     * @Description :取消顶赞
     * @param id
     * @return
     */
    @RequestMapping("deleteMy")
    @ResponseBody
    public String deleteMyJourney(Integer id,String token){

        RequestResult result = new RequestResult();
        result.setSuccess(false);

        String userPhone = HttpSessionProvider.getSessionUserPhone();
        //获取APP的token
        if(!StringUtils.isBlank(token)){
            userPhone = userService.queryPhoneByToken(token);
        }
        if(StringUtils.isBlank(userPhone)){
            result.setMessage("请先登录。");
            return gson.toJson(result);
        }

        if(null != id){
            RequestResult r = journeyService.untopJourney(id,userPhone);
            result.setData(r.getData());
            result.setSuccess(r.getSuccess());
        }else{
            result.setMessage("id 不可为空");
        }

        return gson.toJson(result);
    }

}
