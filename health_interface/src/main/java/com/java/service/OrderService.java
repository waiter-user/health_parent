package com.java.service;

import com.java.common.entity.Result;

import java.util.Map;

/**
 * 体检预约服务接口
 */
public interface OrderService {
    //添加体检预约信息
    Result saveOrder(Map<String,Object> map);
    //根据id查询预约信息，包括体检人信息、套餐信息
    Map<String,Object> findById(Integer id);
}
