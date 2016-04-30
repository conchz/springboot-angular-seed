package com.github.lavenderx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;

/**
 * Created by lavenderx on 2016-04-30.
 */
@Order(0)
@Configuration
@PropertySource({
        "classpath:common-config.properties",
        "classpath:${spring.profiles.active}.properties"
})
public class RootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
