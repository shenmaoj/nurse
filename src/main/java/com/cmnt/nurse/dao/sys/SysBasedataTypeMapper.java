package com.cmnt.nurse.dao.sys;

import com.cmnt.nurse.model.sys.SysBasedataType;

public interface SysBasedataTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysBasedataType record);

    int insertSelective(SysBasedataType record);

    SysBasedataType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysBasedataType record);

    int updateByPrimaryKey(SysBasedataType record);
}