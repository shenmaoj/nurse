package com.cmnt.nurse.dao.sys;

import com.cmnt.nurse.model.sys.SysMenu;
import com.cmnt.nurse.vo.sys.MenuVO;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);


    List<MenuVO> findMenuOfIds(List<String> menuIds);

    List<MenuVO> findMenusIdOfParentId(Map<String,Object> map);

    int deleteMenuOfIds(List<String> menuIds);

    MenuVO findMenuByCode(Map<String,Object> map);
}