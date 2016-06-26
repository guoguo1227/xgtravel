package com.stars.travel.service.impl;

import com.stars.common.utils.Page;
import com.stars.common.enums.CollectionTopType;
import com.stars.common.enums.CommentTypeEnum;
import com.stars.common.utils.BeanUtilExt;
import com.stars.travel.dao.base.mapper.*;
import com.stars.travel.dao.ext.mapper.JourneyVoMapper;
import com.stars.travel.model.base.*;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.JourneyDayVo;
import com.stars.travel.model.ext.JourneyVo;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.JourneyService;
import com.stars.travel.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description : 用户行程服务层实现
 * Author : guo
 * Date : 2016/2/1 21:57
 */
@Service
public class JourneyServiceImpl implements JourneyService {

    private final static Logger logger = LoggerFactory.getLogger(JourneyServiceImpl.class);

    @Autowired
    private JourneyMapper journeyMapper;

    @Autowired
    private JourneyVoMapper journeyVoMapper;

    @Autowired
    private JourneyDayMapper journeyDayMapper;

    @Autowired
    private JourneyItemMapper journeyItemMapper;

    @Autowired
    private JourneyCollectionMapper journeyCollectionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Override
    public JourneyVo addJourney(JourneyVo journeyVo) {
        if(null != journeyVo && null != journeyVo.getPhone()){
            journeyVo.setCreatetime(new Date());
            int i = journeyMapper.insertSelective(journeyVo);
            if(i>0){
                return journeyVo;
            }
        }
        return null;
    }

    @Override
    public JourneyDayVo addJourneyDay(JourneyDayVo journeyDayVo) {
        if(null != journeyDayVo && null != journeyDayVo.getJourneyId()){
            JourneyDay journeyDay = new JourneyDay();
            journeyDay.setJourneyId(journeyDayVo.getJourneyId());
            journeyDay.setCurrentDay(journeyDayVo.getCurrentDay());
            journeyDay.setCreatetime(new Date());
            int i = journeyDayMapper.insertSelective(journeyDay);
            if(i>0){
                if(null != journeyDayVo.getJourneyItemVoList() && journeyDayVo.getJourneyItemVoList().size()>0){
                    for(JourneyItem item : journeyDayVo.getJourneyItemVoList()){
                        if(null != journeyDay.getJourneyId()){
                            item.setJourneyDayId(journeyDay.getId());
                            item.setCreatetime(new Date());
                            journeyItemMapper.insertSelective(item);
                        }
                    }
                }
            }
        }
        return journeyDayVo;
    }

    @Override
    public JourneyWithBLOBs updateJourney(JourneyWithBLOBs journey) {
        if(null != journey && null != journey.getPhone()){
            int i = journeyMapper.updateByPrimaryKeySelective(journey);
            if(i>0){
                return  journey;
            }
        }
        return null;
    }

