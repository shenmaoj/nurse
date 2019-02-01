package com.cmnt.nurse.service.sys.impl;

import com.alibaba.fastjson.JSONObject;
import com.cmnt.nurse.common.Context;
import com.cmnt.nurse.common.datatables.DataTablesRequest;
import com.cmnt.nurse.common.datatables.DataTablesResponse;
import com.cmnt.nurse.common.utils.BeanCopyUtils;
import com.cmnt.nurse.common.utils.DateUtils;
import com.cmnt.nurse.common.utils.StringUtils;
import com.cmnt.nurse.dao.sys.SysMenuMapper;
import com.cmnt.nurse.model.sys.SysMenu;
import com.cmnt.nurse.service.sys.MenuService;
import com.cmnt.nurse.vo.sys.MenuVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<Map<String, Object>> menuToJsTree() {
        List<Map<String, Object>> menusTree = new ArrayList<>();
        Map<String, Object> state = new HashMap<>();
        state.put("opened",true);
        state.put("selected",true);
        Map<String, Object> rootMap = new HashMap<>();
        rootMap.put("id", "root_999");
        rootMap.put("text", "所有");
        rootMap.put("parent", "#");
        rootMap.put("state",state);
        menusTree.add(rootMap);
        Map<String, Object> childState = new HashMap<>();
        childState.put("opened",true);
        childState.put("selected",false);
        List<MenuVO> menus = menuMapper.findMenuOfIds(null);
        if (null != menus && !menus.isEmpty()) {
            menusTree.addAll(menus.stream().map(m -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", m.getId());
                map.put("parent", m.getParentId());
                map.put("text", m.getName());
                map.put("level",m.getLevel());
                map.put("state", childState);
                return map;
            }).collect(Collectors.toList()));
        }
        return menusTree;
    }

    /**
     * 获取子菜单
     * @param dataTablesRequest
     * @param map
     * @return
     */
    @Override
    public DataTablesResponse menuOfChildren(DataTablesRequest dataTablesRequest, Map<String, Object> map) {
        PageHelper.startPage(dataTablesRequest.getCurrentPage(), dataTablesRequest.getPageSize());
        List<MenuVO> list = menuMapper.findMenusIdOfParentId(map);
        return new DataTablesResponse((Page<MenuVO>)list ,dataTablesRequest.getDraw());
    }

    /**
     * 保存菜单信息
     * @param menuVO
     * @return
     */
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

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Override
    public int deleteMenu(String id) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("parentId", id);
        /* 查找子菜单id */
        List<String> menuIds = menuMapper.findMenusIdOfParentId(map).stream().map(MenuVO::getId).collect(Collectors.toList());
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


    @Override
    public Boolean validCode(Map<String, Object> map) {
        return menuMapper.findMenuByCode(map) == null;
    }

    @Override
    public MenuVO findOneOfId(String id) {
        SysMenu menu = menuMapper.selectByPrimaryKey(id);
        MenuVO menuVO = new MenuVO();
        BeanCopyUtils.copyProperties(menu, menuVO);
        return menuVO;
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
