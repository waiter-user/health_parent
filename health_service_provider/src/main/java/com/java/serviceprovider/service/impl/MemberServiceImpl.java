package com.java.serviceprovider.service.impl;

import com.java.common.pojo.Member;
import com.java.service.MemberService;
import com.java.serviceprovider.mapper.MemberMapper;
import com.java.util.JodaTimeUtil;
import com.java.util.MD5;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(timeout = 5000)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public Member findByTelephone(String telephone) {
        //调用mapper中的方法
        Member member = memberMapper.findByTelephone(telephone);
        return member;
    }

    @Override
    public void add(Member member) {
        //调用mapper方法插入
        String password = member.getPassword();
        if(null!=password){
            password= MD5.encrypt(password);
            member.setPassword(password);
        }
        memberMapper.add(member);
    }

    @Override
    public Map<String, Object> getMemberLineDate() {
        Map<String, Object> map=new HashedMap<>();
        //获取过去半年的日期
        List<String> beforeMonths = JodaTimeUtil.getBeforeMonths(6);
        map.put("months",beforeMonths);
        List<Long> list=new ArrayList<>();
        for (String beforeMonth : beforeMonths) {
            long count = memberMapper.selectByMonth(beforeMonth);
            list.add(count);
        }
        map.put("memberCount",list);
        return map;
    }
}
