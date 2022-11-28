package com.java.serviceprovider.mapper;

import com.github.pagehelper.Page;
import com.java.common.pojo.Setmeal;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface SetMealMapper {
    //根据条件作分页查询
    Page<Setmeal> selectByCondition(String queryString);
    //插入套餐
    void insert(Map<String, Object> map);
    //插入中间关系表，建立套餐与检查组的关联
    void  setMealGroupAndCheckGroup(Map<String,Object> map);
    List<Setmeal> findAll();
    Setmeal findById(Integer id);

}
