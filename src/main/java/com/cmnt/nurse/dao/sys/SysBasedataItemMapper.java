package com.cmnt.nurse.dao.sys;

import com.cmnt.nurse.model.sys.SysBasedataItem;

public interface SysBasedataItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysBasedataItem record);

    int insertSelective(SysBasedataItem record);

    SysBasedataItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysBasedataItem record);

    int updateByPrimaryKey(SysBasedataItem record);
}