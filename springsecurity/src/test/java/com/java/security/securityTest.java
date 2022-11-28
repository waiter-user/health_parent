package com.java.security;

import com.java.security.pojo.UserInfo;
import com.java.security.serviceImpl.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
@SpringBootTest
public class securityTest {
    @Autowired
   private UserInfoService userInfoService;
    @Autowired
    private PasswordEncoder pe;
    @Test
    public void addUser(){
        UserInfo userInfo=new UserInfo();
        userInfo.setUsername("zs");
        userInfo.setPassword(pe.encode("123456"));
        userInfo.setRole("normal");
        userInfoService.add(userInfo);
        userInfo=new UserInfo();
        userInfo.setUsername("admin");
        System.out.println("加密的密码为:"+pe.encode("admin"));
        userInfo.setPassword(pe.encode("admin"));
        userInfo.setRole("admin");
        userInfoService.add(userInfo);
    }
}
