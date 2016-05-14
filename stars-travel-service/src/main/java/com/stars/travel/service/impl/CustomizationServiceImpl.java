package com.stars.travel.service.impl;

import com.lagou.platform.common.Page;
import com.stars.travel.dao.base.mapper.CustomizationMapper;
import com.stars.travel.model.base.Customization;
import com.stars.travel.model.base.CustomizationCriteria;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.service.CustomizationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Description : 定制service服务
 * Author : guo
 * Date : 2016/3/25 21:51
 */
@Service
public class CustomizationServiceImpl implements CustomizationService {

    private static long orderNum = 0l;
    private static String date ;

    @Autowired
    private CustomizationMapper customizationMapper;

    @Override
    public boolean addCustomization(Customization customization) {
        if(null != customization){
            String orderNo = getOrderNo();
            customization.setOrderNumber(orderNo); //生成唯一订单号
            customization.setCreatetime(new Date());
            int i = customizationMapper.insertSelective(customization);
            if(i>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteCustomization(Integer id) {
        Date nowDate = new Date();
        if(null != id){
            Customization customization = customizationMapper.selectByPrimaryKey(id);
            if(null != customization){
                customization.setIsEnable(false); //标记删除
                customization.setUpdatetime(nowDate);
                int i = customizationMapper.updateByPrimaryKeySelective(customization);
                if(i>0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Page<Customization> queryCustomizationPage(AuctionSearchCondition condition) {
        Page<Customization> page = null;
        if(null != condition){
            page = new Page<>();
            page.setPageSize(condition.getLimit());
            CustomizationCriteria criteria = new CustomizationCriteria();
            CustomizationCriteria.Criteria cri = criteria.createCriteria();
            //指定id
            if(null != condition.getId()){
                cri.andIdEqualTo(condition.getId());
            }
            //用户手机
            if(!StringUtils.isBlank(condition.getPhone())){
                cri.andPhoneEqualTo(condition.getPhone());
            }

            criteria.setOrderByClause("  createtime desc ");
            cri.andIsEnableEqualTo(true); //可用
            int count = customizationMapper.countByExample(criteria);
            if(count>0){
                criteria.setLimitStart(condition.getOffset());
                criteria.setLimitEnd(condition.getLimit());
                List<Customization> list = customizationMapper.selectByExample(criteria);
                page.setPageData(list);
                page.setTotalCount(count);
            }
        }
        return page;
    }

    @Override
    public List<Customization> queryCustomizationListApp(AuctionSearchCondition condition) {
        List<Customization> list = null;
        if(null != condition){
            CustomizationCriteria criteria = new CustomizationCriteria();
            CustomizationCriteria.Criteria cri = criteria.createCriteria();

            //用户手机
            if(!StringUtils.isBlank(condition.getPhone())){
                cri.andPhoneEqualTo(condition.getPhone());
            }
            //是否查询最新，历史
            if(null != condition.getIfNew()){
                if(condition.getIfNew()){
                    if(null != condition.getfId()){
                        cri.andIdGreaterThan(condition.getfId());//大于该id
                    }
                }else{
                    if(null != condition.getfId()){
                        cri.andIdLessThan(condition.getfId());//小于该id
                    }
                }
            }
            cri.andIsEnableEqualTo(true); //可用

            criteria.setOrderByClause("  createtime desc ");
            criteria.setLimitStart(condition.getOffset());
            criteria.setLimitEnd(condition.getLimit());
            list = customizationMapper.selectByExample(criteria);
        }
        return list;
    }

    @Override
    public int countCustomization(AuctionSearchCondition condition) {
        int count = 0;
        if(null != condition){
            CustomizationCriteria criteria = new CustomizationCriteria();
            CustomizationCriteria.Criteria cri= criteria.createCriteria();
            //用户手机
            if(!StringUtils.isBlank(condition.getPhone())){
                cri.andPhoneEqualTo(condition.getPhone());
            }
            count = customizationMapper.countByExample(criteria);
        }
        return count;
    }
    /**
     * 生成订单编号
     * @return
     */
    public static synchronized String getOrderNo() {
        String str = new SimpleDateFormat("yyMMddHHmm").format(new Date());
        if(date==null||!date.equals(str)){
            date = str;
            orderNum  = 0l;
        }
        orderNum ++;
        long orderNo = Long.parseLong((date)) * 10000;
        orderNo += orderNum;;
        return orderNo+"";
    }
}
