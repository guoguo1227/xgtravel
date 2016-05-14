package com.stars.travel.service.impl;

import com.stars.travel.dao.base.mapper.LogMapper;
import com.stars.travel.model.base.Log;
import com.stars.travel.model.base.LogCriteria;
import com.stars.travel.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description : 用户日志服务
 * Author : guo
 * Date : 2016/1/27 23:58
 */
@Service
public class LogSerivceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public boolean addLog(Log log) {
        if(null != log){
            int i = logMapper.insertSelective(log);
            if(i>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Log> queryLogList(String type) {
        if(null != type){
            LogCriteria criteria = new LogCriteria();
            criteria.createCriteria().andLogCategoryEqualTo(type).andIsEnableEqualTo(true);
            criteria.setOrderByClause(" log_time desc ");
            List<Log> logList = logMapper.selectByExample(criteria);
            return logList;
        }
        return null;
    }

    @Override
    public int countQueryLog(String type) {
        int count = 0;
        if(null != type){
            LogCriteria criteria = new LogCriteria();
            criteria.createCriteria().andLogCategoryEqualTo(type);
            count = logMapper.countByExample(criteria);
        }
        return count ;
    }
}
