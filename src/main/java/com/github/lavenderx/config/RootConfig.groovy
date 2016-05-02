package com.github.lavenderx.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.annotation.Order

/**
 * Created by lavenderx on 2016-04-30.
 */
@Order(0)
@Configuration
@ComponentScan([
        'com.github.lavenderx.model.dao',
        'com.github.lavenderx.service'
])
@PropertySource([
        'classpath:common-config.properties',
        'classpath:${spring.profiles.active}.properties'
])
class RootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        new PropertySourcesPlaceholderConfigurer()
    }
}
