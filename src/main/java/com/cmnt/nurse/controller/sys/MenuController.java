package com.cmnt.nurse.controller.sys;

import com.cmnt.nurse.common.ZLRes;
import com.cmnt.nurse.common.datatables.DataTablesRequest;
import com.cmnt.nurse.common.datatables.DataTablesResponse;
import com.cmnt.nurse.common.utils.WebUtils;
import com.cmnt.nurse.service.sys.MenuService;
import com.cmnt.nurse.vo.sys.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Describe
 * @auther shenmj
 * @create 2019/1/7
 **/
@RequestMapping("/sys/menu/")
@RestController
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 左侧菜单
     * @param map
     * @return
     */
    @RequestMapping(value = "availableMenu")
    public ZLRes<List<MenuVO>> availableMenu(@RequestBody Map<String, Object> map) {
        return new ZLRes<>(menuService.availableMenu(null));
    }

    /**
     * 菜单tree
     * @param map
     * @return
     */
    @RequestMapping("jsTree")
    public ZLRes<List<Map<String,Object>>> menuToJsTree(@RequestBody Map<String, Object> map){
        return new ZLRes<>(menuService.menuToJsTree());
    }

    /**
     * 菜单列表
     * @param request
     * @return
     */
    @RequestMapping("of/children")
    public DataTablesResponse menuOfChildren(HttpServletRequest request){
        return menuService.menuOfChildren(new DataTablesRequest(request), WebUtils.getParametersStartingWith(request,null));
    }

    /**
     * 保存菜单
     * @param menuVO
     * @return
     */
    @RequestMapping("save")
    public ZLRes<Integer> save(@RequestBody MenuVO menuVO){
        return new ZLRes<>(menuService.saveMenu(menuVO));
    }

    /**
     * 删除菜单及其子菜单
     * @param id
     * @return
     */
    @DeleteMapping("del/{id}")
    public ZLRes<Integer> del( @PathVariable("id") String id){
        return new ZLRes<>(menuService.deleteMenu(id));
    }

    /**
     * 查询菜单编码是否存在
     * @param map（code：菜单编码；id：菜单id）
     * @return
     */
    @RequestMapping("validCode")
    public Boolean validCode(@RequestParam Map<String,Object> map){
        return menuService.validCode(map);
    }

    @RequestMapping("of/{id}")
    public ZLRes<MenuVO> findOneOfId(@PathVariable("id") String id){
        return new ZLRes<>(menuService.findOneOfId(id));
    }
}
