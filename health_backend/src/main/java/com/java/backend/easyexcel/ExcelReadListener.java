package com.java.backend.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ExcelReadListener extends AnalysisEventListener<Employee> {
    //调用invoke()读取除表头外的每行数据
    @Override
    public void invoke(Employee data, AnalysisContext context) {
        System.out.println(data);
    }

    /**
     *
     * @param headMap 存储sheet表的每个表头索引
     * @param context
     */
    //读取表头数据
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        // super.invokeHeadMap(headMap, context);
        System.out.println(headMap);
    }

    //读取完所有数据后就调用doAfterAllAnalysed
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("读取完成了...");
    }
}
