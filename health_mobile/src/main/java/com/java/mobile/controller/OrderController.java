package com.java.mobile.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.Result;
import com.java.common.pojo.Order;
import com.java.redis.RedisConstant;
import com.java.redis.RedisOptBean;
import com.java.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 体检预约
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference(retries=0)
    private OrderService orderService;
    @Autowired
    private RedisOptBean redisOptBean;

    /**
     * 体检预约
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map<String,Object> map){
        //获取map中获取用户填写的验证码
        String validateCode = String.valueOf(map.get("validateCode"));
        //校验Redis中存储的验证码与用户填写的验证码比较
        String telephone = String.valueOf(map.get("telephone"));
        //从Redis中获取缓存的验证码，key为手机号+“_"+disConstant.SENDTYPE_ORDER
        String codeInRedis =String.valueOf(redisOptBean.get(telephone + RedisConstant.SENDTYPE_ORDER));
        //验证码错误
        if(codeInRedis == null || !codeInRedis.equals(validateCode) || validateCode== null){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //验证码正确，调用体检预约服务
        map.put("orderType", Order.ORDERTYPE_WEIXIN);
        //调用service
        Result result=orderService.saveOrder(map);
        return result;
    }

    /**
     * 根据id查询预约信息，包括套餐信息和会员信息
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{

            Map map = orderService.findById(id);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
