package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.Log;
import com.stars.travel.model.base.LogCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogMapper extends BaseMapper {
    int countByExample(LogCriteria example);

    int deleteByExample(LogCriteria example);

    int deleteByPrimaryKey(Integer logId);

    int insert(Log record);

    int insertSelective(Log record);

    List<Log> selectByExample(LogCriteria example);

    Log selectByPrimaryKey(Integer logId);

    int updateByExampleSelective(@Param("record") Log record, @Param("example") LogCriteria example);

    int updateByExample(@Param("record") Log record, @Param("example") LogCriteria example);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}