package com.stars.travel.service.impl;

import com.stars.common.utils.Page;
import com.stars.common.enums.CollectionTopType;
import com.stars.common.enums.CommentTypeEnum;
import com.stars.common.utils.BeanUtilExt;
import com.stars.travel.dao.base.mapper.CommentMapper;
import com.stars.travel.dao.base.mapper.MicroblogCollectionMapper;
import com.stars.travel.dao.base.mapper.MicroblogMapper;
import com.stars.travel.dao.ext.mapper.MicroblogVoMapper;
import com.stars.travel.model.base.*;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.MicroblogVo;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.model.ext.UserInfo;
import com.stars.travel.service.CommentService;
import com.stars.travel.service.MicroblogVoService;
import com.stars.travel.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description :
 * Author : guo
 * Date : 2016/2/29 23:04
 */
@Service
public class MicroblogVoServiceImpl implements MicroblogVoService {

    private static final Logger logger = LoggerFactory.getLogger(MicroblogVoServiceImpl.class);

    @Autowired
    private MicroblogVoMapper microblogVoMapper;

    @Autowired
    private MicroblogMapper microblogMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MicroblogCollectionMapper microblogCollectionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Page<MicroblogVo> querySharedMicroblogVoPage(SearchCondition condition, String currentPhone) {
        Page<MicroblogVo> page = new Page<>();
        page.setPageSize(condition.getLimit());
        if(null != condition){
            //condition.setIfEnable(true); //可用
            condition.setOrderByClause(" createtime desc");
            int count = countSharedMicroblogVoList(condition);
            if(count >0){
                List<MicroblogVo> list = microblogVoMapper.querySharedMicroblogList(condition);

                if(null !=  list && list.size()>0){
                    for(MicroblogVo vo : list){
                        addMicroblogExtAttr(vo,currentPhone);
                    }
                }
                page.setTotalCount(count);
                page.setPageData(list);
            }
        }
        return page;
    }

    @Override
    public List<MicroblogVo> querySharedMicroblogVoApp(SearchCondition condition, String currentPhone) {

        List<MicroblogVo> list = null;
        if(null != condition){
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
            //最热
            if(null != condition.getIfHot() && condition.getIfHot()){
                condition.setOrderByClause(" microblog.top_count desc "); //点赞倒序
            }else{
                //排序
                condition.setOrderByClause(" microblog.id desc"); //按照id倒序
            }

            //我的
            if(null != condition.getMy()){
                if(condition.getMy()){
                    condition.setPhone(currentPhone);
                }
            }
            //他人的--phone
            if(null != condition.getPhone()){
                condition.setPhone(condition.getPhone());
            }
            //他人的-- userId
            if(null != condition.getUserId()){
                User user = userService.queryUserById(condition.getUserId());
                if(null != user && !StringUtils.isBlank(user.getPhone())){
                    condition.setPhone(user.getPhone());
                }
            }
            condition.setIfEnable(true); //可用

            list = microblogVoMapper.querySharedMicroblogList(condition);
            if(null !=  list && list.size()>0){
                for(MicroblogVo vo : list){
                    addMicroblogExtAttr(vo,currentPhone);
                }
            }
        }
        return list;
    }

