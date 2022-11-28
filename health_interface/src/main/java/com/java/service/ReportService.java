package com.java.service;

import java.util.List;
import java.util.Map;

/**
 * 统计分析接口
 */
public interface ReportService {
    //获取套餐预约占比饼图数据
    List<Map<String,Object>> getSetmealCounts();
    //获取运营数据
    Map<String,Object> getBusinessData();
}
