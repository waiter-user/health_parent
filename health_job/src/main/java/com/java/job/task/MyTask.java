package com.java.job.task;

import com.java.redis.RedisConstant;
import com.java.redis.RedisOptBean;
import com.java.util.QiniuUtil;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Component
public class MyTask {
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //当前方法是执行定时任务的，fixedDelay延迟多少毫秒后执行
     //@Scheduled(fixedDelay =3000)
    @Scheduled(cron = "* 31 8 * * 2 ")
    public void  showTime(){
        System.out.println("当前时间"+sdf.format(new Date()));
    }
    @Autowired
    private RedisOptBean redisOptBean;
    @Reference
    private QiniuUtil qiniuUtil;
    @Scheduled(cron = "0 3 22 * * ? ")
    public void clearImgs(){
        Set sdiff = redisOptBean.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //遍历set集合
        for (Object o : sdiff) {
            String img=String.valueOf(o);
            //删除七牛云上的垃圾图片
            qiniuUtil.deleteFromQiniu(img);
            //从Redis上删除图片
            redisOptBean.setRemove(RedisConstant.SETMEAL_PIC_RESOURCES,img);
        }
    }
}
