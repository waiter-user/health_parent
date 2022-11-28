package com.java.security.mapper;

import com.java.security.pojo.SysUser;

public interface SysUserMapper {
    //新增用户
    Integer insert(SysUser user);
    //根据用户名查询用户
    SysUser findByUsername(String username);
}
