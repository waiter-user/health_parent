package com.java.mobile.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.constant.RedisConstant;
import com.java.common.entity.Result;
import com.java.redis.RedisOptBean;
import com.java.util.SmsUtil;
import com.java.util.ValidateCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validateCode")
@Api(tags = "发送验证码控制器")
public class ValidateCodeController {

    @Autowired
    private RedisOptBean redisOptBean;

    @PostMapping("/sendMessageForTest")
    @ApiOperation("测试发送验证码")
    public Result sendMessageForTest(@ApiParam(name = "手机号码",value = "telePhone") String telePhone) {
        try {
            Integer i = ValidateCodeUtil.generateValidateCode(6);
            SmsUtil.sendMes(i.toString(),telePhone);
            return new Result(true, "发送验证码短信成功");
        } catch (Exception e) {
            throw new RuntimeException("发送验证码失败！");
        }
    }

    //体检预约时发送手机验证码
    @PostMapping("/sendSmForOrder")
    @ApiOperation("体检预约时发送手机验证码")
    public Result sendSmForOrder(String telephone){
        Integer code = ValidateCodeUtil.generateValidateCode(6);
        try {
            //发送短信
            SmsUtil.sendMes(telephone, code.toString());
            //将验证码存储到Redis中,5分钟有效
            redisOptBean.set(telephone+"-"+ RedisConstant.SENDTYPE_ORDER,code,5*60);
            //验证码发送成功
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            //验证码发送失败
            throw new RuntimeException(MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
    //手机快速登录时发送手机验证码
    @PostMapping("/sendSmForLogin")
    public Result sendSmForLogin(String telephone){

        try {
            //发送短信
            Integer code = ValidateCodeUtil.generateValidateCode(6);//生成6位数字验证码
            System.out.println("发送的手机验证码为：" + code);
            //将生成的验证码缓存到redis
            redisOptBean.set(telephone+"-"+ RedisConstant.SENDTYPE_LOGIN,code,5*60);
        } catch (Exception e) {
            e.printStackTrace();
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //验证码发送成功
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
