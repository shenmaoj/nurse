package com.cmnt.nurse.service.sys;


import com.cmnt.nurse.model.sys.SysUser;

/**
 * Created by shenmj on 2019/1/1.
 */
public interface UserService {

    SysUser findUserByName(String userName);
}
