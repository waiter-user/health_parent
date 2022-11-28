package com.java;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class freemarkerTest {
    @Test
    public void test1(){
        try {
            //1.创建配置类
            Configuration configuration=new Configuration(Configuration.getVersion());
            //2.设置模板所在的目录
            configuration.setDirectoryForTemplateLoading(new File("G:\\ftl"));
            //3.设置字符集
            configuration.setDefaultEncoding("utf-8");
            //4.加载模板
            Template template = configuration.getTemplate("test1.ftl");
            //5.创建数据模型
            Map map=new HashMap();
            map.put("name", "张三");
            map.put("message", "欢迎来到湖北理工！");
            //6.创建Writer对象
            Writer out =new FileWriter(new File("G:\\test1.html"));
            //7.输出
            template.process(map, out);
            //8.关闭Writer对象
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
