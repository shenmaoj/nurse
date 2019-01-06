package com.cmnt.nurse.service.sys;

import com.cmnt.nurse.vo.sys.MenuVO;

import java.util.List;

/**
 * Created by shenmj on 2019/1/6.
 */
public interface MenuService {

    /**
     * 获取用户所有菜单
     * @return
     */
    List<MenuVO> availableMenu(List<String> menuIds);

    /**
     * 保存菜单信息
     * @param menuVO
     * @return
     */
    int saveMenu(MenuVO menuVO);

    /**
     * 删除菜单信息
     * @param id
     * @return
     */
    int deleteMenu(String id);

}
