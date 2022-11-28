package com.java.security.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SpringSecurityController {
    @GetMapping("/hello")
   // @PreAuthorize(value = "hasAnyRole('admin','normal')")
    public String hello() {
        return "hello SpringSecurity!可由admin与normal访问";
    }

    @GetMapping("/helloAdmin")
    //@PreAuthorize(value = "hasAnyRole('admin')")
    public String helloAdimn() {
        return "hello SpringSecurity!可由admin访问";
    }

    @GetMapping("/success")
    public String success() {
            return "forward:/success.html";
    }
}
