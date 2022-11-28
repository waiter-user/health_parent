package com.java.serviceprovider.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.pojo.CheckGroup;
import com.java.service.CheckGroupService;
import com.java.serviceprovider.mapper.CheckGroupMapper;
import com.java.common.vo.CheckGroupVo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(timeout = 5000)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    CheckGroupMapper checkGroupMapper;
    @Override
    public void add(CheckGroupVo checkGroupVo) {
        CheckGroup checkgroup = checkGroupVo.getCheckgroup();
        checkGroupMapper.add(checkgroup);
        checkGroupMapper.setCheckGroupAndCheckItem(checkGroupVo);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupMapper.selectByCondition(queryPageBean.getQueryString());
        PageResult pageResult=new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public void editCheckGroup(CheckGroupVo checkGroupVo) {
        CheckGroup checkgroup = checkGroupVo.getCheckgroup();
        //修改检查组的基本信息
        checkGroupMapper.update(checkgroup);
        //闪烁出检查组id删除的记录
        checkGroupMapper.deleteAssociation(checkgroup.getId());
        //建立检查组与检查项关联
        checkGroupMapper.setCheckGroupAndCheckItem(checkGroupVo);
    }

    @Override
    public List<CheckGroup> getList() {
        List<CheckGroup> checkGroups = checkGroupMapper.selectAll();
        return null;
    }
}
