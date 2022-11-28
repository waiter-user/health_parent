package com.java.serviceprovider.service.impl;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.Result;
import com.java.common.pojo.Member;
import com.java.common.pojo.Order;
import com.java.common.pojo.OrderSetting;
import com.java.service.OrderService;
import com.java.serviceprovider.mapper.MemberMapper;
import com.java.serviceprovider.mapper.OrderMapper;
import com.java.serviceprovider.mapper.OrderSettingMapper;
import com.java.util.DateUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * 体检预约服务
 */
@Service(timeout = 5000)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Result saveOrder(Map<String, Object> map) {
        //检查当前日期是否进行了预约设置
        String orderDate = (String) map.get("orderDate");
        try{
            Date date = DateUtil.parseString2Date(orderDate);
            OrderSetting orderSetting = orderSettingMapper.findByOrderDate(date);
            //检查用户所选日期是否可预约
            if(orderSetting == null){
                return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
            //检查预约日期是否预约已满
            int number = orderSetting.getNumber();//可预约人数
            int reservations = orderSetting.getReservations();//已预约人数
            if(reservations >= number){
                //预约已满，不能预约
                return new Result(false,MessageConstant.ORDER_FULL);
            }
            //检查当前用户是否为会员，根据手机号判断
            String telephone = (String) map.get("telephone");
            Member member = memberMapper.findByTelephone(telephone);
            //防止重复预约
            if(member != null){
                Integer memberId = member.getId();
                int setmealId = Integer.parseInt(map.get("setmealId").toString());
                //封装order对象，最为查询条件
                Order order = new Order(memberId,date,null,null,setmealId);
                //根据id获取对应的
                long count = orderMapper.findByCondition(order);
                if(count > 0){
                    //已经完成了预约，不能重复预约
                    return new Result(false, MessageConstant.HAS_ORDERED);
                }
            }else{
                //当前用户不是会员，需要添加到会员表
                member = new Member();
                member.setName((String) map.get("name"));
                member.setPhoneNumber(telephone);
                member.setIdCard((String) map.get("idCard"));
                member.setSex((String) map.get("sex"));
                member.setRegTime(new Date());
                memberMapper.add(member);
            }
            //可以预约，设置预约人数加一
            orderSetting.setReservations(orderSetting.getReservations()+1);
            orderSettingMapper.editReservationsByOrderDate(orderSetting);
            //保存预约信息到预约表
            Order order = new Order(member.getId(),
                    date,
                    (String)map.get("orderType"),
                    Order.ORDERSTATUS_NO,
                    Integer.parseInt(map.get("setmealId").toString()));
            orderMapper.add(order);
            return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
        }catch(Exception e){
            e.printStackTrace();
            return new Result(false,"预约失败!");
        }
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        Map map = orderMapper.findByIdsDetail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            try {
                map.put("orderDate",DateUtil.parseDate2String(orderDate));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
