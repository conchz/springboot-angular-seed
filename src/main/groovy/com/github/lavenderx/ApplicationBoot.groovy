package com.github.lavenderx

import groovy.transform.TypeChecked
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.util.StringUtils

/**
 * Created by lavenderx on 2016-05-05.
 */
@ComponentScan('com.github.lavenderx.config')
@SpringBootApplication
@TypeChecked
class ApplicationBoot {

    static final String PROFILE = 'spring.profiles.active'

    static main(String[] args) {
        if (StringUtils.isEmpty(System.getProperty(PROFILE))) {
            System.setProperty(PROFILE, 'prod')
        }

        SpringApplication.run(ApplicationBoot.class, args)
    }
}
