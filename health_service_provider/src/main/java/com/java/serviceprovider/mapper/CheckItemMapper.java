package com.java.serviceprovider.mapper;

import com.github.pagehelper.Page;
import com.java.common.pojo.CheckItem;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface CheckItemMapper {
    //插入一条记录
    void insert(CheckItem checkItem);
    //根据条件做分页查询
    Page<CheckItem> selectByCondition(String queryString);
    //根据id删除检查项
    @Delete("delete from t_checkitem where id=#{id}")
    void deleteById(Integer id);
    //根据检查项id查询中间关系表
    Long findCountByCheckItemId(Integer checkItemId);
    //修改检查项
    void update(CheckItem checkItem);
    //查询所有检查项
    List<CheckItem> selectAll();
    //根据检查组id删除检查项
    List<Integer> selectIdsByGroupId(Integer groupId);
    //根据检查组id查询检查项信息
    CheckItem findCheckItemByCheckgroupId(Integer groupId);
}
