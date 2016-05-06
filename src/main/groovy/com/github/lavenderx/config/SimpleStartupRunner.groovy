package com.github.lavenderx.config

import groovy.transform.TypeChecked
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

/**
 * Created by lavenderx on 2016-05-05.
 */
@Order(1)
@Component
@TypeChecked
class SimpleStartupRunner implements CommandLineRunner {

    static final Logger LOGGER = LoggerFactory.getLogger(SimpleStartupRunner.class)

    @Override
    void run(String... strings) throws Exception {
        LOGGER.info('>>>>>>>>>>>>>>>> Spring Boot extra startup is running <<<<<<<<<<<<<<<<')
    }
}
