package com.java.backend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.java.util","com.java.backend","com.java.redis"})
@EnableDubbo
public class backendApp {
    public static void main(String[] args) {
        SpringApplication.run(backendApp.class,args);
    }
}
