package com.stars.travel.service;


import com.lagou.platform.common.Page;
import com.stars.travel.model.base.Customization;
import com.stars.travel.model.condition.AuctionSearchCondition;

import java.util.List;

/**
 * Description : 行程定制服务
 * Author : guo
 * Date : 2016/3/25 21:45
 */
public interface CustomizationService {

    /**
     * @Description : 添加定制
     * @param customization
     * @return
     */
    public boolean addCustomization(Customization customization);

    /**
     * @Description : 删除定制
     * @param id
     * @return
     */
    public boolean deleteCustomization(Integer id);

    /**
     * @Description : 查询定制分页列表
     * @param condition
     * @return
     */
    public Page<Customization> queryCustomizationPage(AuctionSearchCondition condition);

    /**
     * @Description : 查询定制列表
     * @param condition
     * @return
     */
    public List<Customization> queryCustomizationListApp(AuctionSearchCondition condition);


    /**
     * @Description : 查询符合条件的定制数量
     * @param condition
     * @return
     */
    public int countCustomization(AuctionSearchCondition condition);
}