    @Override
    public boolean deleteJourney(Integer id) {
        if(null != id){
            JourneyWithBLOBs journeyWithBLOBs = journeyMapper.selectByPrimaryKey(id);
            if(null != journeyWithBLOBs){
                journeyWithBLOBs.setIsEnable(false); //标记删除
                journeyWithBLOBs.setUpdatetime(new Date());
                //更新
                int i = journeyMapper.updateByPrimaryKeySelective(journeyWithBLOBs);
                if( i>0 ){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Page<JourneyVo> queryJourneys(AuctionSearchCondition condition, String currentPhone) {

        Page<JourneyVo> page = new Page<>();
        page.setPageSize(condition.getLimit());
        List<JourneyVo> journeyVos = new ArrayList<>();
        if(null != condition){
            JourneyCriteria criteria = new JourneyCriteria();
            JourneyCriteria.Criteria cri = criteria.createCriteria();
            //是否可用
            if(null != condition.getIfEnable()){
                cri.andIsEnableEqualTo(condition.getIfEnable());
            }
            //是否分享
            if(null != condition.getIfShared()){
                cri.andIsSharedEqualTo(condition.getIfShared());
            }
            //是否最新
            if(null != condition.getIfNew() && condition.getIfNew() == true){
                criteria.setOrderByClause(" createtime desc");
            }
            //是否最热
            if(null != condition.getIfHot() && condition.getIfHot() == true){
                criteria.setOrderByClause(" top desc ");
            }
            //用户手机
            if(!StringUtils.isBlank(condition.getPhone())){
                cri.andPhoneEqualTo(condition.getPhone());
            }

            int count = journeyMapper.countByExample(criteria);
            if(count >0){
                List<JourneyWithBLOBs> list = journeyMapper.selectByExampleWithBLOBs(criteria);
                if(null != list && list.size()>0){
                    for(JourneyWithBLOBs j: list){
                        JourneyVo vo = new JourneyVo();
                        try {
                            BeanUtilExt.copyProperties(vo,j);
                            //添加属性
                            addJourneyVoExtAttr(vo,currentPhone);
                            journeyVos.add(vo);
                        } catch (InvocationTargetException e) {
                            logger.info("拷贝行程id:"+j.getId()+"对象属性失败");
                        } catch (IllegalAccessException e) {
                            logger.info("拷贝行程id:"+j.getId()+"对象属性失败");
                        }
                    }
                }
                page.setTotalCount(count);
                page.setPageData(journeyVos);
            }
        }
        return page;
    }

    @Override
    public List<JourneyVo> queryJourneyListApp(AuctionSearchCondition condition, String currentPhone) {

        List<JourneyVo> list = null;
        if(null != condition){
            condition.setIfEnable(true);

            //最新，历史
            if(null != condition.getIfNew()){
                if(condition.getIfNew()){
                    if(null != condition.getfId()){
                        condition.setIdGreaterThan(condition.getfId());
                    }
                }else{
                    if(null != condition.getfId()){
                        condition.setIdLessThan(condition.getfId());
                    }
                }
            }
            //我的
            if(null != condition.getMy()){
                if(condition.getMy()){
                    condition.setPhone(currentPhone);
                }
            }
            //排序
            condition.setOrderByClause(" id desc ");
            list = journeyVoMapper.queryJourneyVoList(condition);
            if(null != list && list.size()>0 ){
                for(JourneyVo vo : list){
                    addJourneyVoExtAttr(vo,currentPhone);
                }
            }
        }
        return list ;
    }

    @Override
    public Page<JourneyVo> queryJourneyVos(AuctionSearchCondition condition,String currentPhone) {
        Page<JourneyVo> page  = new Page<>();
        List<JourneyVo> list = null;
        if(null != condition){
            condition.setIfEnable(true);
            condition.setOrderByClause(" createtime desc ");
            int count = journeyVoMapper.countJourneyVo(condition);
            if(count>0){
                list = journeyVoMapper.queryJourneyVoList(condition);
                if(null != list && list.size()>0 ){
                    for(JourneyVo vo : list){
                        addJourneyVoExtAttr(vo,currentPhone);
                    }
                    page.setTotalCount(count);
                    page.setPageData(list);
                }
            }
        }
        return page ;
    }

    @Override
    public Integer queryJourneyCount(AuctionSearchCondition condition) {
        int count = 0;
        if(null != condition){
            count = journeyVoMapper.countJourneyVo(condition);
        }
        return count;
    }

    @Override
    public boolean collectionJourney(Integer id, String userPhone) {
        boolean successs = false;
        Date nowDate = new Date();
        int j = 0;
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andPhoneEqualTo(userPhone).andRelateIdEqualTo(id).andTypeEqualTo(CollectionTopType.COLLECTION.getCode());
            List<JourneyCollection> list = journeyCollectionMapper.selectByExample(criteria);
            //记录存在
            if(!list.isEmpty()){
                JourneyCollection journeyCollection = list.get(0);
                journeyCollection.setUpdatetime(nowDate);
                journeyCollection.setIsEnable(true);
                j = journeyCollectionMapper.updateByPrimaryKeySelective(journeyCollection);
            }else{
                //记录不存在
                String phoneJourneyKey = id+"-"+userPhone+"-"+CollectionTopType.COLLECTION.getDescription();
                JourneyCollection collection = new JourneyCollection();
                collection.setCreatetime(nowDate);
                collection.setRelateId(id);
                collection.setPhone(userPhone);
                collection.setPhoneJourneyKey(phoneJourneyKey);
                collection.setIsEnable(true);
                collection.setType(CollectionTopType.COLLECTION.getCode()); //收藏类型
                j = journeyCollectionMapper.insertSelective(collection);
            }
            if(j>0){
                successs = true;
            }
        }
        return successs;
    }

    @Override
    public boolean uncollectionJourney(Integer id, String userPhone) {
        boolean success = false;
        Date nowDate = new Date();
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(userPhone).andTypeEqualTo(CollectionTopType.COLLECTION.getCode()).andIsEnableEqualTo(true);
            List<JourneyCollection> list = journeyCollectionMapper.selectByExample(criteria);

            if(!list.isEmpty()){
                JourneyCollection collection = list.get(0);
                collection.setIsEnable(false);
                collection.setUpdatetime(nowDate);
                int i = journeyCollectionMapper.updateByPrimaryKeySelective(collection);
                if(i>0){
                    logger.info("取消用户:"+userPhone+"收藏的行程id:"+id);
                    success = true;
                }
            }
        }
        return success;
    }

    @Override
    public boolean ifCollectionTop(Integer id, String userPhone,Integer type) {
        boolean success = false;
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(userPhone).andRelateIdEqualTo(id).andTypeEqualTo(type);
            int count = journeyCollectionMapper.countByExample(criteria);
            if(count >0){
                success = true;
            }
        }
        return success;
    }

    @Override
    public RequestResult topJourney(Integer id, String userPhone) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        Date nowDate = new Date();
        int j = 0;
        if(null != id && !StringUtils.isBlank(userPhone)){
            //记录存在
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andPhoneEqualTo(userPhone).andRelateIdEqualTo(id).andTypeEqualTo(CollectionTopType.TOP.getCode());
            List<JourneyCollection> list = journeyCollectionMapper.selectByExample(criteria);
            if(!list.isEmpty()){
                JourneyCollection journeyCollection = list.get(0);
                if(null != journeyCollection){
                    // journeyCollection
                    journeyCollection.setUpdatetime(nowDate);
                    journeyCollection.setIsEnable(true);
                    j = journeyCollectionMapper.updateByPrimaryKeySelective(journeyCollection);
                }
            }else{
                //记录不存在
                String phoneJourneyKey = id+"-"+userPhone+"-"+CollectionTopType.TOP.getDescription();
                JourneyCollection collection = new JourneyCollection();
                collection.setCreatetime(nowDate);
                collection.setRelateId(id);
                collection.setPhone(userPhone);
                collection.setPhoneJourneyKey(phoneJourneyKey);
                collection.setIsEnable(true);
                collection.setType(CollectionTopType.TOP.getCode()); //点赞类型
                j = journeyCollectionMapper.insertSelective(collection);
            }

            if(j>0){
                //行程顶，赞+1
                JourneyWithBLOBs journey = journeyMapper.selectByPrimaryKey(id);
                if(null != journey){
                    int count = journey.getTopCount()+1;
                    journey.setTopCount(count);
                    int i = journeyMapper.updateByPrimaryKey(journey);
                    if(i>0){
                        result.setSuccess(true);
                        result.setData(count);
                    }
                }
            }else{
                logger.info("用户phone:"+userPhone+"点赞行程id:"+id+"失败");
            }
        }
        return result;
    }

