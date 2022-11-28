package com.java.backend.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.entity.Result;
import com.java.common.pojo.CheckItem;
import com.java.service.CheckItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
@Api(tags = "检查项后端管理端口")
public class CheckItemController {
    //注入service接口的代理对象
    @Reference(retries = 0)
    private CheckItemService checkItemService;
    //新增
    @PostMapping("/add")
    @ApiOperation(value = "新增检查项")
    public Result add(@RequestBody @ApiParam(name = "checkItem",value = "新增的检查项对象") CheckItem checkItem){
        checkItemService.add(checkItem);
        Result result=new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        return result;
    }

    @PostMapping("/findPage")
    @ApiOperation(value = "分页查询检查项")
    public Result findPage(@RequestBody @ApiParam(name = "queryPageBean",value = "分页查询检查项对象") QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.queryByPage(queryPageBean);
        Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
        return result;
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "删除检查项")
    public Result deleteById(@PathVariable("id") @ApiParam(name = "id",value = "检查项编号") Integer id){
        //如果调用方法抛出了异常，可由统一异常处理器来处理异常，返回异常响应信息
        checkItemService.deleteCheckItemById(id);
        Result result=new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        return result;
    }

    @PutMapping("/edit")
    @ApiOperation(value = "编辑检查项")
    public Result edit(@RequestBody @ApiParam(name = "checkItem",value = "修改的检查项对象") CheckItem checkItem){
        checkItemService.update(checkItem);
        Result result=new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        return result;
    }

    @GetMapping("/findAll")
    @ApiOperation(value = "查询所有检查项")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }


    @GetMapping("/findIdsByCheckGroupId/{groupId}")
    @ApiOperation(value = "根据检查组编号查询检查项")
    public Result findIdsByCheckGroupId(@PathVariable("groupId") @ApiParam(name = "groupId",value = "检查组编号") Integer groupId){
        List<Integer> itemIds = checkItemService.getItemIds(groupId);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,itemIds);
    }
}
