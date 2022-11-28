package com.java.service;

import com.java.common.pojo.Member;

import java.util.Map;

public interface MemberService {
    //根据手机号查询会员信息
    Member findByTelephone(String telephone);
    //添加会员
    void add(Member member);
    //获取会员数量的折线图数据
    Map<String,Object> getMemberLineDate();
}
