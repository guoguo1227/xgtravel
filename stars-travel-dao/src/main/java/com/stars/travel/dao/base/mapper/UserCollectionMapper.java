package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.UserCollection;
import com.stars.travel.model.base.UserCollectionCriteria;
import com.stars.travel.model.base.UserCollectionKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCollectionMapper extends BaseMapper {
    int countByExample(UserCollectionCriteria example);

    int deleteByExample(UserCollectionCriteria example);

    int deleteByPrimaryKey(UserCollectionKey key);

    int insert(UserCollection record);

    int insertSelective(UserCollection record);

    List<UserCollection> selectByExample(UserCollectionCriteria example);

    UserCollection selectByPrimaryKey(UserCollectionKey key);

    int updateByExampleSelective(@Param("record") UserCollection record, @Param("example") UserCollectionCriteria example);

    int updateByExample(@Param("record") UserCollection record, @Param("example") UserCollectionCriteria example);

    int updateByPrimaryKeySelective(UserCollection record);

    int updateByPrimaryKey(UserCollection record);
}