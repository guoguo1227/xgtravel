package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.UserRole;
import com.stars.travel.model.base.UserRoleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper {
    int countByExample(UserRoleCriteria example);

    int deleteByExample(UserRoleCriteria example);

    int deleteByPrimaryKey(Integer userRoleId);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleCriteria example);

    UserRole selectByPrimaryKey(Integer userRoleId);

    int updateByExampleSelective(@Param("record") UserRole record, @Param("example") UserRoleCriteria example);

    int updateByExample(@Param("record") UserRole record, @Param("example") UserRoleCriteria example);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
}