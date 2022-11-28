package com.java.service;

import com.java.common.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> list);
    List<Map<String,Integer>> getOrderSetting(String dateStr);
    //根据日期设置可预约人数
    void editNumberByDate(OrderSetting orderSetting);
}
