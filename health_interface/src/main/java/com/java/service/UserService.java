package com.java.service;

import com.java.common.pojo.User;

public interface UserService {
    //获取登录用户
    User findByUsername(String username);
}
