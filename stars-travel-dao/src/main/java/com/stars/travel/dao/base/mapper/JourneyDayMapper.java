package com.stars.travel.dao.base.mapper;
import com.stars.travel.dao.base.BaseMapper;

import com.stars.travel.model.base.JourneyDay;
import com.stars.travel.model.base.JourneyDayCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JourneyDayMapper extends BaseMapper {
    int countByExample(JourneyDayCriteria example);

    int deleteByExample(JourneyDayCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(JourneyDay record);

    int insertSelective(JourneyDay record);

    List<JourneyDay> selectByExample(JourneyDayCriteria example);

    JourneyDay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") JourneyDay record, @Param("example") JourneyDayCriteria example);

    int updateByExample(@Param("record") JourneyDay record, @Param("example") JourneyDayCriteria example);

    int updateByPrimaryKeySelective(JourneyDay record);

    int updateByPrimaryKey(JourneyDay record);
}