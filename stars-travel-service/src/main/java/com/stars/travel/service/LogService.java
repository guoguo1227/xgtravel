package com.stars.travel.service;

import com.stars.travel.model.base.Log;

import java.util.List;

/**
 * Description : 日志服务
 * Author : guo
 * Date : 2016/1/27 23:54
 */
public interface LogService {

    public boolean addLog(Log log);

    public List<Log> queryLogList(String type);

    /**
     * @Description : 查询符合条件的日志数量
     * @param type
     * @return
     */
    public int countQueryLog(String type);
}

