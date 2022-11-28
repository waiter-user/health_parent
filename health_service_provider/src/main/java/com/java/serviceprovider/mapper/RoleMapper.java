package com.java.serviceprovider.mapper;

import com.java.common.pojo.Role;

import java.util.Set;

public interface RoleMapper {
    //根据用户id查询用户权限
    Set<Role> findByUserId(int id);
}
