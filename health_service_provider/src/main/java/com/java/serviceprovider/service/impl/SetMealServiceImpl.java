package com.java.serviceprovider.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.java.common.constant.MessageConstant;
import com.java.common.entity.PageResult;
import com.java.common.entity.QueryPageBean;
import com.java.common.entity.Result;
import com.java.common.pojo.Setmeal;
import com.java.redis.RedisConstant;
import com.java.redis.RedisOptBean;
import com.java.service.SetMealService;
import com.java.serviceprovider.mapper.SetMealMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Service(timeout = 5000)
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
   private RedisOptBean redisOptBean;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${outputPath}")
    private String outputPath;
    @Override
    public void addSetMeal(Map<String, Object> map) {
        LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) map.get("setmeal");
        redisOptBean.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,String.valueOf(linkedHashMap.get("img")));
        setMealMapper.insert(linkedHashMap);
        setMealMapper.setMealGroupAndCheckGroup(map);
        //将套餐图片存储到redis的set集合中
        redisOptBean.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,String.valueOf(linkedHashMap.get("img")));
        //获取套餐列表
       List<Setmeal> setmeals= setMealMapper.findAll();
       //生成套餐列表页面
        this.generateSetmealListHtml(setmeals);
        //生成套餐详情静态页面
        this.generateSetmealDetailHtml(setmeals);
    }

    /**
     * 生成套餐列表静态页面
     * @param setmeals
     */
    private void generateSetmealListHtml(List<Setmeal> setmeals){
        Map dataMap=new HashedMap();
        dataMap.put("setmealList",setmeals);
        generateHtml("m_setmeal.ftl","m_setmeal.html",dataMap);
    }

    /**
     * 生成套餐详情静态页面
     * @param setmeals
     */
    private void generateSetmealDetailHtml(List<Setmeal> setmeals){
        for (Setmeal setmeal : setmeals) {
            Integer id = setmeal.getId();
            //获取套餐详情
          Setmeal details=  setMealMapper.findById(id);
          Map dataMap=new HashedMap();
          dataMap.put("setmeal",details);
          generateHtml("m_setmeal_detail.ftl","m_setmeal_detail_"+id+".html",dataMap);
        }
    }
    /**
     * 通用的生成静态页面的方法
     * @param templateName 模板名称
     * @param htmlPage 页面名称
     * @param dataMap
     */
    private void generateHtml(String templateName,String htmlPage,Map<String,Object> dataMap){
        Configuration config = freeMarkerConfigurer.getConfiguration();
        Writer out=null;
        try {
            //加载模板
            Template template = config.getTemplate(templateName);
            //创建Writer对象
            out=new BufferedWriter(new FileWriter(new File(outputPath+ File.separator+htmlPage)));
            //输出
            template.process(dataMap,out);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public PageResult queryByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setMealMapper.selectByCondition(queryPageBean.getQueryString());
        PageResult pageResult=new PageResult(page.getTotal(),page.getResult());
        return pageResult;
    }

    @Override
    public List<Setmeal> findAll() {
        return setMealMapper.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealMapper.findById(id);
    }
}
