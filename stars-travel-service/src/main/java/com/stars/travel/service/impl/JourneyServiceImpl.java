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

            cri.andIsEnableEqualTo(true); //可用
            int count = journeyMapper.countByExample(criteria);
            if(count >0){
                List<JourneyWithBLOBs> list = journeyMapper.selectByExampleWithBLOBs(criteria);
                if(null != list && list.size()>0){
                    for(JourneyWithBLOBs j: list){
                        //添加属性
                        JourneyVo journeyVo = addJourneyVoExtAttr(j,currentPhone);
                        journeyVos.add(journeyVo);
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
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollection collection = new JourneyCollection();
            collection.setCreatetime(new Date());
            collection.setRelateId(id);
            collection.setPhone(userPhone);
            collection.setType(CollectionTopType.COLLECTION.getCode()); //收藏类型
            int i = journeyCollectionMapper.insertSelective(collection);
            if(i>0){
                successs = true;
            }
        }
        return successs;
    }

    @Override
    public boolean uncollectionJourney(Integer id, String userPhone) {
        boolean success = false;
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(userPhone).andTypeEqualTo(CollectionTopType.COLLECTION.getCode());
            List<JourneyCollection> list = journeyCollectionMapper.selectByExample(criteria);

            if(null != list && list.size()>0){
                for(JourneyCollection collection : list){
                    int i = journeyCollectionMapper.deleteByPrimaryKey(collection.getId());
                    if(i>0){
                        logger.info("删除用户:"+userPhone+"收藏的行程id:"+id);
                        success = true;
                    }
                }
            }
        }
        return success;
    }

    @Override
    public boolean ifCollection(Integer id, String userPhone,Integer type) {
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
    public boolean ifTop(Integer id, String userPhone) {
        boolean success = false;
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(userPhone).andRelateIdEqualTo(id).andTypeEqualTo(CollectionTopType.TOP.getCode());
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
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollection collection = new JourneyCollection();
            collection.setCreatetime(new Date());
            collection.setRelateId(id);
            collection.setPhone(userPhone);
            collection.setType(CollectionTopType.TOP.getCode()); //收藏类型
            int j = journeyCollectionMapper.insertSelective(collection);
            if(j>0){
                //行程顶，赞+1
                JourneyWithBLOBs journey = journeyMapper.selectByPrimaryKey(id);
                if(null != journey){
                    int count = journey.getTop()+1;
                    journey.setTop(count);
                    int i = journeyMapper.updateByPrimaryKey(journey);
                    if(i>0){
                        result.setSuccess(true);
                        result.setData(count);
                    }
                }
            }

        }
        return result;
    }

    @Override
    public RequestResult untopJourney(Integer id, String userPhone) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(userPhone).andTypeEqualTo(CollectionTopType.TOP.getCode());
            List<JourneyCollection> list = journeyCollectionMapper.selectByExample(criteria);

            if(null != list && list.size()>0){
                for(JourneyCollection collection : list){
                    int i = journeyCollectionMapper.deleteByPrimaryKey(collection.getId());
                    if(i>0){

                        //行程顶，赞+1
                        JourneyWithBLOBs journey = journeyMapper.selectByPrimaryKey(id);
                        if(null != journey){
                            int count = 0;
                            if(journey.getTop() -1>0){
                                count = journey.getTop() -1;
                            }
                            journey.setTop(count);
                            int j = journeyMapper.updateByPrimaryKey(journey);
                            if(j>0){
                                result.setSuccess(true);
                                result.setData(count);
                            }
                        }
                        logger.info("删除用户:"+userPhone+"顶赞的行程id:"+id);
                    }
                }
            }
        }
        return result;
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
     * @Description：根据行程id，用户id查询收藏记录
     * @param id 行程id
     * @param userPhone 用户手机
     * @param type 类型,1==收藏,2==赞
     * @return
     */
    public boolean isExistCollectionByUserAndId(Integer id,String userPhone,Integer type){

        if(null != id && !StringUtils.isBlank(userPhone)){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andPhoneEqualTo(userPhone).andRelateIdEqualTo(id).andTypeEqualTo(type);
            List<JourneyCollection> list = journeyCollectionMapper.selectByExample(criteria);
            if(null != list && list.size()>0){
                return true;
            }
        }
        return false;
    }

    /**
     * @Description :添加行程扩展属性
     * @param j
     * @param currentPhone
     * @return
     */
    private JourneyVo addJourneyVoExtAttr(Journey j,String currentPhone){
        JourneyVo journeyVo = new JourneyVo();
        //是否收藏
        boolean ifCollection = false;
        //是否评论
        boolean isComment = false;
        //是否顶赞
        boolean isTop = false;
        if(!StringUtils.isBlank(currentPhone)){
            ifCollection = ifCollection(j.getId(),currentPhone,CollectionTopType.COLLECTION.getCode());
            isComment = ifComment(j.getId(),currentPhone);
            isTop = ifCollection(j.getId(),currentPhone,CollectionTopType.TOP.getCode());
        }

        //评论数
        journeyVo.setCommentCount(queryCommentCount(j.getId()).toString());
        journeyVo.setTopCount(queryTopCount(j.getId()).toString());
        try {
            BeanUtilExt.copyProperties(journeyVo,j);
        } catch (InvocationTargetException e) {
            logger.info("拷贝行程属性失败。");
        } catch (IllegalAccessException e) {
            logger.info("拷贝行程属性失败。");
        }
        //查询用户
        User user = userService.queryUserByPhoneNumber(j.getPhone());
        if(null != user){
            UserInfo userInfo = new UserInfo();
            try {
                BeanUtilExt.copyProperties(userInfo,user);
                journeyVo.setUserInfo(userInfo);
            } catch (InvocationTargetException e) {
                logger.info("拷贝用户属性失败。"+e.toString());
            } catch (IllegalAccessException e) {
                logger.info("拷贝用户属性失败。"+e.toString());
            }
        }
        journeyVo.setCollection(ifCollection);
        journeyVo.setComment(isComment);
        journeyVo.setHasTop(isTop);
        //评论数
        journeyVo.setCommentCount(queryCommentCount(journeyVo.getId()).toString());
        return journeyVo;
    }
}
