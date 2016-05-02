package com.github.lavenderx.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * Created by lavenderx on 2016-04-30.
 */
@Configuration
@ComponentScan('com.github.lavenderx.controller')
@EnableWebMvc
@Import(RootConfig.class)
class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler('/favicon.ico', '/static/**', '/**')
                .addResourceLocations('classpath:/static/')
    }

    @Override
    void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController('/').setViewName('index')
    }
}
