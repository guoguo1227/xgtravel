package com.stars.travel.service.impl;

import com.lagou.platform.common.Page;
import com.stars.common.enums.CollectionTopType;
import com.stars.common.enums.CommentTypeEnum;
import com.stars.common.utils.BeanUtilExt;
import com.stars.travel.dao.base.mapper.CommentMapper;
import com.stars.travel.dao.base.mapper.MicroblogCollectionMapper;
import com.stars.travel.dao.base.mapper.MicroblogMapper;
import com.stars.travel.dao.ext.mapper.MicroblogVoMapper;
import com.stars.travel.model.base.*;
import com.stars.travel.model.condition.AuctionSearchCondition;
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
    public Page<MicroblogVo> querySharedMicroblogVoPage(AuctionSearchCondition condition, String currentPhone) {
        Page<MicroblogVo> page = new Page<>();
        page.setPageSize(condition.getLimit());
        if(null != condition){
            condition.setIfEnable(true); //可用
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
    public List<MicroblogVo> querySharedMicroblogVoApp(AuctionSearchCondition condition,String currentPhone) {

        List<MicroblogVo> list = null;
        if(null != condition){
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
            condition.setOrderByClause(" id desc");

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
    public int countSharedMicroblogVoList(AuctionSearchCondition condition) {
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
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollection collection= new MicroblogCollection();
            collection.setCreatetime(new Date());
            collection.setPhone(currentPhone);
            collection.setRelateId(id);
            collection.setType(CollectionTopType.COLLECTION.getCode());
            int i = microblogCollectionMapper.insertSelective(collection);
            if(i>0){
                success = true;
            }
        }
        return success;
    }

    @Override
    public boolean uncollectMicroblog(Integer id, String currentPhone) {
        boolean success = false;
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollectionCriteria criteria = new MicroblogCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.COLLECTION.getCode());
            List<MicroblogCollection> list = microblogCollectionMapper.selectByExample(criteria);
            if(null != list && list.size()>0){
                for(MicroblogCollection collection : list){
                    int i = microblogCollectionMapper.deleteByPrimaryKey(collection.getId());
                    if(i>0){
                        success = true;
                        logger.info("成功删除用户:"+currentPhone+"收藏的微游记:"+id);
                    }
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
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollection collection= new MicroblogCollection();
            collection.setCreatetime(new Date());
            collection.setPhone(currentPhone);
            collection.setRelateId(id);
            collection.setType(CollectionTopType.TOP.getCode());
            int i = microblogCollectionMapper.insertSelective(collection);
            if(i>0){
                MicroblogWithBLOBs microblog = microblogMapper.selectByPrimaryKey(id);
                if(null != microblog){
                    int count = microblog.getTop()+1;
                    microblog.setTop(count);
                    int j = microblogMapper.updateByPrimaryKeySelective(microblog);
                    if(j>0){
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
        if(null != id && !StringUtils.isBlank(currentPhone)){
            MicroblogCollectionCriteria criteria = new MicroblogCollectionCriteria();
            criteria.createCriteria().andRelateIdEqualTo(id).andPhoneEqualTo(currentPhone).andTypeEqualTo(CollectionTopType.TOP.getCode());
            List<MicroblogCollection> list = microblogCollectionMapper.selectByExample(criteria);
            if(null != list && list.size()>0){
                for(MicroblogCollection collection : list){
                    int i = microblogCollectionMapper.deleteByPrimaryKey(collection.getId());
                    if(i>0){
                        MicroblogWithBLOBs microblog = microblogMapper.selectByPrimaryKey(id);
                        if(null != microblog){
                            int count = 0;
                            if(microblog.getTop()-1 >0){
                                microblog.setTop(count);
                            }
                            microblog.setTop(0);
                            int j = microblogMapper.updateByPrimaryKeySelective(microblog);
                            if(j>0){
                                result.setSuccess(true);
                                result.setData(count);
                            }
                        }
                        logger.info("成功删除用户:"+currentPhone+"顶赞的微游记:"+id);
                    }
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
    private void addMicroblogExtAttr(MicroblogVo vo,String currentPhone){
        //该用户是否对该行程评论
        boolean isComment = false;
        //该用户是否收藏
        boolean isCollection = false;
        //该用户是否顶赞
        boolean isTop = false;
        if(!StringUtils.isBlank(currentPhone)){
            isComment = ifComment(vo.getId(),currentPhone);
            isCollection = ifCollectionTop(vo.getId(),currentPhone,CollectionTopType.COLLECTION.getCode());
            isTop = ifCollectionTop(vo.getId(),currentPhone,CollectionTopType.TOP.getCode());
        }
        //评论数
        vo.setCommentCount(queryCommentCount(vo.getId()).toString());
        //点赞数
        vo.setTopCount(queryTopCount(vo.getId()).toString());
        vo.setCollection(isCollection);
        vo.setComment(isComment);
        vo.setHasTop(isTop);
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
