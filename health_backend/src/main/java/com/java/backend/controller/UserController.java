package com.java.backend.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.Result;
import com.java.redis.RedisOptBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
@Api(tags = "显示用户菜单与用户名操作")
public class UserController {
    @Autowired
    private RedisOptBean redisOptBean;
    @GetMapping("/getMenus")
    @ApiOperation("获取用户菜单")
    public Result getMenus(){
        //获取当前用户（认证主体）
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        Stream<String> roleStream = authorities.stream().filter(g ->
                     g.getAuthority().startsWith("ROLE_")
                ).map(g -> g.getAuthority());
//        List<String> list=new ArrayList<>();
//        roleStream.forEach(e-> list.add(e));
        List<String> list=roleStream.collect(Collectors.toList());
        //获取菜单在Redis中存储的key
        String key = list.get(0);
        //获取Redis中存储的一级菜单的集合
        List menus = redisOptBean.lrange(key, 0, -1);
        return new Result(true, MessageConstant.GET_MENU_SUCCESS,menus);
    }
    @GetMapping("/getUsername")
    @ApiOperation("获取用户名")
    public Result getUsername(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,username);
    }
}
