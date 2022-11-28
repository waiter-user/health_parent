package com.java.serviceprovider.mapper;

import com.java.common.pojo.Order;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    //新增预约记录
    void add(Order order);
    //根据条件 查询预约记录数
    long findByCondition(Order order);
    //根据id查询预约详情
    @MapKey("key")
    Map<String,Object> findByIdsDetail(Integer id);
    //分组查询套餐及其预约数量
    @MapKey("key")
    List<Map<String,Object>> selectSetmealCounts();
    //查询
    long selectCountByOrderDate(String date);
    //统计今日到诊数
    long selectVisitsCountByDate(String date);
    //统计指定日期之后的预约数
    long findOrderCountAfterDate(String date);
    //统计指定日期之后的到诊数
    long selectVisitsCountAfterDate(String date);
    //获取热门套餐信息
    @MapKey("key")
   List<Map<String,Object>> findHotSetmeal();
}
