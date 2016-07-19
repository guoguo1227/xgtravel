package com.stars.travel.dao.ext.mapper;

import com.stars.travel.model.condition.SearchCondition;
import com.stars.travel.model.ext.CommentVo;

import java.util.List;

/**
 * Description : 评论扩展服务
 * Author : guo
 * Date : 2016/3/20 14:42
 */
public interface CommentVoMapper {

    /**
     * @Description :　查询评论分页列表
     * @param condition
     * @return
     */
    List<CommentVo> queryCommentList(SearchCondition condition);

    /**
     * @Description : 查询评论数量
     * @param condition
     * @return
     */
    Integer queryCommentCount(SearchCondition condition);

}
