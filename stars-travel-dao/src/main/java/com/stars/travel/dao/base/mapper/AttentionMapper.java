package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.Attention;
import com.stars.travel.model.base.AttentionCriteria;
import com.stars.travel.model.base.AttentionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttentionMapper extends BaseMapper {
    int countByExample(AttentionCriteria example);

    int deleteByExample(AttentionCriteria example);

    int deleteByPrimaryKey(AttentionKey key);

    int insert(Attention record);

    int insertSelective(Attention record);

    List<Attention> selectByExample(AttentionCriteria example);

    Attention selectByPrimaryKey(AttentionKey key);

    int updateByExampleSelective(@Param("record") Attention record, @Param("example") AttentionCriteria example);

    int updateByExample(@Param("record") Attention record, @Param("example") AttentionCriteria example);

    int updateByPrimaryKeySelective(Attention record);

    int updateByPrimaryKey(Attention record);
}