package com.stars.travel.service;


import com.lagou.platform.common.Page;
import com.stars.travel.model.base.JourneyWithBLOBs;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.JourneyDayVo;
import com.stars.travel.model.ext.JourneyVo;
import com.stars.travel.model.ext.RequestResult;

import java.util.List;

/**
 * Description : 用户行程服务层接口
 * Author : guo
 * Date : 2016/2/1 21:46
 */
public interface JourneyService {

    /**
     * @Description : 添加行程分享
     * @param journeyVo
     * @return
     */
    public JourneyVo addJourney(JourneyVo journeyVo);

    /**
     * @Description : 添加行程分享 具体天数
     * @param journeyDayVo
     * @return
    */
    public JourneyDayVo addJourneyDay(JourneyDayVo journeyDayVo);

    public JourneyWithBLOBs updateJourney(JourneyWithBLOBs journey);

    public boolean deleteJourney(Integer id);

    /**
     * @Description : 获取行程分页列表
     * @param condition
     * @return
     */
    public Page<JourneyVo> queryJourneys(AuctionSearchCondition condition, String currentPhone);

    /**
     * @Descripition : 查询行程列表 -移动端
     * @param condition
     * @param currentPhone
     * @return
     */
    public List<JourneyVo> queryJourneyListApp(AuctionSearchCondition condition, String currentPhone);

    public Page<JourneyVo> queryJourneyVos(AuctionSearchCondition condition,String currentPhone);


    /**
     * @Description : 查询行程总数
     * @param condition
     * @return
     */
    public Integer queryJourneyCount(AuctionSearchCondition condition);

    /**
     * @Description : 收藏行程分享
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean collectionJourney(Integer id,String currentPhone);

    /**
     * @Description : 取消收藏行程分享
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean uncollectionJourney(Integer id,String currentPhone);

    /**
     * @Description : 验证该用户是否收藏该行程
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean ifCollection(Integer id,String currentPhone,Integer type);

    /**
     * @Description : 是否顶赞
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean ifTop(Integer id,String currentPhone);
    /**
     * @Description : 顶，赞行程分享
     * @param id
     * @return
     */
    public RequestResult topJourney(Integer id, String currentPhone);

    /**
     * @Description : 取消赞行程分享
     * @param id
     * @param currentPhone
     * @return
     */
    public RequestResult untopJourney(Integer id, String currentPhone);
    /**
     * @Description : 根据行程id,用户id查询是否收藏
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean isExistCollectionByUserAndId(Integer id,String currentPhone,Integer type);
}


