package com.java.backend.controller;

import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.pojo.CheckGroup;
import com.java.service.CheckGroupService;
import com.java.common.vo.CheckGroupVo;
import com.java.common.constant.MessageConstant;
import com.java.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
@Api(tags = "检查组后端管理端口")
public class CheckGroupController {
    @Reference(retries = 0)
    private CheckGroupService checkGroupService;

    @PostMapping("/add")
    @ApiOperation(value = "新增检查组")
    public Result add(@RequestBody @ApiParam(name = "checkGroupVo", value = "新增的检查组对象") CheckGroupVo checkGroupVo) {
        checkGroupService.add(checkGroupVo);
        Result result = new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        return result;
    }

    @PostMapping("/findPage")
    @ApiOperation("查询检查组")
    public Result findPage(@RequestBody @ApiParam(name = "QueryPageBean", value = "查询的检查组对象") QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.pageQuery(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    @PutMapping("/edit")
    @ApiOperation("编辑检查组")
    public Result edit(@RequestBody @ApiParam(name = "checkGroupVo", value = "修改的检查组对象") CheckGroupVo checkGroupVo){
        checkGroupService.editCheckGroup(checkGroupVo);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @GetMapping("/findAll")
    public Result  findAll(){
        List<CheckGroup> list = checkGroupService.getList();
        Result result=new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        return result;
    }
}
