package com.java.mobile.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.constant.RedisConstant;
import com.java.common.entity.Result;
import com.java.common.pojo.Member;
import com.java.redis.RedisOptBean;
import com.java.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/member")
@Api(tags = "会员登录后台处理接口")
public class MemberController {
    @Reference(retries = 0)
    private MemberService memberService;
    @Autowired
    private RedisOptBean redisOptBean;

    //使用手机号和验证码登录
    @PostMapping("/login")
    @ApiOperation("会员登录处理方法")
    public Result login(HttpServletResponse response, @RequestBody Map<String, String> map) {
        String telephone =String.valueOf(map.get("telephone"));
        String validateCode =String.valueOf(map.get("validateCode")) ;
        //reds中获取正确的验证码
        String key = telephone + "-" + RedisConstant.SENDTYPE_LOGIN;
        String codeInRedis =String.valueOf(redisOptBean.get(key));
        //校验手机验证码
        if (validateCode == null || codeInRedis == null || !codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //验证码输入正确
        //判断当前用户是否为会员
        Member member = memberService.findByTelephone(telephone);
        if (member == null) {
            //非会员，注册会员
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            //设置会员初始密码
            member.setPassword("123");
            memberService.add(member);
        }
        //登录成功，手机号写入cookie
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setPath("/");
        //以秒为单位
        cookie.setMaxAge(60 * 60 * 24 * 10);
        response.addCookie(cookie);
        //手机号缓存到redis中
        //保持同步（reds中存储时间与cookie存储时间相同,10天有效）
        redisOptBean.set(telephone, member, 60 * 60 * 24 * 10);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }


    @GetMapping("/getInfo")
    @ApiOperation("获取会员信息")
    public Result getInfo(HttpServletRequest request){
        //获取cookie中存储的手机号
        Cookie[] cookies = request.getCookies();
        Cookie ck=null;
        for (Cookie cookie : cookies) {
            if("login_member_telephone".equals(cookie.getName())){
                ck=cookie;
                break;
            }
        }
        String phone = ck.getValue();
        Member member =(Member) redisOptBean.get(phone);
        return new Result(true,"获取会员信息成功！",member);
    }
}
