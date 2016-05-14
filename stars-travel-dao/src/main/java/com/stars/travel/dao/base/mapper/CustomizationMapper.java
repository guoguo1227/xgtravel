package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.Customization;
import com.stars.travel.model.base.CustomizationCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CustomizationMapper extends BaseMapper {
    int countByExample(CustomizationCriteria example);

    int deleteByExample(CustomizationCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(Customization record);

    int insertSelective(Customization record);

    List<Customization> selectByExample(CustomizationCriteria example);

    Customization selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Customization record, @Param("example") CustomizationCriteria example);

    int updateByExample(@Param("record") Customization record, @Param("example") CustomizationCriteria example);

    int updateByPrimaryKeySelective(Customization record);

    int updateByPrimaryKey(Customization record);
}