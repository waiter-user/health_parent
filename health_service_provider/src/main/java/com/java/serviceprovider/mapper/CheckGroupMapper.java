package com.java.serviceprovider.mapper;

import com.github.pagehelper.Page;
import com.java.common.pojo.CheckGroup;
import com.java.common.vo.CheckGroupVo;
import io.lettuce.core.dynamic.annotation.Param;


import java.util.List;

public interface CheckGroupMapper {
    void add(CheckGroup checkGroup);
    //插入中间关系表，建立检查组和检查项的关联
    void setCheckGroupAndCheckItem(@Param("GroupVo") CheckGroupVo checkGroupVo);
    Page<CheckGroup> selectByCondition(String queryString);
    //修改检查组
    void update(CheckGroup checkGroup);
    //删除检查组
    void deleteAssociation(Integer id);
    //查询检查组列表
    List<CheckGroup> selectAll();
    //根据套餐编号查询检查组列表
    List<CheckGroup> selectListBySetmealId(Integer setmealId);

}
