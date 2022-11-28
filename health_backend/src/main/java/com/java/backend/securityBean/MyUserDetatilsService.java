package com.java.backend.securityBean;

import com.java.common.pojo.Permission;
import com.java.common.pojo.Role;
import com.java.common.pojo.User;
import com.java.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Component
public class MyUserDetatilsService implements UserDetailsService {
    @Reference(retries = 0)
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户
        User loginUser = userService.findByUsername(username);
        if (null == loginUser) {
            throw new UsernameNotFoundException("用户名错误!");
        }else{
            UserDetails user=null;
            //创建权限集合
            List<GrantedAuthority> list = new ArrayList<>();
            Set<Role> roles = loginUser.getRoles();
            for (Role role : roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getKeyword());
                list.add(authority);
                //获取角色关联权限
                Set<Permission> permissions = role.getPermissions();
                if (null!=permissions && permissions.size()>0) {
                    for (Permission permission : permissions) {
                        authority=new SimpleGrantedAuthority(permission.getKeyword());
                        list.add(authority);
                    }
                }
            }
            user=new org.springframework.security.core.userdetails.User(loginUser.getUsername(),loginUser.getPassword(),list);
            return user;
        }
    }
}
