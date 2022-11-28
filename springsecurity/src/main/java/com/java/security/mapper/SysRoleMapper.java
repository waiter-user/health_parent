package com.java.security.mapper;

import com.java.security.pojo.SysRole;

import java.util.List;

public interface SysRoleMapper {
    //根据用户id查询用户权限
    List<SysRole> selectRoleByUser(Integer userId);
}
