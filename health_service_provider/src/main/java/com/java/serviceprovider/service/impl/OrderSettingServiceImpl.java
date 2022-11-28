package com.java.serviceprovider.service.impl;

import com.java.common.pojo.OrderSetting;
import com.java.service.OrderSettingService;
import com.java.serviceprovider.mapper.OrderSettingMapper;
import com.sun.javafx.collections.MappingChange;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(timeout = 5000)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Override
    public void add(List<OrderSetting> list) {
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                long date = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
                if(date > 0){
                    //已经存在，执行更新操作
                    orderSettingMapper.editNumberByOrderDate(orderSetting);
                }else{
                    //不存在，执行添加操作
                    orderSettingMapper.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map<String, Integer>> getOrderSetting(String dateStr) {
        List<OrderSetting> orderSettings = orderSettingMapper.selectByDateStr(dateStr);
        List<Map<String,Integer>> mapList=new ArrayList<>();
        for (OrderSetting orderSetting : orderSettings) {
            Map<String,Integer> map=new HashedMap<>();
            map.put("date",orderSetting.getOrderDate().getDate());
            map.put("number",orderSetting.getNumber());
            map.put("reservations",orderSetting.getReservations());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingMapper.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingMapper.add(orderSetting);
        }
    }
}
