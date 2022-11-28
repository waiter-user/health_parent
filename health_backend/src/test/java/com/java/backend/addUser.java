package com.java.backend;

import com.java.backend.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class addUser {
    @Autowired
    private SecurityConfig securityConfig;
    public void test1(){
        String admin = securityConfig.passwordEncoder().encode("admin");
        System.out.println();
    }
}
