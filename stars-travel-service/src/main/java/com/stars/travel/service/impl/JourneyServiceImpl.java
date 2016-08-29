package com.stars.travel.service.impl;

import com.stars.common.utils.Page;
import com.stars.common.enums.CollectionTopType;
import com.stars.common.enums.CommentTypeEnum;
import com.stars.common.utils.BeanUtilExt;
import com.stars.elasticsearch.JourneySearchService;
import com.stars.travel.dao.base.mapper.*;
import com.stars.travel.dao.ext.mapper.JourneyVoMapper;
import com.stars.travel.model.base.*;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.*;
import com.stars.travel.service.JourneyService;
import com.stars.travel.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private JourneySearchService journeySearchService;

    @Override
    public RequestResult addJourneyDetail(JourneyVo journeyVo) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        Date nowDate = new Date();
        if(null != journeyVo && !CollectionUtils.isEmpty(journeyVo.getJourneyDayVoList())){
            //添加行程基本属性
            journeyVo.setTotalday(journeyVo.getJourneyDayVoList().size());
            Journey journey = addJourney(journeyVo);
            if(null != journey && null != journey.getId()){
                //添加每天属性
                for(JourneyDayVo journeyDayVo: journeyVo.getJourneyDayVoList()){
                    JourneyDay journeyDay = new JourneyDay();
                    journeyDay.setJourneyId(journey.getId()); //行程id
                    journeyDay.setCurrentDay(journeyDayVo.getCurrentDay());
                    journeyDay.setCreatetime(nowDate);
                    int i = journeyDayMapper.insertSelective(journeyDay);
                    //添加每天事件
                    if(i>0){
                        if(!CollectionUtils.isEmpty(journeyDayVo.getJourneyItemVoList())){
                            for(JourneyItem item : journeyDayVo.getJourneyItemVoList()){
                                item.setJourneyDayId(journeyDay.getId()); //每天行程id
                                item.setCreatetime(nowDate);
                                journeyItemMapper.insertSelective(item);
                            }
                        }
                    }
                }
                //添加索引
                journeySearchService.addJourneyIndex(journey.getId());
                result.setSuccess(true);
            }
        }else{
            result.setMessage("行程分享不可为空");
        }
        return result;
    }

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
                //添加索引
                journeySearchService.addJourneyIndex(journeyDayVo.getJourneyId());
            }
        }
        return journeyDayVo;
    }

    @Override
    public JourneyWithBLOBs updateJourney(JourneyWithBLOBs journey) {
        if(null != journey && null != journey.getPhone()){
            int i = journeyMapper.updateByPrimaryKeySelective(journey);
            if(i>0){
                //更新索引
                journeySearchService.addJourneyIndex(journey.getId());
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
                    //更新索引
                    journeySearchService.addJourneyIndex(id);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Page<JourneyVo> queryJourneys(SearchCondition condition, String currentPhone) {

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
    public List<JourneyVo> queryJourneyListApp(SearchCondition condition, String currentPhone) {

        List<JourneyVo> list = null;
        if(null != condition){
            condition.setIfEnable(true);

            //最新，历史
            if(null != condition.getIfNew()){
                if(condition.getIfNew()){
                    if(null != condition.getfId()){
                        condition.setIdGreaterThan(condition.getfId());
                    }
                    //大于点赞数
                    if(null != condition.getTopCount()){
                        condition.setTopGreaterThan(condition.getTopCount());
                    }
                }else{
                    if(null != condition.getfId()){
                        condition.setIdLessThan(condition.getfId());
                    }
                    //小于点赞数
                    if(null != condition.getTopCount()){
                        condition.setTopLessTan(condition.getTopCount());
                    }
                }
            }
            //我的
            if(null != condition.getMy()){
                if(condition.getMy()){
                    condition.setPhone(currentPhone);
                }
            }

            //用户id
            if(null != condition.getUserId()){
                User user = userService.queryUserById(condition.getUserId());
                if(null != user && !StringUtils.isBlank(user.getPhone())){
                    condition.setPhone(user.getPhone());
                }
            }
            //排序
            if(!StringUtils.isBlank(condition.getOrderByClause())){
                condition.setOrderByClause(" journey.id desc ");
            }
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
    public List<JourneyVo> queryJourneyVoListApp(SearchCondition condition, String currentPhone) {
        List<JourneyVo> list = new ArrayList<>();
        JourneyCriteria criteria = new JourneyCriteria();
        JourneyCriteria.Criteria cri = criteria.createCriteria();
        if(null != condition){
            cri.andIsEnableEqualTo(true);
            //最新，历史
            if(null != condition.getIfNew()){
                if(condition.getIfNew()){
                    if(null != condition.getfId()){
                        cri.andIdGreaterThan(condition.getfId());
                    }
                    //大于点赞数
                    if(null != condition.getTopCount()){
                        cri.andTopCountGreaterThan(condition.getTopCount());
                    }
                }else{
                    if(null != condition.getfId()){
                        cri.andIdLessThan(condition.getfId());
                    }
                    //小于点赞数
                    if(null != condition.getTopCount()){
                        cri.andTopCountLessThan(condition.getTopCount());
                    }
                }
            }
            //我的
            if(null != condition.getMy()){
                if(condition.getMy()){
                    cri.andPhoneEqualTo(currentPhone);
                }
            }

            //用户id
            if(null != condition.getUserId()){
                User user = userService.queryUserById(condition.getUserId());
                if(null != user && !StringUtils.isBlank(user.getPhone())){
                    cri.andPhoneEqualTo(user.getPhone());
                }
            }
            //排序
            if(!StringUtils.isBlank(condition.getOrderByClause())){
                criteria.setOrderByClause(" id desc ");
            }
            if(null != condition.getId()){
                cri.andIdEqualTo(condition.getId());
            }
            List<JourneyWithBLOBs> journeyList = journeyMapper.selectByExampleWithBLOBs(criteria);
            if(!CollectionUtils.isEmpty(journeyList)){
                for(JourneyWithBLOBs j : journeyList){
                    JourneyVo vo = new JourneyVo();
                    try {
                        BeanUtilExt.copyProperties(vo,j);
                        //每天行程
                        JourneyDayCriteria dayCriteria = new JourneyDayCriteria();
                        dayCriteria.createCriteria().andJourneyIdEqualTo(j.getId());
                        List<JourneyDay> journeyDayList = journeyDayMapper.selectByExample(dayCriteria);
                        List<JourneyDayVo> journeyDayVoList = new ArrayList<>();
                        if(!CollectionUtils.isEmpty(journeyDayList)){
                            for(JourneyDay day : journeyDayList){
                                JourneyDayVo dayVo = new JourneyDayVo();
                                BeanUtilExt.copyProperties(dayVo,day);
                                //查询具体安排
                                JourneyItemCriteria itemCriteria = new JourneyItemCriteria();
                                itemCriteria.createCriteria().andJourneyDayIdEqualTo(day.getId());
                                List<JourneyItem> itemList = journeyItemMapper.selectByExample(itemCriteria);
                                List<JourneyItemVo> itemVoList = new ArrayList<>();
                                if(!CollectionUtils.isEmpty(itemList)){
                                    for(JourneyItem item:itemList){
                                        JourneyItemVo itemVo = new JourneyItemVo();
                                        BeanUtilExt.copyProperties(itemVo,item);
                                        itemVo.setCurrentDay(day.getCurrentDay());
                                        itemVo.setJourneyId(j.getId());
                                        itemVoList.add(itemVo);
                                    }
                                }
                                dayVo.setJourneyItemVoList(itemVoList);
                                journeyDayVoList.add(dayVo);
                            }
                        }

                        vo.setJourneyDayVoList(journeyDayVoList);
                        addJourneyVoExtAttr(vo,currentPhone);

                    } catch (InvocationTargetException | IllegalAccessException e) {
                        logger.info("拷贝行程分享id:"+j.getId()+"属性失败"+e.toString());
                    }
                    list.add(vo);
                }
            }
        }
        return list ;
    }

    @Override
    public List<Journey> queryRecommendJourney() {
        JourneyCriteria criteria = new JourneyCriteria();
        criteria.setOrderByClause(" createtime ");
        criteria.setLimitEnd(5);
        List<Journey> list =  journeyMapper.selectByExample(criteria);
        return list;
    }


    @Override
    public List<JourneyVo> searchJourneyListApp(SearchCondition condition, String currentPhone) {
        List<JourneyVo> list = null;
        if(null != condition){
            List<Integer> ids = journeySearchService.queryJourneyVoList(condition);
            if(!CollectionUtils.isEmpty(ids)){
                condition.setIdsIn(ids);
                list = queryJourneyListApp(condition,currentPhone);
            }
        }
        return list;
    }

    @Override
    public Page<JourneyVo> queryJourneyVos(SearchCondition condition, String currentPhone) {
        Page<JourneyVo> page  = new Page<>();
        List<JourneyVo> list = null;
        if(null != condition){
            JourneyCriteria criteria = new JourneyCriteria();
            criteria.createCriteria().andIsEnableEqualTo(true);
            criteria.setOrderByClause(" createtime desc ");
            int count = journeyMapper.countByExample(criteria);

            condition.setIfEnable(true);
            condition.setOrderByClause(" createtime desc ");
            if(count>0){
                list = queryJourneyVoListApp(condition,currentPhone);
                page.setTotalCount(count);
                page.setPageData(list);
            }
        }
        return page ;
    }

    @Override
    public Integer queryJourneyCount(SearchCondition condition) {
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
    public List<JourneyVo> queryMyCollectList(SearchCondition condition, String currentPhone) {
        List<JourneyVo> list = null;
        List<Integer> ids = new ArrayList<>();
        if(null != condition){
            JourneyCollectionCriteria criteria = new JourneyCollectionCriteria();
            criteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.COLLECTION.getCode());
            criteria.setOrderByClause(" id desc");
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
                list = queryJourneyVoListApp(condition,currentPhone);
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
