package com.java.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
//加载支持注解
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //内存中配置账号与密码
//        PasswordEncoder pe = passwordEncoder();
//        //链式调用
//        auth.inMemoryAuthentication()
//                .withUser("zhangsan")
//                .password(pe.encode("123456"))
//                .roles("normal");
//        auth.inMemoryAuthentication()
//                .withUser("lisi")
//                .password(pe.encode("123456"))
//                .roles("mormal");
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password(pe.encode("admin"))
//                .roles("admin","normal");
        //基于数据库配置账号与密码
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //指定无权限访问页面，
        http.exceptionHandling().accessDeniedPage("/403.html");
        //自定义安全退出接口路径以及退出后跳转到登录页面
        http.logout().logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/success.html").permitAll();
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                //登陆成功后的跳转路径
                .defaultSuccessUrl("/success")
                .failureForwardUrl("/login.html")
                .and()
                .authorizeRequests()
                .antMatchers("/index", "/login.html").permitAll() //指定登录页面路径
                .antMatchers("/css/**", "/images/**").permitAll()//放行静态资源文件
                .antMatchers("/hello").hasAnyRole("admin", "normal")
                .antMatchers("/helloAdmin").hasRole("admin")
                .anyRequest()
                .authenticated();//其他任何请求都要认证后访问
        http.csrf().disable();//禁用跨站访问
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//创建PasawordEncoder的实现类bean， 实现类是加密算法
        return new BCryptPasswordEncoder();
    }

}
