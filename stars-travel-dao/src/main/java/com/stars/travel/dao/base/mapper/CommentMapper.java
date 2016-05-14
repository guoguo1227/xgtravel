package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.Comment;
import com.stars.travel.model.base.CommentCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper extends BaseMapper {
    int countByExample(CommentCriteria example);

    int deleteByExample(CommentCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentCriteria example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentCriteria example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentCriteria example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}