package com.java.mobile.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.Result;
import com.java.common.pojo.Setmeal;
import com.java.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Api(tags = "移动端套餐处理")
public class SetmealController {
    @Reference(retries = 0)
    private SetMealService setMealService;

    //获取所有套餐信息
    @PostMapping("/getSetmeal")
    @ApiOperation("获取套餐列表")
    public Result getSetmeal() {
        List<Setmeal> list = setMealService.findAll();
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, list);
    }

    //根据id查询套餐信息
    @PostMapping("/findById/{id}")
            @ApiOperation("查询套餐详情")
    public Result findById(@PathVariable("id") Integer id) {
        Setmeal setmeal = setMealService.findById(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}
