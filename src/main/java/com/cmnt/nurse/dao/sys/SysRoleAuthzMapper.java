package com.cmnt.nurse.dao.sys;

import com.cmnt.nurse.model.sys.SysRoleAuthz;

public interface SysRoleAuthzMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRoleAuthz record);

    int insertSelective(SysRoleAuthz record);

    SysRoleAuthz selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRoleAuthz record);

    int updateByPrimaryKey(SysRoleAuthz record);
}