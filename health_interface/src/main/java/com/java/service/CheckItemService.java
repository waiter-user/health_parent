package com.java.service;

import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.pojo.CheckItem;

import java.util.List;

/**
 * 检查项的业务接口
 */
public interface CheckItemService {
    //新增检查项
    void add(CheckItem checkItem);
    //分页查询
    PageResult queryByPage(QueryPageBean queryPageBean);
    //根据id删除检查项
    void deleteCheckItemById(Integer id);
    //修改检查项
    void update(CheckItem checkItem);
    //查询所有检查项
    List<CheckItem> findAll();
    //获取检查组对应的一组检查项id
    List<Integer> getItemIds(Integer groupId);
}
