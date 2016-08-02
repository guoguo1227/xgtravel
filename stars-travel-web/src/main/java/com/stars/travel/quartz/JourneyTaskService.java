package com.stars.travel.quartz;

/**
 * Description : 行程分享定时任务接口
 * Author : guo
 * Date : 2016/7/31 15:05
 */
public interface JourneyTaskService {

    /**
     * @Description:刷新所有的行程分享索引
     */
    public void refreshAllIndex();
}
