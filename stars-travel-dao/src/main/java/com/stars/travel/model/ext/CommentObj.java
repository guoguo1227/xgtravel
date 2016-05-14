package com.stars.travel.model.ext;

import java.util.List;

/**
 * Description : 评论列表对象
 * Author : guo
 * Date : 2016/5/11 22:21
 */
public class CommentObj {

    private Integer count ;

    private List<CommentVo> commentVoList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CommentVo> getCommentVoList() {
        return commentVoList;
    }

    public void setCommentVoList(List<CommentVo> commentVoList) {
        this.commentVoList = commentVoList;
    }
}
