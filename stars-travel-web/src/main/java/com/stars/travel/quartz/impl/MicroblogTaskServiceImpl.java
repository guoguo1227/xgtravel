package com.stars.travel.quartz.impl;

import com.stars.elasticsearch.MicroblogSearchService;
import com.stars.travel.dao.base.mapper.MicroblogMapper;
import com.stars.travel.model.base.Microblog;
import com.stars.travel.model.base.MicroblogCriteria;
import com.stars.travel.quartz.MicroblogTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Description :
 * Author : guo
 * Date : 2016/8/3 23:38
 */
@Service
public class MicroblogTaskServiceImpl implements MicroblogTaskService{

    private final static Logger logger = LoggerFactory.getLogger(MicroblogTaskServiceImpl.class);

    @Autowired
    private MicroblogMapper microblogMapper;

    @Autowired
    private MicroblogSearchService microblogSearchService;

    @Override
    public void refreshAllIndex() {
        logger.info("开始刷新微游记索引");

        MicroblogCriteria criteria = new MicroblogCriteria();
        criteria.createCriteria();
        List<Microblog> list = microblogMapper.selectByExample(criteria);
        if(!CollectionUtils.isEmpty(list)){
            for(Microblog microblog : list){
                microblogSearchService.addMicroblogIndex(microblog.getId());
            }
        }
        logger.info("开始刷新微游记索引");

    }
}
