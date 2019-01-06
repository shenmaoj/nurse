package com.cmnt.nurse.vo.sys;

import lombok.Data;

import java.util.List;

/**
 * @Describe
 * @auther shenmj
 * @create 2019/1/6
 **/
@Data
public class MenuVO {
    private String id;

    private String parentId;

    private String name;

    private String code;

    private String icon;

    private String url;

    private Integer level;

    private Integer menuOrder;

    private String authz;

    private List<MenuVO> children;
}