    @Override
    public RequestResult untopJourney(Integer id, String userPhone) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        Date nowDate = new Date();
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(userPhone).andTypeEqualTo(CollectionTopType.TOP.getCode()).andIsEnableEqualTo(true);
            List<JourneyCollection> list = journeyCollectionMapper.selectByExample(criteria);

            if(!list.isEmpty()){
                JourneyCollection collection = list.get(0);
                collection.setUpdatetime(nowDate);
                collection.setIsEnable(false);//标记删除
                int i = journeyCollectionMapper.updateByPrimaryKeySelective(collection);
                if(i>0){
                    //行程顶，赞+1
                    JourneyWithBLOBs journey = journeyMapper.selectByPrimaryKey(id);
                    if(null != journey){
                        int count = 0;
                        if(journey.getTopCount() -1>0){
                            count = journey.getTopCount() -1;
                        }
                        journey.setTopCount(count);
                        int j = journeyMapper.updateByPrimaryKey(journey);
                        if(j>0){
                            result.setSuccess(true);
                            result.setData(count);
                        }
                    }
                    logger.info("取消用户:"+userPhone+"顶赞的行程id:"+id);
                }
            }
        }
        return result;
    }

    @Override
    public List<JourneyVo> queryMyCollectList(AuctionSearchCondition condition, String currentPhone) {
        List<JourneyVo> list = null;
        List<Integer> ids = new ArrayList<>();
        if(null != condition){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.COLLECTION.getCode());
            criteria.setOrderByClause("id desc");
            //收藏列表
            List<JourneyCollection> collections = journeyCollectionMapper.selectByExample(criteria);
            if(!collections.isEmpty()){
                for(JourneyCollection j : collections){
                    ids.add(j.getRelateId());
                }
            }

            if(ids.size()>0){
                //最新，历史
                if(null != condition.getIfNew()){
                    if(condition.getIfNew()){
                        if(null != condition.getfId()){
                            condition.setIdGreaterThan(condition.getfId());
                        }
                    }else{
                        if(null != condition.getfId()){
                            condition.setIdLessThan(condition.getfId());
                        }
                    }
                }
                condition.setIdsIn(ids);
                //排序
                condition.setOrderByClause(" id desc ");
                list = journeyVoMapper.queryJourneyVoList(condition);
                if(!list.isEmpty()){
                    for(JourneyVo vo : list){
                        addJourneyVoExtAttr(vo,currentPhone);
                    }
                }
            }

        }
        return list ;
    }

    /**
     * @Description : 是否评论
     * @param id
     * @param userPhone
     * @return
     */
    public boolean ifComment(Integer id,String userPhone){
        boolean ifComment = false;
        CommentCriteria criteria = new CommentCriteria();
        criteria.createCriteria().andIsEnableEqualTo(true).andTypeEqualTo(CommentTypeEnum.JOURNEYTYPE.getDescription())
                .andRelateIdEqualTo(id).andPhoneEqualTo(userPhone);

        int count = commentMapper.countByExample(criteria);
        if(count >0){
            ifComment = true;
        }
        return ifComment;
    }

    /**
     * @Description : 查询行程的评论数
     * @param id
     * @return
     */
    public Integer queryCommentCount(Integer id){
        Integer count = 0;
        CommentCriteria criteria = new CommentCriteria();
        criteria.createCriteria().andIsEnableEqualTo(true).andTypeEqualTo(CommentTypeEnum.JOURNEYTYPE.getDescription())
                .andRelateIdEqualTo(id);

        count = commentMapper.countByExample(criteria);
        return count;
    }

    /**
     * @Description : 查询行程点赞数
     * @param id
     * @return
     */
    public Integer queryTopCount(Integer id){
        Integer count = 0;

        JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
        criteria.createCriteria().andIsEnableEqualTo(true).andTypeEqualTo(CollectionTopType.TOP.getCode()).andRelateIdEqualTo(id);

        count = journeyCollectionMapper.countByExample(criteria);
        return count;
    }

    /**
     * @Description：根据行程id，用户id查询收藏,点赞记录
     * @param id 行程id
     * @param userPhone 用户手机
     * @param type 类型,1==收藏,2==赞
     * @return
     */
    public boolean isExistCollectionByUserAndId(Integer id,String userPhone,Integer type){

        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andPhoneEqualTo(userPhone).andRelateIdEqualTo(id).andTypeEqualTo(type).andIsEnableEqualTo(true);
            int count = journeyCollectionMapper.countByExample(criteria);
            if(count>0){
                return true;
            }
        }
        return false;
    }

    /**
     * @Description :添加行程扩展属性
     * @param vo
     * @param currentPhone
     * @return
     */
    private JourneyVo addJourneyVoExtAttr(JourneyVo vo,String currentPhone){
        //是否收藏
        Boolean ifCollection = false;
        //是否评论
        Boolean ifComment = false;
        //是否顶赞
        Boolean ifTop = false;
        if(!StringUtils.isBlank(currentPhone)){
            ifCollection = ifCollectionTop(vo.getId(),currentPhone,CollectionTopType.COLLECTION.getCode());
            ifComment = ifComment(vo.getId(),currentPhone);
            ifTop = ifCollectionTop(vo.getId(),currentPhone,CollectionTopType.TOP.getCode());
        }

        vo.setIfCollection(ifCollection);
        vo.setIfComment(ifComment);
        vo.setIfTop(ifTop);
        //查询用户
        User user = userService.queryUserByPhoneNumber(vo.getPhone());
        if(null != user){
            UserInfo userInfo = new UserInfo();
            try {
                BeanUtilExt.copyProperties(userInfo,user);
                vo.setUserInfo(userInfo);
            } catch (InvocationTargetException e) {
                logger.info("拷贝用户属性失败。"+e.toString());
            } catch (IllegalAccessException e) {
                logger.info("拷贝用户属性失败。"+e.toString());
            }
        }
        //评论数
        vo.setCommentCount(queryCommentCount(vo.getId()).toString());
        return vo;
    }
}
