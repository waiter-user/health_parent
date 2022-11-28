package com.java.serviceprovider.mapper;

import com.java.common.pojo.Permission;

import java.util.Set;

public interface PermissionMapper {
    Set<Permission> findByRoleId(int roleId);
}
