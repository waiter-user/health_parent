package com.java.serviceprovider.service.impl;

import com.java.common.pojo.Menu;
import com.java.common.pojo.Permission;
import com.java.common.pojo.Role;
import com.java.common.pojo.User;
import com.java.redis.RedisOptBean;
import com.java.service.UserService;
import com.java.serviceprovider.mapper.MenuMapper;
import com.java.serviceprovider.mapper.PermissionMapper;
import com.java.serviceprovider.mapper.RoleMapper;
import com.java.serviceprovider.mapper.UserMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service(timeout = 5000)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisOptBean redisOptBean;

    @Override
    public User findByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (null == user) {
            //用户不存在
            throw new UsernameNotFoundException("用户不存在!");
        } else {
            Set<Role> roles = roleMapper.findByUserId(user.getId());
            if (null != roles && roles.size() > 0) {
                for (Role role : roles) {
                    //根据角色获取权限
                    Set<Permission> permissions = permissionMapper.findByRoleId(role.getId());
                    if (permissions != null && permissions.size() > 0) {
                        //为角色设置权限
                        role.setPermissions(permissions);
                    }
                    long count = redisOptBean.llen(role.getKeyword());
                    if(count==0){
                        //获取关联菜单
                        //获取角色关联的菜单集合
                        List<Menu> menus = menuMapper.selectByRoleId(role.getId());
                        //从菜单集合获取一级菜单
                        List<Menu> firstMenus = menus.stream().filter(menu -> {
                            return menu.getParentMenuId() == null;
                        }).collect(Collectors.toList());
                        //从菜单集合获取二级菜单
                        for (Menu firstMenu : firstMenus) {
                            List<Menu> secondMenus = menus.stream().filter(menu -> {
                                if (menu.getParentMenuId() == firstMenu.getId()) {
                                    return true;
                                }
                                return false;
                            }).collect(Collectors.toList());
                            //建立一级与二级菜单的关联
                            firstMenu.setChildren(secondMenus);
                            //使用Redis缓存一级菜单,10天有效
                            redisOptBean.rpush(role.getKeyword(),firstMenu,60*60*24*10);
                        }
                    }
                }
            }
            //设置角色与用户关联
            user.setRoles(roles);
        }
        return user;
    }
}
