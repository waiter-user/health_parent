package com.java.security.securityBean;

import com.java.security.mapper.UserInfoMapper;
import com.java.security.pojo.UserInfo;
import com.java.security.serviceImpl.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 当springsecurity执行认证时，会自动调用loadUserByUsername，springsecurity会让我们在登录页面上进行认证
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = null;
        if (username != null) {
            //查询数据库，获取用户信息
            UserInfo userInfo = userInfoService.findByUsername(username);
            //创建权限集合
            List<SimpleGrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority("ROLE_" + userInfo.getRole()));
            user = new User(userInfo.getUsername(), userInfo.getPassword(), list);
        } else {
            throw new RuntimeException("用户为空!");
        }
        return user;
    }
}
