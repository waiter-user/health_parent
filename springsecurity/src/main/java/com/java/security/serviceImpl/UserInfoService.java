package com.java.security.serviceImpl;

import com.java.security.mapper.UserInfoMapper;
import com.java.security.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public void add(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    public UserInfo findByUsername(String username){
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        return userInfo;
    }
}
