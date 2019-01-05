package com.cmnt.nurse.dao.sys;

import com.cmnt.nurse.model.sys.SysUser;

import java.util.Map;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser findUserByName(Map<String, Object> map);
}