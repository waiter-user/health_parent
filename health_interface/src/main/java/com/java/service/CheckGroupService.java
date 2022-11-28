package com.java.service;


import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.pojo.CheckGroup;
import com.java.common.vo.CheckGroupVo;

import java.util.List;

public interface CheckGroupService {
    //新增检查组
    void add(CheckGroupVo checkGroupVo);
    //分页查询检查组
    PageResult pageQuery(QueryPageBean queryPageBean);
    //修改检查组
    void editCheckGroup(CheckGroupVo checkGroupVo);
    //获取检查组列表
    List<CheckGroup> getList();
}
