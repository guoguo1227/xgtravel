package com.stars.travel.service.impl;

import com.stars.common.utils.BeanUtilExt;
import com.stars.common.utils.Page;
import com.stars.common.enums.CommentTypeEnum;
import com.stars.travel.dao.base.mapper.CommentMapper;
import com.stars.travel.dao.base.mapper.JourneyMapper;
import com.stars.travel.dao.base.mapper.MicroblogMapper;
import com.stars.travel.dao.base.mapper.UserMapper;
import com.stars.travel.dao.ext.mapper.CommentVoMapper;
import com.stars.travel.model.base.*;
import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.CommentVo;
import com.stars.travel.model.ext.RequestResult;
import com.stars.travel.service.CommentService;
import com.stars.travel.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description : 评论服务实现
 * Author : guo
 * Date : 2016/3/20 13:56
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentVoMapper commentVoMapper;
    @Autowired
    private JourneyMapper journeyMapper;
    @Autowired
    private MicroblogMapper microblogMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Override
    public Page<CommentVo> queryCommentPage(SearchCondition condition) {
        Page<CommentVo> page = null;
        if(null != condition){
            page = new Page();
            page.setPageSize(condition.getLimit());
            condition.setIfEnable(true); //可用
            condition.setOrderByClause("  id desc ");
            if(!StringUtils.isBlank(condition.getType())){
                if(condition.getType().equals(CommentTypeEnum.JOURNEYTYPE.getCode().toString())){
                    condition.setType(CommentTypeEnum.JOURNEYTYPE.getDescription());
                }else if(condition.getType().equals(CommentTypeEnum.LOCALUSER.getCode().toString())){
                    //当地人
                    condition.setType(CommentTypeEnum.LOCALUSER.getDescription());
                }else if(condition.getType().equals(CommentTypeEnum.MICROBLOG.getCode().toString())){
                    condition.setType(CommentTypeEnum.MICROBLOG.getDescription());
                }

            }
            int count = commentVoMapper.queryCommentCount(condition);
            if(count>0){
                List<CommentVo> commentList = commentVoMapper.queryCommentList(condition);
                if(null != commentList && commentList.size()>0){
                    page.setPageData(commentList);
                }
            }
            page.setTotalCount(count);
        }
        return page;
    }

    @Override
    public List<CommentVo> queryCommentListApp(SearchCondition condition) {
        List<CommentVo> list = null;
        if(null != condition){
            condition.setIfEnable(true); //可用
            condition.setOrderByClause("  id desc ");
            if(!StringUtils.isBlank(condition.getType())){
                if(condition.getType().equals(CommentTypeEnum.JOURNEYTYPE.getCode().toString())){
                    //行程
                    condition.setType(CommentTypeEnum.JOURNEYTYPE.getDescription());
                }else if(condition.getType().equals(CommentTypeEnum.MICROBLOG.getCode().toString())){
                    //微游记
                    condition.setType(CommentTypeEnum.MICROBLOG.getDescription());
                }else if(condition.getType().equals(CommentTypeEnum.LOCALUSER.getCode().toString())){
                    //当地人
                    condition.setType(CommentTypeEnum.LOCALUSER.getDescription());
                }

            }
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
            list = commentVoMapper.queryCommentList(condition);

        }
        return list;
    }

    @Override
    public List<CommentVo> queryMyCommentList(SearchCondition condition) {
        List<CommentVo> list = new ArrayList<>();
        CommentCriteria criteria = new CommentCriteria();
        CommentCriteria.Criteria cri = criteria.createCriteria();
        if(null != condition){
            condition.setIfEnable(true); //可用
            //最新，历史
            if(null != condition.getIfNew()){
                if(condition.getIfNew()){
                    if(null != condition.getfId()){
                        cri.andIdGreaterThan(condition.getfId());
                    }
                }else{
                    if(null != condition.getfId()){
                        cri.andIdLessThan(condition.getfId());
                    }
                }
            }
            if(!StringUtils.isBlank(condition.getPhone())){
                cri.andPhoneEqualTo(condition.getPhone());
            }
            //排序
            condition.setOrderByClause(" id desc");
            List<Comment> comments = commentMapper.selectByExample(criteria);
            if(!CollectionUtils.isEmpty(comments)){
                for(Comment c : comments){
                    CommentVo vo = new CommentVo();
                    try {
                        BeanUtilExt.copyProperties(vo,c);
                    } catch (InvocationTargetException e) {
                        logger.info("拷贝评论对象属性失败,评论id:"+c.getId());
                    } catch (IllegalAccessException e) {
                        logger.info("拷贝评论对象属性失败,评论id:"+c.getId());
                    }
                    if(c.getType().equals(CommentTypeEnum.JOURNEYTYPE.getDescription())){
                        //行程
                        JourneyWithBLOBs journey = journeyMapper.selectByPrimaryKey(c.getRelateId());
                        if(null != journey){
                            vo.setTitle(journey.getTitle());
                        }
                    }else if(c.getType().equals(CommentTypeEnum.MICROBLOG.getDescription())){
                        //微游记
                        MicroblogWithBLOBs microblog = microblogMapper.selectByPrimaryKey(c.getRelateId());
                        if(null != microblog){
                            vo.setTitle(microblog.getTitle());
                        }
                    }else{
                        //当地人
                        User user = userMapper.selectByPrimaryKey(c.getRelateId());
                        if(null != user){
                            vo.setTitle(user.getName());
                        }
                    }
                    list.add(vo);
                }
            }
        }
        return list;
    }

    @Override
    public Integer queryCommentCount(SearchCondition condition) {
        int count = 0;
        if(null != condition){
            count = commentVoMapper.queryCommentCount(condition);
        }
        return count ;
    }

    @Override
    public boolean addComment(Comment comment) {
        if(null != comment){
            comment.setCreatetime(new Date());
            int i = commentMapper.insertSelective(comment);
            if(i>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteComment(Integer id) {
        if(null != id){
            int i = commentMapper.deleteByPrimaryKey(id);
            if(i>0){
                return true;
            }
        }
        return false;
    }

    @Override
    public RequestResult deleteMyComment(String currentPhone, Integer id) {
        boolean success = false;
        String message = "";
        if(null != id && !StringUtils.isBlank(currentPhone)){
            Comment comment = commentMapper.selectByPrimaryKey(id);
            //自己发布的
            if(null != comment && comment.getPhone().equals(currentPhone)){
                comment.setIsEnable(false); //标记删除
                int i = commentMapper.updateByPrimaryKey(comment);
                if(i>0){
                    success = true;
                }
            }else{
                message = "只能删除自己发布的评论";
            }
        }
        RequestResult result = new RequestResult();
        result.setSuccess(success);
        result.setMessage(message);
        return result;
    }

    @Override
    @Transactional
    public boolean deleteCommentByMicroblogId(Integer id) {
        boolean success = false;
        if(null != id){
            CommentCriteria criteria = new CommentCriteria();
            CommentCriteria.Criteria cri = criteria.createCriteria();
            cri.andRelateIdEqualTo(id).andIsEnableEqualTo(true);
            List<Comment> list = commentMapper.selectByExample(criteria);
            if(!CollectionUtils.isEmpty(list)){
                for(Comment comment:list){
                    comment.setIsEnable(false); //标记删除
                    int i = commentMapper.updateByPrimaryKeySelective(comment);
                    if(i>0){
                        success = true;
                    }
                }
            }else{
                success = true;
            }
        }
        return success;
    }
}
