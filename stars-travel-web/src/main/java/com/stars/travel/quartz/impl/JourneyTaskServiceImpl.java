package com.stars.travel.quartz.impl;

import com.stars.elasticsearch.JourneySearchService;
import com.stars.travel.dao.base.mapper.JourneyMapper;
import com.stars.travel.model.base.Journey;
import com.stars.travel.model.base.JourneyCriteria;
import com.stars.travel.quartz.JourneyTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Description : 行程分享的定时任务实现
 * Author : guo
 * Date : 2016/7/31 15:06
 */
@Service
public class JourneyTaskServiceImpl implements JourneyTaskService {

    private final static Logger logger = LoggerFactory.getLogger(JourneyTaskServiceImpl.class);

    @Autowired
    private JourneyMapper journeyMapper;

    @Autowired
    private JourneySearchService journeySearchService;

    @Override
    public void refreshAllIndex() {

        logger.info("开始刷新行程分享索引");

        JourneyCriteria criteria = new JourneyCriteria();
        criteria.createCriteria();
        List<Journey> list = journeyMapper.selectByExample(criteria);
        if(!CollectionUtils.isEmpty(list)){
            for(Journey j : list){
                journeySearchService.addJourneyIndex(j.getId());
            }
        }
        logger.info("完成刷新行程分享索引");
    }
}
