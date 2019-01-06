package com.cmnt.nurse.service.sys.impl;

import com.cmnt.nurse.common.enums.UserStatusEnum;
import com.cmnt.nurse.common.exception.BusinessException;
import com.cmnt.nurse.common.utils.Constants;
import com.cmnt.nurse.dao.sys.SysUserMapper;
import com.cmnt.nurse.model.sys.SysUser;
import com.cmnt.nurse.service.sys.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther shenj
 * @create 2019/1/1
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    SysUserMapper userMapper;

    @Override
    public SysUser findUserByName(String userName) {
        Map<String,Object> map = new HashMap<>();
        map.put("username",userName);
        map.put("status", UserStatusEnum.INVALID.getValue());
        SysUser user = userMapper.findUserByName(map);
        if (null == user){
            throw BusinessException.creat("90001");
        }
        if( UserStatusEnum.FREEZE.getValue().equals(user.getStatus())){
            throw BusinessException.creat("90002");
        }
        return user;
    }
}
