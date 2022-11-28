package com.java.backend.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.entity.Result;
import com.java.common.pojo.CheckGroup;
import com.java.redis.RedisConstant;
import com.java.redis.RedisOptBean;
import com.java.service.SetMealService;
import com.java.util.QiniuUtil;
import com.java.util.Stringuntil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@EnableDubbo
public class SetmealController {
    @Autowired
    private QiniuUtil qiniuUtil;
    @Reference
    private SetMealService setMealService;
    @Autowired
   private RedisOptBean redisOptBean;

    @RequestMapping("/upload")
    public Result upload(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String newName = Stringuntil.getNewName(originalFilename);
        try {
            qiniuUtil.uploadToQiniu(multipartFile.getBytes(), newName);
            Result result=new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, newName);
            //将图片名称存储到set集合中
            redisOptBean.sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newName);
            return result;
        } catch (IOException e) {
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL, e.getMessage());
        }
    }
    @PostMapping("/add")
    public Result add(@RequestBody Map<String,Object> map){
        setMealService.addSetMeal(map);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setMealService.queryByPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }
}
