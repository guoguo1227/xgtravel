package com.stars.travel.dao.ext.mapper;


import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.MicroblogVo;

import java.util.List;

/**
 * Description : 微游记扩展接口
 * Author : guo
 * Date : 2016/2/29 21:45
 */
public interface MicroblogVoMapper {

    /**
     * @Description : 按条件获取微游记列表
     * @param param
     * @return
     */
    public List<MicroblogVo> querySharedMicroblogList(AuctionSearchCondition param);

    /**
     * @Description : 获取符合条件的微游记数量
     * @param param
     * @return
     */
    public int countSharedMicroblogList(AuctionSearchCondition param);
}
