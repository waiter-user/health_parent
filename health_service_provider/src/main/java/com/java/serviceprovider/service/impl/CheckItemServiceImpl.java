package com.java.serviceprovider.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.java.common.constant.MessageConstant;
import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.pojo.CheckItem;
import com.java.service.CheckItemService;
import com.java.serviceprovider.mapper.CheckItemMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(timeout = 5000)
//事务管理
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckItem checkItem) {
        checkItemMapper.insert(checkItem);
    }

    @Override
    public PageResult queryByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemMapper.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteCheckItemById(Integer id) {
        Long count = checkItemMapper.findCountByCheckItemId(id);
        if (count > 0) {
            //当前检查项被引用，不能被删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        //删除检查项
        checkItemMapper.deleteById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        try {
            checkItemMapper.update(checkItem);
        }catch(Exception e){
            throw new RuntimeException(MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItems = checkItemMapper.selectAll();
        return checkItems;
    }

    @Override
    public List<Integer> getItemIds(Integer groupId) {
        List<Integer> ids = checkItemMapper.selectIdsByGroupId(groupId);
        return ids;
    }
}
