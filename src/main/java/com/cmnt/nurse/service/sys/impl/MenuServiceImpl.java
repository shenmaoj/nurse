package com.cmnt.nurse.service.sys.impl;

import com.alibaba.fastjson.JSONObject;
import com.cmnt.nurse.common.Context;
import com.cmnt.nurse.common.utils.BeanCopyUtils;
import com.cmnt.nurse.common.utils.DateUtils;
import com.cmnt.nurse.common.utils.StringUtils;
import com.cmnt.nurse.dao.sys.SysMenuMapper;
import com.cmnt.nurse.model.sys.SysMenu;
import com.cmnt.nurse.service.sys.MenuService;
import com.cmnt.nurse.vo.sys.MenuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Describe
 * @auther 78645
 * @create 2019/1/7
 **/
@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    SysMenuMapper menuMapper;

    @Override
    public List<MenuVO> availableMenu(List<String> menuIds) {
        return menusToTree(menuMapper.findMenuOfIds(menuIds));
    }

    @Override
    public int saveMenu(MenuVO menuVO) {
        logger.info("{} {} 修改了菜单数据：{}", Context.getLoginUserId(), DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), JSONObject.toJSONString(menuVO));
        SysMenu menu = new SysMenu();
        BeanCopyUtils.copyProperties(menuVO, menu);
        if (StringUtils.isBlank(menu.getId())) {
            //新增
            menu.setId(StringUtils.createId());
            return menuMapper.insert(menu);
        } else {
            return menuMapper.updateByPrimaryKeySelective(menu);
        }
    }

    @Override
    public int deleteMenu(String id) {

        /* 查找子菜单id */
        List<String> menuIds = menuMapper.findMenusIdOfParentId(id);
        if (null == menuIds) {
            menuIds = new ArrayList<>();
        }
        menuIds.add(id);
        /*
        * TODO 删除菜单关联的角色
        * 2019/1/7
        */
        logger.info("{} {} 删除了菜单及子菜单:{}", Context.getLoginUserId(), DateUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"), JSONObject.toJSONString(menuIds));
        return menuMapper.deleteMenuOfIds(menuIds);
    }


    /**
     * 将菜单转为树形结构数据
     *
     * @param menus
     * @return
     */
    private List<MenuVO> menusToTree(List<MenuVO> menus) {
        List<MenuVO> result = new ArrayList<>();
        for (MenuVO menu : menus) {
            boolean add = true;
            for (MenuVO menuVO : menus) {
                if (menuVO.getId().equals(menu.getParentId())) {
                    add = false;
                    if (null == menuVO.getChildren()) {
                        menuVO.setChildren(new ArrayList<>());
                    }
                    menuVO.getChildren().add(menu);
                }
            }
            if (add) {
                result.add(menu);
            }
        }
        return result;
    }
}
