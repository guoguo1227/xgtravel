package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.UserCollection;
import com.stars.travel.model.base.UserCollectionCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCollectionMapper extends BaseMapper {
    int countByExample(UserCollectionCriteria example);

    int deleteByExample(UserCollectionCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserCollection record);

    int insertSelective(UserCollection record);

    List<UserCollection> selectByExample(UserCollectionCriteria example);

    UserCollection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserCollection record, @Param("example") UserCollectionCriteria example);

    int updateByExample(@Param("record") UserCollection record, @Param("example") UserCollectionCriteria example);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);
}