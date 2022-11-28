package com.java.common.vo;

import com.java.common.pojo.CheckGroup;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 封装检查组参数(前台交给后台的数据)
 */
public class CheckGroupVo implements Serializable {
    //检查项id数组
    private Integer[] itemIds;
    private CheckGroup checkgroup;

    public CheckGroupVo() {
    }

    public CheckGroupVo(Integer[] itemIds, CheckGroup checkgroup) {
        this.itemIds = itemIds;
        this.checkgroup = checkgroup;
    }

    public Integer[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(Integer[] itemIds) {
        this.itemIds = itemIds;
    }

    public CheckGroup getCheckgroup() {
        return checkgroup;
    }

    public void setCheckgroup(CheckGroup checkgroup) {
        this.checkgroup = checkgroup;
    }

    @Override
    public String toString() {
        return "CheckGroupVo{" +
                "itemIds=" + Arrays.toString(itemIds) +
                ", checkgroup=" + checkgroup +
                '}';
    }
}
