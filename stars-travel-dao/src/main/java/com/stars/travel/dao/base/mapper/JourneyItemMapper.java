package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.JourneyItem;
import com.stars.travel.model.base.JourneyItemCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JourneyItemMapper extends BaseMapper {
    int countByExample(JourneyItemCriteria example);

    int deleteByExample(JourneyItemCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(JourneyItem record);

    int insertSelective(JourneyItem record);

    List<JourneyItem> selectByExampleWithBLOBs(JourneyItemCriteria example);

    List<JourneyItem> selectByExample(JourneyItemCriteria example);

    JourneyItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") JourneyItem record, @Param("example") JourneyItemCriteria example);

    int updateByExampleWithBLOBs(@Param("record") JourneyItem record, @Param("example") JourneyItemCriteria example);

    int updateByExample(@Param("record") JourneyItem record, @Param("example") JourneyItemCriteria example);

    int updateByPrimaryKeySelective(JourneyItem record);

    int updateByPrimaryKeyWithBLOBs(JourneyItem record);

    int updateByPrimaryKey(JourneyItem record);
}