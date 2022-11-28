package com.java.serviceprovider.service.impl;

import com.java.service.ReportService;
import com.java.serviceprovider.mapper.MemberMapper;
import com.java.serviceprovider.mapper.OrderMapper;
import com.java.util.DateUtil;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.dubbo.config.annotation.Service;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(timeout = 5000)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Map<String, Object>> getSetmealCounts() {
        List<Map<String, Object>> maps = orderMapper.selectSetmealCounts();
        return maps;
    }

    @Override
    public Map<String, Object> getBusinessData() {
        Map<String, Object> map=new HashedMap<>();
        //获取当前日期
        String today = LocalDate.now().toString("yyyy-MM-dd");
        map.put("reportDate",today);
        //获取今日新增会员数
        Integer todayNewMember = memberMapper.selectMemberCountByDate(today);
        map.put("todayNewMember",todayNewMember);
        //获取总会员数
        Integer total = memberMapper.selectTotalCount();
        map.put("totalMember",total);
        //获取本周新增会员数
        //获取本周一日期
        Date monday = DateUtil.getThisWeekMonday();
        try {
            //获取本周新增会员数
            String weekMonday = DateUtil.parseDate2String(monday);
            Integer weekNewCount = memberMapper.selectCountAfterDate(weekMonday);
            map.put("thisWeekNewMember",weekNewCount);
            //获取本月新增会员数
            Date firstDay4ThisMonth = DateUtil.getFirstDay4ThisMonth();
            String monthFirstDay = DateUtil.parseDate2String(firstDay4ThisMonth);
            Integer monthNewCount = memberMapper.selectCountAfterDate(monthFirstDay);
            map.put("thisMonthNewMember",monthNewCount);
            //获取今日预约数
            long todayOrderNumber = orderMapper.selectCountByOrderDate(today);
            map.put("todayOrderNumber",todayOrderNumber);
            //获取今日到诊数
            long todayVisitsNumber = orderMapper.selectVisitsCountByDate(today);
            map.put("todayVisitsNumber",todayVisitsNumber);
            //获取本周预约数
            long thisWeekOrderNumber = orderMapper.findOrderCountAfterDate(weekMonday);
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);
            //获取本周到诊人数
            long thisWeekVisitsNumber = orderMapper.selectVisitsCountAfterDate(weekMonday);
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            //获取本月预约数
            long thisMonthOrderNumber = orderMapper.findOrderCountAfterDate(monthFirstDay);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            //获取本月到诊人数
            long thisMonthVisitsNumber = orderMapper.selectVisitsCountAfterDate(monthFirstDay);
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
            //获取热门套餐信息
            List<Map<String, Object>> hotSetmeal = orderMapper.findHotSetmeal();
            map.put("hotSetmeal",hotSetmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
