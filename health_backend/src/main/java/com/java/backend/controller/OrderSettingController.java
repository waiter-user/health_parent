package com.java.backend.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.Result;
import com.java.common.pojo.OrderSetting;
import com.java.service.OrderSettingService;
import com.java.util.EasyExcelUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference(retries = 0)
    private OrderSettingService orderSettingService;

    /**
     * Excel文件上传，并解析文件内容保存到数据库
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            //读取Excel文件数据
            InputStream inputStream = excelFile.getInputStream();
            List<OrderSetting> orderSettings = EasyExcelUtil.readExcel(inputStream, OrderSetting.class, 0);
            orderSettingService.add(orderSettings);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
    @GetMapping("/getOrdersettingData/{DateStr}")
    public Result getOrdersettingData(@PathVariable("dateStr") String dateStr){
        try {
            List<Map<String, Integer>> orderSetting = orderSettingService.getOrderSetting(dateStr);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,orderSetting);
        } catch (Exception e) {
            throw new RuntimeException(MessageConstant.ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据指定日期修改可预约人数
     * @param orderSetting
     * @return
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            //预约设置成功
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            //预约设置失败
            throw new RuntimeException(MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
