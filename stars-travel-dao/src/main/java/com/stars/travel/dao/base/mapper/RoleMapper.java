package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.Role;
import com.stars.travel.model.base.RoleCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper {
    int countByExample(RoleCriteria example);

    int deleteByExample(RoleCriteria example);

    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleCriteria example);

    Role selectByPrimaryKey(Integer roleId);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleCriteria example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleCriteria example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}