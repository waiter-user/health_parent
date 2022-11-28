package com.java.service;

import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetMealService {
    void addSetMeal(Map<String,Object> map);
    PageResult queryByPage(QueryPageBean queryPageBean);
    List<Setmeal> findAll();
    //获取套餐详情
    Setmeal findById(Integer id);
}
