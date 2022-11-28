package com.java.serviceprovider.mapper;

import com.java.common.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingMapper {
    void add(OrderSetting orderSetting);
    //更新可预约人数
    void editNumberByOrderDate(OrderSetting orderSetting);
    //更新已预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);
    long findCountByOrderDate(Date orderDate);
    List<OrderSetting> selectByDateStr(String dateStr);
    //根据预约日期查询预约设置信息
    OrderSetting findByOrderDate(Date orderDate);
}
