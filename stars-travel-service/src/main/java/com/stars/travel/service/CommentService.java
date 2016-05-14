package com.stars.travel.service;

import com.lagou.platform.common.Page;
import com.stars.travel.model.base.Comment;
import com.stars.travel.model.condition.AuctionSearchCondition;
import com.stars.travel.model.ext.CommentVo;

import java.util.List;

/**
 * Description : 评论服务接口
 * Author : guo
 * Date : 2016/3/20 13:33
 */
public interface CommentService {

    /**
     * @Description : 查询评论分页列表
     * @param condition
     * @return
     */
    public Page<CommentVo> queryCommentPage(AuctionSearchCondition condition);

    /**
     * @Description : 查询评论列表-移动端
     * @param condition
     * @return
     */
    public List<CommentVo> queryCommentListApp(AuctionSearchCondition condition);

     /**
     * @Description : 查询评论数量
     * @param condition
     * @return
     */
    public Integer queryCommentCount(AuctionSearchCondition condition);

    /**
     * @Description :添加评论
     * @param comment
     * @return
     */
    public boolean addComment(Comment comment);

    /**
     * @Description : 删除微游记下的所有评论
     * @param id
     * @return
     */
    public boolean deleteCommentByMicroblogId(Integer id);
}
