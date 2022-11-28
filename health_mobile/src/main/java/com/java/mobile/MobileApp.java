package com.java.mobile;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.java.util","com.java.mobile","com.java.redis"},exclude = {SecurityAutoConfiguration.class})
@EnableDubbo
public class MobileApp {
    public static void main(String[] args) {
        SpringApplication.run(MobileApp.class,args);
    }
}