    @Override
    public List<MicroblogVo> queryMyCollection(SearchCondition condition, String currentPhone) {
        List<MicroblogVo> list = null;
        List<Integer> ids = new ArrayList<>();
        if(null != condition){

            MicroblogCollectionCriteria collectionCriteria = new MicroblogCollectionCriteria();
            collectionCriteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.COLLECTION.getCode());
            List<MicroblogCollection> collections = microblogCollectionMapper.selectByExample(collectionCriteria);
            //收藏列表
            if(!collections.isEmpty()){
                for(MicroblogCollection collection: collections){
                    ids.add(collection.getRelateId());
                }
            }
            //最新，历史
            if(null != condition.getIfNew()){
                if(condition.getIfNew()){
                    //id大于
                    if(null != condition.getfId()){
                        condition.setIdGreaterThan(condition.getfId());
                    }
                    //点赞数大于
                    if(null != condition.getTopCount()){
                        condition.setTopGreaterThan(condition.getTopCount());
                    }
                }else{
                    //id小于
                    if(null != condition.getfId()){
                        condition.setIdLessThan(condition.getfId());
                    }
                    //点赞数小于
                    if(null != condition.getTopCount()){
                        condition.setTopLessTan(condition.getTopCount());
                    }

                }
            }
            //排序
            if(null != condition.getOrderByClause()){
                condition.setOrderByClause(" microblog.id desc");
            }

            if(ids.size()>0){
                condition.setIdsIn(ids);
                list = microblogVoMapper.querySharedMicroblogList(condition);
                if(!list.isEmpty()){
                    for(MicroblogVo vo : list){
                        addMicroblogExtAttr(vo,currentPhone);
                    }
                }
            }
        }
        return list;
    }

    @Override
    public int countSharedMicroblogVoList(SearchCondition condition) {
        int count = 0;
        if(null != condition){
             count = microblogVoMapper.countSharedMicroblogList(condition);
        }
        return count;
    }

    @Override
    public boolean addMicroblog(MicroblogVo microblogVo) {
            if(null != microblogVo){
                MicroblogWithBLOBs microblog = new MicroblogWithBLOBs();
            try{
                BeanUtils.copyProperties(microblogVo,microblog);
                microblog.setCreatetime(new Date());
                int i = microblogMapper.insertSelective(microblog);
                if(i>0){
                    return true;
                }
            }catch (BeansException e){
                logger.info("添加微游记失败。"+e.toString());
            }catch (Exception e){
                logger.info("添加微游记失败。",e);
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteMicroblog(Integer id) {
        boolean success = false;
        if(null != id){
            //删除微游记的评论
            success = commentService.deleteCommentByMicroblogId(id);
            if(success){
                MicroblogWithBLOBs microblog = microblogMapper.selectByPrimaryKey(id);
                if(null != microblog){
                    microblog.setIsEnable(false); //标记删除
                    microblog.setUpdatetime(new Date());
                    int i = microblogMapper.updateByPrimaryKeySelective(microblog);
                    if(i>0){
                        success = true;
                    }
                }
            }
        }
        return success;
    }

    @Override
    public boolean collectMicroblog(Integer id, String currentPhone) {
        boolean success = false;
        Date nowDate = new Date();
        int j = 0;
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollectionCriteria criteria = new MicroblogCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.COLLECTION.getCode());
            List<MicroblogCollection> list = microblogCollectionMapper.selectByExample(criteria);
            //记录存在
            if(!list.isEmpty()){
                MicroblogCollection microblogCollection = list.get(0);
                microblogCollection.setIsEnable(true);
                microblogCollection.setUpdatetime(nowDate);
                j = microblogCollectionMapper.updateByPrimaryKeySelective(microblogCollection);
            }else{
                //记录不存在
                String phoneMicroblogKey = id+"-"+currentPhone+"-"+CollectionTopType.COLLECTION.getDescription();
                MicroblogCollection collection= new MicroblogCollection();
                collection.setCreatetime(nowDate);
                collection.setPhone(currentPhone);
                collection.setRelateId(id);
                collection.setPhoneMicroblogKey(phoneMicroblogKey);
                collection.setIsEnable(true);
                collection.setType(CollectionTopType.COLLECTION.getCode());
                j = microblogCollectionMapper.insertSelective(collection);
            }

            if(j>0){
                success = true;
            }
        }
        return success;
    }

    @Override
    public boolean uncollectMicroblog(Integer id, String currentPhone) {
        boolean success = false;
        Date nowDate = new Date();
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollectionCriteria criteria = new MicroblogCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.COLLECTION.getCode()).andIsEnableEqualTo(true);
            List<MicroblogCollection> list = microblogCollectionMapper.selectByExample(criteria);
            if(!list.isEmpty()){
                MicroblogCollection collection = list.get(0);
                collection.setUpdatetime(nowDate);
                collection.setIsEnable(false);
                int i = microblogCollectionMapper.updateByPrimaryKeySelective(collection);
                if(i>0){
                    success = true;
                    logger.info("成功取消用户:"+currentPhone+"收藏的微游记:"+id);
                }
            }
        }
        return success;
    }


    /**
     * @Description : 用户是否收藏，顶赞该行程
     * @param id
     * @param currentPhone
     * @return
     */
    @Override
    public boolean ifCollectionTop(Integer id,String currentPhone,Integer type) {
        boolean success = false;
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollectionCriteria criteria = new MicroblogCollectionCriteria();
            criteria.createCriteria().andIsEnableEqualTo(true).andPhoneEqualTo(currentPhone).andRelateIdEqualTo(id).andTypeEqualTo(type);
            int count = microblogCollectionMapper.countByExample(criteria);
            if(count >0){
                success = true;
            }
        }
        return success;
    }

    @Override
    public RequestResult topMicroblog(Integer id, String currentPhone) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        Date nowDate = new Date();
        int j = 0;
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollectionCriteria criteria = new MicroblogCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.TOP.getCode());
            List<MicroblogCollection> list = microblogCollectionMapper.selectByExample(criteria);
            //记录存在
            if(!list.isEmpty()){
                MicroblogCollection microblogCollection = list.get(0);
                microblogCollection.setUpdatetime(nowDate);
                microblogCollection.setIsEnable(true);
                j = microblogCollectionMapper.updateByPrimaryKeySelective(microblogCollection);
            }else{
                //记录不存在
                String phoneMicroblogKey = id+"-"+currentPhone+"-"+CollectionTopType.TOP.getDescription();
                MicroblogCollection collection= new MicroblogCollection();
                collection.setCreatetime(nowDate);
                collection.setPhone(currentPhone);
                collection.setRelateId(id);
                collection.setPhoneMicroblogKey(phoneMicroblogKey);
                collection.setIsEnable(true);
                collection.setType(CollectionTopType.TOP.getCode());
                j = microblogCollectionMapper.insertSelective(collection);
            }

            if(j>0){
                MicroblogWithBLOBs microblog = microblogMapper.selectByPrimaryKey(id);
                if(null != microblog){
                    int count = microblog.getTopCount()+1;
                    microblog.setTopCount(count);
                    int i = microblogMapper.updateByPrimaryKeySelective(microblog);
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
    public RequestResult untopMicroblog(Integer id, String currentPhone) {
        RequestResult result = new RequestResult();
        result.setSuccess(false);
        Date nowDate = new Date();
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollectionCriteria criteria = new MicroblogCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.TOP.getCode()).andIsEnableEqualTo(true);
            List<MicroblogCollection> list = microblogCollectionMapper.selectByExample(criteria);
            if(!list.isEmpty()){
                MicroblogCollection collection = list.get(0);
                collection.setUpdatetime(nowDate);
                collection.setIsEnable(false);
                int i = microblogCollectionMapper.updateByPrimaryKeySelective(collection);
                if(i>0){
                    MicroblogWithBLOBs microblog = microblogMapper.selectByPrimaryKey(id);
                    if(null != microblog){
                        int count = 0;
                        if(microblog.getTopCount()-1 >0){
                            microblog.setTopCount(count);
                        }
                        microblog.setTopCount(0);
                        int j = microblogMapper.updateByPrimaryKeySelective(microblog);
                        if(j>0){
                            result.setSuccess(true);
                            result.setData(count);
                        }
                    }
                    logger.info("成功取消用户:"+currentPhone+"顶赞的微游记:"+id);
                }
            }
        }
        return result;
    }

    /**
     * @Description : 是否评论
     * @param id
     * @param currentPhone
     * @return
     */
    public boolean ifComment(Integer id,String currentPhone){
        boolean ifComment = false;
        CommentCriteria criteria = new CommentCriteria();
        criteria.createCriteria().andIsEnableEqualTo(true).andTypeEqualTo(CommentTypeEnum.MICROBLOG.getDescription())
                .andRelateIdEqualTo(id).andPhoneEqualTo(currentPhone);

        int count = commentMapper.countByExample(criteria);
        if(count >0){
            ifComment = true;
        }
        return ifComment;
    }

    /**
     * @Description ：查询微游记的评论数
     * @param id
     * @return
     */
    public Integer queryCommentCount(Integer id){
        Integer count = 0;
        CommentCriteria criteria = new CommentCriteria();
        criteria.createCriteria().andIsEnableEqualTo(true).andTypeEqualTo(CommentTypeEnum.MICROBLOG.getDescription()).andRelateIdEqualTo(id);

        count = commentMapper.countByExample(criteria);

        return count;
    }

    /**
     * @Description : 查询微游记的点赞数
     * @param id
     * @return
     */
    public Integer queryTopCount(Integer id){
        Integer count = 0;
        MicroblogCollectionCriteria criteria = new MicroblogCollectionCriteria();
        criteria.createCriteria().andIsEnableEqualTo(true).andTypeEqualTo(CollectionTopType.TOP.getCode()).andRelateIdEqualTo(id);
        count = microblogCollectionMapper.countByExample(criteria);

        return count;
    }

    /**
     * @Description : 添加微游记扩展属性
     * @param vo
     * @param currentPhone
     */
    public void addMicroblogExtAttr(MicroblogVo vo,String currentPhone){
        //该用户是否对该行程评论
        boolean ifComment = false;
        //该用户是否收藏
        boolean ifCollection = false;
        //该用户是否顶赞
        boolean ifTop = false;
        if(!StringUtils.isBlank(currentPhone)){
            ifComment = ifComment(vo.getId(),currentPhone);
            ifCollection = ifCollectionTop(vo.getId(),currentPhone,CollectionTopType.COLLECTION.getCode());
            ifTop = ifCollectionTop(vo.getId(),currentPhone,CollectionTopType.TOP.getCode());
        }
        //评论数
        vo.setCommentCount(queryCommentCount(vo.getId()).toString());
        vo.setIfCollection(ifCollection);
        vo.setIfComment(ifComment);
        vo.setIfTop(ifTop);
        //创建用户
        User user = userService.queryUserByPhoneNumber(vo.getPhone());

        if(null != user){
            UserInfo userInfo = new UserInfo();
            try {
                BeanUtilExt.copyProperties(userInfo,user);
                vo.setUserInfo(userInfo);
            } catch (InvocationTargetException e) {
                logger.info("拷贝用户属性失败 "+e.toString());
            } catch (IllegalAccessException e) {
                logger.info("拷贝用户属性失败 "+e.toString());
            }
            vo.setUserInfo(userInfo);
        }
    }
}
