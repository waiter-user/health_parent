package com.java.serviceprovider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.java.serviceprovider","com.java.redis"})
@EnableDubbo
@MapperScan("com.java.serviceprovider.mapper")
public class providerApp {
    public static void main(String[] args) {
        SpringApplication.run(providerApp.class,args);
    }
}
