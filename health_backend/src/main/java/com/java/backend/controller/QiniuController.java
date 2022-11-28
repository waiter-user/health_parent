package com.java.backend.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.Result;
import com.java.util.QiniuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/qiniu")
@Api(tags = "七牛云文件上传与删除端口")
public class QiniuController {
    @Autowired
    private QiniuUtil qiniuUtil;
    @PostMapping("upload")
    @ApiOperation(value = "测试文件上传的接口")
    public Result upload(@RequestParam("multipartFile") @ApiParam(name = "multipartFile",value = "富文本文件") MultipartFile multipartFile){
        byte[] bytes = new byte[0];
        try {
            bytes = multipartFile.getBytes();
            String filename = multipartFile.getOriginalFilename();
            qiniuUtil.uploadToQiniu(bytes,filename);
            Result result = new Result(true, MessageConstant.UPLOAD_SUCCESS,filename);
            return result;
        } catch (IOException e) {
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL,e.getMessage());
        }
    }

    @DeleteMapping("deleteImg")
    @ApiOperation(value = "测试文件删除的接口")
    public Result deleteImg(@ApiParam(name = "文件名",value = "filename") String filename){
        qiniuUtil.deleteFromQiniu(filename);
        return new Result(true,"删除文件成功!");
    }
}
