package com.java.serviceprovider.mapper;

import com.java.common.pojo.User;

public interface UserMapper {
    //根据用户名查询用户
    User findByUsername(String username);
}
