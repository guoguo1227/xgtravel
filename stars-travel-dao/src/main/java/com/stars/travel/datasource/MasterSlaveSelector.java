package com.stars.travel.datasource;

import javax.sql.DataSource;

/**
 * 主从选择
 * @Author Joran
 * @Date 2014年8月4日 下午9:15:57 
 */
public interface MasterSlaveSelector {

    public abstract DataSource get();

    /**
     * 设置主库
     */
    public abstract void master();

    /**
     * 设置从库
     */
    public abstract void slave();

    /**
     * 对数据库资源的监控，每10分钟检测一次
     */
    public abstract void monitor();

}