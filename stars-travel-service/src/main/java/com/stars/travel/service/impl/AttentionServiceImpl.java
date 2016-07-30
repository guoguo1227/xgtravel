package com.stars.travel.service.impl;

import com.stars.travel.dao.base.mapper.AttentionMapper;
import com.stars.travel.enums.AttentionEnum;
import com.stars.travel.model.base.Attention;
import com.stars.travel.model.base.AttentionCriteria;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.AttentionService;
import com.stars.travel.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description : 关注服务实现
 * Author : guo
 * Date : 2016/7/19 0:29
 */
@Service
public class AttentionServiceImpl implements AttentionService{

    private final static Logger logger = LoggerFactory.getLogger(AttentionServiceImpl.class);

    @Autowired
    private AttentionMapper attentionMapper; //关注服务

    @Autowired
    private UserService userService; //用户服务

    @Override
    public boolean addAttention(String phone,String currentPhone) {
        boolean success = false;
        Date nowDate = new Date();
        if(!StringUtils.isBlank(currentPhone) && !StringUtils.isBlank(phone)){
            //存在则更新
            AttentionCriteria criteria = new AttentionCriteria();
            criteria.createCriteria().andRelatePhoneEqualTo(phone).andCurrentPhoneEqualTo(currentPhone).andTypeEqualTo(AttentionEnum.USER.getCode());
            List<Attention> list = attentionMapper.selectByExample(criteria);
            if(!CollectionUtils.isEmpty(list)){
                for(Attention a : list){
                    a.setIsDelete(false);
                    a.setUpdatetime(nowDate);
                    int j = attentionMapper.updateByPrimaryKeySelective(a);
                    if(j>0){
                        success = true;
                        logger.info("更新当地人关注,添加用户phone:"+currentPhone+"关注用户:"+phone);
                    }
                }
            }else{
                String key = phone+"-"+currentPhone;
                Attention attention = new Attention();
                attention.setCreatetime(nowDate);
                attention.setCurrentPhone(currentPhone);
                attention.setRelatePhone(phone);
                attention.setType(AttentionEnum.USER.getCode()); //关注类型
                attention.setRelateUserKey(key);
                //不存在，则添加
                int i = attentionMapper.insertSelective(attention);
                if(i>0){
                    success = true;
                    logger.info("添加当地人关注,当前用户currentphone:"+attention.getCurrentPhone()+"关注用户phone:"+attention.getRelatePhone());
                }
            }
        }else{
            logger.info("关注用户手机不可为空");
        }
        return success;
    }

    @Override
    public boolean unAttention(String phone,String currentPhone) {
        boolean success = false;
        if(!StringUtils.isBlank(currentPhone) && !StringUtils.isBlank(phone)){
            AttentionCriteria criteria = new AttentionCriteria();
            criteria.createCriteria().andCurrentPhoneEqualTo(currentPhone)
                    .andRelatePhoneEqualTo(phone).andTypeEqualTo(AttentionEnum.USER.getCode()).andIsDeleteEqualTo(false);
            List<Attention> attentionList = attentionMapper.selectByExample(criteria);
            if(!CollectionUtils.isEmpty(attentionList)){
                for(Attention attention:attentionList){
                    attention.setIsDelete(true);//标记删除
                    int i = attentionMapper.updateByPrimaryKeySelective(attention);
                    if(i>0){
                        success = true;
                        logger.info("成功取消用户phone:"+currentPhone+"关注用户phone:"+phone);
                    }
                }
            }
        }else{
            logger.info("关注当地人phone不可为空");
        }
        return success;
    }

    @Override
    public boolean ifAttention(String relatePhone, String currentPhone) {
        boolean ifAttention = false;
        if(!StringUtils.isBlank(relatePhone) && !StringUtils.isBlank(currentPhone)){
            AttentionCriteria criteria = new AttentionCriteria();
            criteria.createCriteria().andRelatePhoneEqualTo(relatePhone).andCurrentPhoneEqualTo(currentPhone)
                    .andTypeEqualTo(AttentionEnum.USER.getCode()).andIsDeleteEqualTo(false);
            List<Attention> list = attentionMapper.selectByExample(criteria);
            if(!CollectionUtils.isEmpty(list)){
                ifAttention = true;
            }
        }else{
            logger.info("关注用户手机不可为空");
        }
        return ifAttention;
    }

    @Override
    public Integer attentionCount(String phone) {
        Integer count = 0;
        AttentionCriteria criteria = new AttentionCriteria();
        criteria.createCriteria().andRelatePhoneEqualTo(phone)
                .andTypeEqualTo(AttentionEnum.USER.getCode()).andIsDeleteEqualTo(false);
        count = attentionMapper.countByExample(criteria);

        return count;
    }

    @Override
    public List<UserInfo> queryAttetionApp(SearchCondition condition, String currentPhone) {

        List<UserInfo> userInfoList = null;
        List<Attention> list = null;
        AttentionCriteria criteria = new AttentionCriteria();
        AttentionCriteria.Criteria attentionCriteria = criteria.createCriteria();
        if(null != condition && !StringUtils.isBlank(currentPhone)){
            //我的关注
            if(null != condition.getMy() && condition.getMy()){
                attentionCriteria.andCurrentPhoneEqualTo(currentPhone);
            }

            //是否查询最新，历史
            if(null != condition.getIfNew()){
                if(condition.getIfNew()){
                    if(null != condition.getfId()){
                        attentionCriteria.andIdGreaterThan(condition.getfId());//大于该id
                    }
                }else{
                    if(null != condition.getfId()){
                        attentionCriteria.andIdLessThan(condition.getfId());//小于该id
                    }
                }
            }
            //查询数量
            if(null != condition.getLimit() && null != condition.getOffset()){
                criteria.setLimitStart(condition.getOffset());
                criteria.setLimitEnd(condition.getLimit());
            }
            //关注我的
            if(null != condition.getAttentionMe()){
                attentionCriteria.andRelatePhoneEqualTo(currentPhone);
            }
            attentionCriteria.andIsDeleteEqualTo(false); //查询未删除的
            attentionCriteria.andTypeEqualTo(AttentionEnum.USER.getCode());//关注类型
            list = attentionMapper.selectByExample(criteria);
            if(!CollectionUtils.isEmpty(list)){
                userInfoList = new ArrayList<>();
                for(Attention a : list){
                    //查询用户对象
                    UserInfo userInfo = queryUserInfo(a);
                    userInfoList.add(userInfo);
                }
            }
        }else{
            logger.info("请先登录");
        }
        return userInfoList;
    }

    /**
     * @Description:查询用户对象
     * @param a
     * @return
     */
    public UserInfo queryUserInfo(Attention a){
        UserInfo userInfo = null;
        if(null != a){
            userInfo =userService.queryUserInfoByPhone(a.getRelatePhone(),a.getCurrentPhone());
        }
        return userInfo;
    }
}
