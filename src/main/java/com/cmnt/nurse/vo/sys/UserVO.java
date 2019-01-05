package com.cmnt.nurse.vo.sys;

import lombok.Data;

/**
 * @auther shenmj
 * @create 2019/1/5
 **/
@Data
public class UserVO {
    private String id;

    private String name;

    private String orgId;

    private String fullName;

    private String userPwd;

    private String userType;

    private String status;
}
