//package com.java.security.serviceImpl;
//
//import com.java.security.mapper.SysRoleMapper;
//import com.java.security.mapper.SysUserMapper;
//import com.java.security.pojo.SysRole;
//import com.java.security.pojo.SysUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//@Component
//public class JdbcUserDetatilsServiceImpl implements UserDetailsService {
//    @Autowired
//    private SysUserMapper sysUserMapper;
//    @Autowired
//    private SysRoleMapper sysRoleMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //查询用户
//        SysUser sysUser = sysUserMapper.findByUsername(username);
//        if (null != sysUser) {
//            //该用户存在
//            //查询该用户的权限
//            List<SysRole> roles = sysRoleMapper.selectRoleByUser(sysUser.getId());
//            List<GrantedAuthority> authorities = new ArrayList<>();
//            for (SysRole role : roles) {
//                //权限名
//                String roleName = role.getName();
//                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);
//                authorities.add(authority);
//            }
//            sysUser.setAuthorities(authorities);
//            return sysUser;
//        }
//        return sysUser;
//    }
//}
