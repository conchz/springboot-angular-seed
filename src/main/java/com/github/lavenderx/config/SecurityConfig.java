//package com.github.lavenderx.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * Created by lavenderx on 2016-04-28.
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Profile({"dev", "prod"})
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("dianrong")
//                .password("2131f59a-0d21-4562-88d4-13cabb1496cd")
//                .roles("USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .fullyAuthenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf()
//                .disable();
//    }
//}
