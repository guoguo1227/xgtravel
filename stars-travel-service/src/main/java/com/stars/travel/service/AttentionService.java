package com.stars.travel.service;

import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.UserInfo;

import java.util.List;

/**
 * Description :关注服务接口
 * Author : guo
 * Date : 2016/7/19 0:20
 */
public interface AttentionService {

    /**
     * @escription: 添加关注
     * @param phone
     * @param currentPhone
     * @return
     */
    public boolean addAttention(String phone,String currentPhone);

    /**
     * @Description: 取消关注
     * @param phone
     * @param currentPhone
     * @return
     */
    public boolean unAttention(String phone,String currentPhone);

    /**
     * @Description: 是否关注
     * @param relatePhone
     * @param currentPhone
     * @return
     */
    public boolean ifAttention(String relatePhone,String currentPhone);

    /**
     * @Description:被关注次数
     * @param phone
     * @return
     */
    public Integer attentionCount(String phone);
    /**
     * @Description:查询关注列表
     * @param condition
     * @return
     */
    public List<UserInfo> queryAttetionApp(SearchCondition condition, String currentPhone);
}
