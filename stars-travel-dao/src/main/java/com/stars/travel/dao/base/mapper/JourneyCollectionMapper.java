package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.JourneyCollection;
import com.stars.travel.model.base.JourneyCollectionCriteria;
import com.stars.travel.model.base.JourneyCollectionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface JourneyCollectionMapper extends BaseMapper {
    int countByExample(JourneyCollectionCriteria example);

    int deleteByExample(JourneyCollectionCriteria example);

    int deleteByPrimaryKey(JourneyCollectionKey key);

    int insert(JourneyCollection record);

    int insertSelective(JourneyCollection record);

    List<JourneyCollection> selectByExample(JourneyCollectionCriteria example);

    JourneyCollection selectByPrimaryKey(JourneyCollectionKey key);

    int updateByExampleSelective(@Param("record") JourneyCollection record, @Param("example") JourneyCollectionCriteria example);

    int updateByExample(@Param("record") JourneyCollection record, @Param("example") JourneyCollectionCriteria example);

    int updateByPrimaryKeySelective(JourneyCollection record);

    int updateByPrimaryKey(JourneyCollection record);
}