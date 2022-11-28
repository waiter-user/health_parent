package com.java.security.mapper;

import com.java.security.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    //插入
    void insert(UserInfo userInfo);
    //查询
    UserInfo selectByUsername(String username);
}
