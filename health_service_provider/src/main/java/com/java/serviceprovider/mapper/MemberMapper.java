package com.java.serviceprovider.mapper;

import com.java.common.pojo.Member;

public interface MemberMapper {
    //根据手机号查询会员
    Member findByTelephone(String telephone);
    //添加会员
    void add(Member member);
    //根据月份查询会员人数
    long selectByMonth(String monthStr);
    //获取今日新增会员数
    Integer selectMemberCountByDate(String date);
    //查询总会员数
    Integer selectTotalCount();
    //查询指定日期后的会员数
    Integer selectCountAfterDate(String date);
}
