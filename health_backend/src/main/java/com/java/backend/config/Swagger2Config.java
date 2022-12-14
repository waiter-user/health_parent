package com.java.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//开启swagger2
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)          //指定Api类型为Swagger2
                .apiInfo(apiInfo())                             //指定文档汇总信息
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.java.backend.controller")) //指定controller包路径
                .paths(PathSelectors.any())                     //指定展示所有controller
                .build();
    }

    private ApiInfo apiInfo() {
        //返回一个apiinfo
        return new ApiInfoBuilder()
                .title("康安体检管理端api接口文档")                                       //文档页标题
                .contact(
                        new Contact("Jackson", "http://softeem.com", "2738469898@qq.com")
                )                                                           // 联系人信息
                .description("api文档描述")          // 描述信息
                .version("1.0.1")              // 文档版本号
                .termsOfServiceUrl("https://www.baidu.com") //服务地址
                .build();
    }
}
