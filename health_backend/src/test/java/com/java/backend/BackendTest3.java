package com.java.backend;

import com.java.util.JodaTimeUtil;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class BackendTest3 {
    @Test
    public void test1() {
        LocalDate localDate = LocalDate.now();
        //获取前两天日期
        LocalDate beforeDate = localDate.minusDays(2);
        System.out.println(beforeDate.toString("yyyy-MM-dd"));
        //获取两天后的日期
        LocalDate afterDate = localDate.plusDays(2);
        System.out.println(afterDate.toString("yyyy-MM-dd"));
    }

    @Test
    public void test2() {
        LocalDate localDate = LocalDate.now();
        //获取前6月
        LocalDate beforeDate = localDate.minusMonths(6);
        System.out.println(beforeDate.toString("yyyy-MM-dd"));
        //获取后六月
        LocalDate afterDate = localDate.plusMonths(6);
        System.out.println(afterDate.toString("yyyy-MM-dd"));
    }

    @Test
    public void test3() {
        List<String> months = JodaTimeUtil.getBeforeMonths(6);
        for (String month : months) {
            System.out.println(month);
        }
    }

    @Test
    public void test4(){
        ClassPathResource classPathResource=new ClassPathResource("static/templates/report_template.xlsx");
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(classPathResource.toString());
        System.out.println(inputStream);
    }

    @Test
    public void test5() {
        ClassPathResource classPathResource = new ClassPathResource("static/templates/report_template.xlsx");
        try {
            InputStream is = classPathResource.getInputStream();
            byte[] b=new byte[1024];
            int len=0;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
