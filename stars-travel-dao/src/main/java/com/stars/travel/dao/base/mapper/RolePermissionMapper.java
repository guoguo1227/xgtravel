package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.RolePermission;
import com.stars.travel.model.base.RolePermissionCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper extends BaseMapper {
    int countByExample(RolePermissionCriteria example);

    int deleteByExample(RolePermissionCriteria example);

    int deleteByPrimaryKey(Integer rolePermissionId);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<RolePermission> selectByExample(RolePermissionCriteria example);

    RolePermission selectByPrimaryKey(Integer rolePermissionId);

    int updateByExampleSelective(@Param("record") RolePermission record, @Param("example") RolePermissionCriteria example);

    int updateByExample(@Param("record") RolePermission record, @Param("example") RolePermissionCriteria example);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
}