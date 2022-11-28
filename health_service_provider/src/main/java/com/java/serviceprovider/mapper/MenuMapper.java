package com.java.serviceprovider.mapper;

import com.java.common.pojo.Menu;

import java.util.List;
import java.util.Set;

/**
 * 操作菜单接口
 */
public interface MenuMapper {
    //根据角色id查询菜单
    List<Menu> selectByRoleId(Integer id);

}
