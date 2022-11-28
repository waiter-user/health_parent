package com.java.backend.controller;

import com.java.common.constant.MessageConstant;
import com.java.common.entity.Result;
import com.java.service.MemberService;
import com.java.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
@Api("生成报表操作")
public class ReportController {
    @Reference(retries = 0)
    private MemberService memberService;
    @Reference(retries = 0)
    private ReportService reportService;

    @GetMapping("/getMemberReport")
    @ApiOperation("获取注册会员用户数据")
    public Result getMemberReport() {
        Map<String, Object> data = memberService.getMemberLineDate();
        return new Result(true, "获取会员数量折线图数据成功!", data);
    }

    @GetMapping("/getPie")
    @ApiOperation("获取数据占比的饼图")
    public Result getPie() {
        List<Map<String, Object>> setmealCounts = reportService.getSetmealCounts();
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, setmealCounts);
    }

    @GetMapping("/getBusinessReportData")
    @ApiOperation("获取运营报告数据")
    public Result getBusinessReportData() {
        Map<String, Object> businessData = reportService.getBusinessData();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, businessData);
    }

    /**
     * 导出Excel报表
     *
     * @return
     */
    @GetMapping("/exportBusinessReport")
    @ApiOperation("导出运营数据到Excel")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //远程调用报表服务获取报表数据
            Map<String, Object> result = reportService.getBusinessData();

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = result.get("reportDate").toString();
            Integer todayNewMember = Integer.valueOf(result.get("todayNewMember").toString());
            Integer totalMember = Integer.valueOf(result.get("totalMember").toString());
            Integer thisWeekNewMember = Integer.valueOf(result.get("thisWeekNewMember").toString());
            Integer thisMonthNewMember = Integer.valueOf(result.get("thisMonthNewMember").toString());
            Integer todayOrderNumber = Integer.valueOf(result.get("todayOrderNumber").toString());
            Integer thisWeekOrderNumber = Integer.valueOf(result.get("thisWeekOrderNumber").toString());
            Integer thisMonthOrderNumber = Integer.valueOf(result.get("thisMonthOrderNumber").toString());
            Integer todayVisitsNumber = Integer.valueOf(result.get("todayVisitsNumber").toString());
            Integer thisWeekVisitsNumber = Integer.valueOf(result.get("thisWeekVisitsNumber").toString());
            Integer thisMonthVisitsNumber = Integer.valueOf(result.get("thisMonthVisitsNumber").toString());
            List<Map<String, Object>> hotSetmeal = (List<Map<String, Object>>) result.get("hotSetmeal");

            //获得Excel模板文件绝对路径
            //String temlateRealPath = request.getSession().getServletContext().getRealPath("template") +
            //File.separator + "report_template.xlsx";
            ClassPathResource classPathResource = new ClassPathResource("/static/templates/report_template.xlsx");
            InputStream inputStream = classPathResource.getInputStream();
            //读取模板文件创建Excel表格对象,获取代表Excel文件的XSSFWorkbook对象
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            //获取表的第三行
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map<String, Object> map : hotSetmeal) {//热门套餐
                String name = map.get("name").toString();
                long setmeal_count = Long.valueOf(map.get("setmeal_count").toString());
                //精确小数类型
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue() * 100 + "%");//占比
            }

            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            //设置响应内容类型（浏览器可解析的与后缀名有关的类型）
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            //设置响应头（report.xlsx：初始文件名；attachment：弹出附件下载框）
            response.setHeader("Content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
