package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.Journey;
import com.stars.travel.model.base.JourneyCriteria;
import com.stars.travel.model.base.JourneyWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JourneyMapper extends BaseMapper {
    int countByExample(JourneyCriteria example);

    int deleteByExample(JourneyCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(JourneyWithBLOBs record);

    int insertSelective(JourneyWithBLOBs record);

    List<JourneyWithBLOBs> selectByExampleWithBLOBs(JourneyCriteria example);

    List<Journey> selectByExample(JourneyCriteria example);

    JourneyWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") JourneyWithBLOBs record, @Param("example") JourneyCriteria example);

    int updateByExampleWithBLOBs(@Param("record") JourneyWithBLOBs record, @Param("example") JourneyCriteria example);

    int updateByExample(@Param("record") Journey record, @Param("example") JourneyCriteria example);

    int updateByPrimaryKeySelective(JourneyWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(JourneyWithBLOBs record);

    int updateByPrimaryKey(Journey record);
}