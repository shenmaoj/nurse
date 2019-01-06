package com.cmnt.nurse.controller.sys;

import com.cmnt.nurse.common.ZLRes;
import com.cmnt.nurse.service.sys.MenuService;
import com.cmnt.nurse.vo.sys.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/availableMenu")
    public ZLRes<List<MenuVO>> availableMenu(@RequestBody Map<String, Object> map) {
        return new ZLRes<>(menuService.availableMenu(null));
    }
}
