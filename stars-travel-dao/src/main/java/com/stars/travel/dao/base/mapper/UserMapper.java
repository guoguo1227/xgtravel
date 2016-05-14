package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.User;
import com.stars.travel.model.base.UserCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper {
    int countByExample(UserCriteria example);

    int deleteByExample(UserCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserCriteria example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByExample(@Param("record") User record, @Param("example") UserCriteria example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}