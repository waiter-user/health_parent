package com.java.backend;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.java.backend.easyexcel.Employee;
import com.java.backend.easyexcel.ExcelReadListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BackendTest2 {
    @Autowired
    private ExcelReadListener excelReadListener;
    @Test
    public void testReadExcel(){
        String pathName="E:\\Idea\\IdeaProjects\\员工信息表.xls";
        EasyExcel.read(pathName, Employee.class,excelReadListener).sheet().doRead();
    }
    //写入到Excel文件的一个Sheet中
    @Test
    public void writeToExcel() {
        String filePath = "E:\\Idea\\IdeaProjects\\员工信息表.xls";
        List<Employee> list=new ArrayList<>();
        list.add(new Employee(1,"Jack","男",26,8500.0));
        list.add(new Employee(2,"Rose","女",24,8600.5));
        EasyExcel.write(filePath,Employee.class).sheet("员工数据表").doWrite(list);
    }
}
