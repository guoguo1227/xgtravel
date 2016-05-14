package com.stars.travel.dao.base.mapper;

import com.stars.travel.dao.base.BaseMapper;
import com.stars.travel.model.base.Permission;
import com.stars.travel.model.base.PermissionCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionMapper extends BaseMapper {
    int countByExample(PermissionCriteria example);

    int deleteByExample(PermissionCriteria example);

    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionCriteria example);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByExampleSelective(@Param("record") Permission record, @Param("example") PermissionCriteria example);

    int updateByExample(@Param("record") Permission record, @Param("example") PermissionCriteria example);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}