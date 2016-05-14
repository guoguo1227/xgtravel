package com.stars.travel.dao.ext.mapper;


import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.JourneyVo;

import java.util.List;

/**
 * Description : 行程分享
 * Author : guo
 * Date : 2016/3/5 23:55
 */
public interface JourneyVoMapper {

    /**
     * @Description : 查询行程分享列表
     * @param condition
     * @return
     */
    public List<JourneyVo> queryJourneyVoList(AuctionSearchCondition condition);

    /**
     * @Description : 查询行程分享数量
     * @param condition
     * @return
     */
    public int countJourneyVo(AuctionSearchCondition condition);
}
