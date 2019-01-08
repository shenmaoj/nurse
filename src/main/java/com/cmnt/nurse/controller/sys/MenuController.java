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

    @RequestMapping(value = "availableMenu")
    public ZLRes<List<MenuVO>> availableMenu(@RequestBody Map<String, Object> map) {
        return new ZLRes<>(menuService.availableMenu(null));
    }

    @RequestMapping("jsTree")
    public ZLRes<List<Map<String,Object>>> menuToJsTree(@RequestBody Map<String, Object> map){
        return new ZLRes<>(menuService.menuToJsTree());
    }

    @RequestMapping("of/children")
    public DataTablesResponse menuOfChildren(HttpServletRequest request){
        return menuService.menuOfChildren(new DataTablesRequest(request), WebUtils.getParametersStartingWith(request,null));
    }
}
